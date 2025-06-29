package info.kgeorgiy.ja.aksenova.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Crawls websites and downloads pages
 *
 * @author Valeria Aksenova
 */
public class WebCrawler implements Crawler, NewCrawler {
    private final Downloader downloader;
    private final ExecutorService downloadExecutor;
    private final ExecutorService extractExecutor;

    /**
     * Constructs a new WebCrawler instance.
     *
     * @param downloader  Downloader used for downloading documents
     * @param downloaders number of threads for downloading
     * @param extractors  number of threads for extracting links
     * @param perHost     maximum number of downloads per host
     */
    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloadExecutor = Executors.newFixedThreadPool(downloaders);
        this.extractExecutor = Executors.newFixedThreadPool(extractors);
    }

    private void handleInterruption(Exception e) {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean excluding(String host, List<String> excludes) {
        for (String exclusion : excludes) {
            if (host.contains(exclusion)) {
                return true;
            }
        }
        return false;
    }

    private Future<?> downloading(int depth, String url, List<String> excludes, List<String> next, Set<String> visited, Set<String> downloaded, Map<String, IOException> errors) {
        return this.downloadExecutor.submit(() -> {
            try {
                Document document = this.downloader.download(url);
                downloaded.add(url);
                if (depth > 1) {
                    extracting(document, excludes, next, visited);
                }
            } catch (IOException | InterruptedException | ExecutionException e) {
                handleInterruption(e);
                if (!(e instanceof InterruptedException)) {
                    assert e instanceof IOException;
                    errors.put(url, (IOException) e);
                }
            }
        });
    }

    private void extracting(Document document, List<String> excludes, List<String> next, Set<String> visited) throws InterruptedException, ExecutionException {
        List<String> links = this.extractExecutor.submit(document::extractLinks).get();
        for (String link : links) {
            try {
                String host = URLUtils.getHost(link);
                if (!excluding(host, excludes) && visited.add(link)) {
                    next.add(link);
                }
            } catch (MalformedURLException ignored) {
            }
        }
    }

    /**
     * Downloads website up to specified depth.
     *
     * @param url      start URL.
     * @param depth    download depth.
     * @param excludes hosts containing one of given substrings are ignored.
     * @return download result.
     */
    @Override
    public Result download(String url, int depth, List<String> excludes) {
        try {
            String host = URLUtils.getHost(url);
            if (depth == 0 || url == null || excluding(host, excludes)) {
                return new Result(Collections.emptyList(), Collections.emptyMap());
            }
        } catch (MalformedURLException ignored) {
        }
        Set<String> visited = Collections.synchronizedSet(new HashSet<>());
        Set<String> downloaded = Collections.synchronizedSet(new HashSet<>());
        Map<String, IOException> errors = new ConcurrentHashMap<>();
        List<String> current = new ArrayList<>();
        current.add(url);
        visited.add(url);
        for (int i = depth; i > 0; i--) {
            List<Future<?>> tasks = new ArrayList<>();
            List<String> next = Collections.synchronizedList(new ArrayList<>());
            for (String currentUrl : current) {
                tasks.add(downloading(i, currentUrl, excludes, next, visited, downloaded, errors));
            }
            for (Future<?> task : tasks) {
                try {
                    task.get();
                } catch (InterruptedException | ExecutionException e) {
                    handleInterruption(e);
                }
            }
            current = next;
        }
        return new Result(new ArrayList<>(downloaded), errors);
    }

    private void shutdown() {
        this.downloadExecutor.shutdown();
        this.extractExecutor.shutdown();
    }

    private void awaitTerminationOrShutdown(ExecutorService executor, TimeUnit unit) {
        try {
            if (!executor.awaitTermination(1, unit)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Closes this crawler, freeing any allocated resources.
     */
    @Override
    public void close() {
        final TimeUnit TIME_UNIT = TimeUnit.MINUTES;
        shutdown();
        awaitTerminationOrShutdown(this.downloadExecutor, TIME_UNIT);
        awaitTerminationOrShutdown(this.extractExecutor, TIME_UNIT);
    }

    /**
     * Usage: {@code WebCrawler url [depth [downloads [extractors [perHost]]]]} or {@code WebCrawler url [depth [downloads [extractors [perHost [excludes]]]]]}
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        if (args == null || args.length < 5 || args.length > 6) {
            System.out.println("Wrong input! Use <WebCrawler url [depth [downloads [extractors [perHost]]]]> or <WebCrawler url [depth [downloads [extractors [perHost [excludes]]]]]>");
            return;
        }
        int depth, downloads, extractors, perHost;
        List<String> excludes = new ArrayList<>();
        String url = args[0];
        try {
            depth = Integer.parseInt(args[1]);
            downloads = Integer.parseInt(args[2]);
            extractors = Integer.parseInt(args[3]);
            perHost = Integer.parseInt(args[4]);
            if (args.length == 6) {
                excludes = Arrays.asList(args[5].split(","));
            }
        } catch (NumberFormatException e) {
            System.out.println("Wrong input! Couldn't parse depth, downloads, extractors, perHost or excludes");
            return;
        }
        try (WebCrawler crawler = new WebCrawler(new CachingDownloader(1), downloads, extractors, perHost)) {
            crawler.download(url, depth, excludes);
        } catch (IOException e) {
            System.err.println("Failed to download! " + e.getMessage());
        }
    }
}