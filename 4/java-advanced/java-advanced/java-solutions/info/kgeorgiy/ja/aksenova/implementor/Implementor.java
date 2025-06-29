package info.kgeorgiy.ja.aksenova.implementor;

import info.kgeorgiy.java.advanced.implementor.Impler;
import info.kgeorgiy.java.advanced.implementor.ImplerException;
import info.kgeorgiy.java.advanced.implementor.tools.JarImpler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

/**
 * This class generates implementations for Java interfaces.
 * It provides functionality to generate Java source files, compile them,
 * and package compiled classes into JAR files.
 *
 * <p>
 * The class implements the {@link info.kgeorgiy.java.advanced.implementor.Impler} and
 * {@link info.kgeorgiy.java.advanced.implementor.tools.JarImpler} interfaces.
 * It generates implementations for interfaces by creating a new class
 * with method stubs that return default values.
 * </p>
 *
 * @author Valeria Aksenova
 */
public class Implementor implements Impler, JarImpler {
    /**
     * Current directory.
     */
    private final static Path DIR = Path.of("");

    /**
     * Line separator.
     */
    private final String LINE_SEPARATOR = System.lineSeparator();

    /**
     * Space character used for formatting.
     */
    private final String SPACE = " ";

    /**
     * Tabulation (4 {@code SPACE}) used for formatting.
     */
    private final String TABULATION = SPACE.repeat(4);

    /**
     * Opening brace followed by a new line, representing the beginning of a code block.
     */
    private final String OPENING = '{' + LINE_SEPARATOR;

    /**
     * Closing brace followed by a new line, representing the end of a code block.
     */
    private final String ENDING = '}' + LINE_SEPARATOR;

    /**
     * Semicolon followed by a new line, used for ending statements.
     */
    private final String SEMICOLON = ';' + LINE_SEPARATOR;

    /**
     * Comma followed by a space, used for separating elements in lists.
     */
    private final String COMMA = ", ";

    /**
     * Default constructor for the {@code Implementor} class.
     */
    public Implementor() {
    }

    /**
     * Generates the full path for the implementation class.
     *
     * @param packageName The package name of the original interface.
     * @param simpleName  The simple name of the original interface.
     * @param format      The file format (e.g., ".java" or ".class").
     * @return The path where the implementation file should be created.
     */
    private Path fullName(String packageName, String simpleName, String format) {
        return DIR.resolve(((packageName.isEmpty() ? "" : packageName.replace(".", File.separator) + File.separator) + simpleName + "Impl" + format));
    }

    /**
     * Generates a string representation of method parameters.
     *
     * @param method The method whose parameters are being extracted.
     * @return A formatted string representing method parameters.
     */
    private String getParameters(Method method) {
        return '(' + Arrays.stream(method.getParameters()).map(p -> p.getType().getCanonicalName() + SPACE + p.getName()).collect(Collectors.joining(COMMA)) + ')';
    }

    /**
     * Generates a string representation of exceptions thrown by a method.
     *
     * @param method The method whose exceptions are being extracted.
     * @return A formatted string representing thrown exceptions.
     */
    private String getExceptions(Method method) {
        return Arrays.stream(method.getExceptionTypes()).map(Class::getCanonicalName).collect(Collectors.joining(COMMA));
    }

    /**
     * Returns a default return value for a given return type.
     *
     * @param returnType The return type of the method.
     * @return A default value (null for objects, false for booleans, 0 for numbers).
     */
    private String getReturnValue(Class<?> returnType) {
        if (!returnType.isPrimitive()) {
            return "null";
        }
        if (returnType.equals(boolean.class)) {
            return "false";
        } else {
            return "0";
        }
    }

    /**
     * Retrieves the classpath of a given class.
     *
     * @param token The class whose classpath is to be retrieved.
     * @return The classpath as a {@link Path}.
     * @throws ImplerException if the classpath cannot be determined.
     */
    private Path getClassPath(Class<?> token) throws ImplerException {
        try {
            return Path.of(token.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (final URISyntaxException e) {
            throw new ImplerException("Error occured, can't get class path! ", e);
        }
    }

    /**
     * Compiles the generated Java file.
     *
     * @param token The class to be compiled.
     * @throws ImplerException if compilation fails.
     */
    private void compile(Class<?> token) throws ImplerException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new ImplerException("Could not find java compiler, include tools.jar to classpath");
        }
        String classpath = getClassPath(token).toString();
        if (compiler.run(null, null, null, "-cp", classpath, "-encoding", StandardCharsets.UTF_8.name(), fullName(token.getPackageName(), token.getSimpleName(), ".java").toString()) != 0) {
            throw new ImplerException("Compilation failed!");
        }
    }

    /**
     * Creates a JAR file containing the compiled implementation class.
     *
     * @param token   The class to be implemented.
     * @param jarFile The path where the JAR file should be created.
     * @throws ImplerException if an error occurs while creating the JAR file.
     */
    private void createJar(Class<?> token, Path jarFile) throws ImplerException {
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(jarFile), manifest)) {
            jarOutputStream.putNextEntry(new JarEntry(token.getPackageName().replace('.', '/') + "/" + token.getSimpleName() + "Impl.class"));
            Files.copy(fullName(token.getPackageName(), token.getSimpleName(), ".class"), jarOutputStream);
            jarOutputStream.closeEntry();
        } catch (IOException e) {
            throw new ImplerException("Error creating jar file", e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Produces code implementing class or interface specified by provided {@code token}.
     * <p>
     * Generated class' name should be the same as the class name of the type token with {@code Impl} suffix
     * added. Generated source code should be placed in the correct subdirectory of the specified
     * {@code root} directory and have correct file name. For example, the implementation of the
     * interface {@link java.util.List} should go to {@code $root/java/util/ListImpl.java}
     *
     * @param token type token to create implementation for.
     * @param root  root directory.
     * @throws info.kgeorgiy.java.advanced.implementor.ImplerException when implementation cannot be
     *                                                                 generated.
     */
    @Override
    public void implement(Class<?> token, Path root) throws ImplerException {
        if (!token.isInterface()) {
            throw new ImplerException("Can't implement " + token.getName());
        }
        if (Modifier.isPrivate(token.getModifiers())) {
            throw new ImplerException("Interface is private! Can't implement " + token.getName());
        }
        String packageName = token.getPackageName();
        String simpleName = token.getSimpleName();
        root = root.resolve(fullName(packageName, simpleName, ".java")).toAbsolutePath();
        if (root.getParent() != null) {
            try {
                Files.createDirectories(root.getParent());
            } catch (IOException e) {
                System.err.println("Can't access output file! " + e.getMessage());
            }
        }
        try (Writer bw = Files.newBufferedWriter(root)) {
            if (!packageName.isEmpty()) {
                bw.write("package " + packageName + SEMICOLON + LINE_SEPARATOR);
            }
            bw.write("public class " + simpleName + "Impl implements " + token.getCanonicalName() + SPACE + OPENING);
            for (Method method : token.getMethods()) {
                if (!Modifier.isAbstract(method.getModifiers())) {
                    continue;
                }
                //bw.write(LINE_SEPARATOR + TABULATION + "@Override" + LINE_SEPARATOR);
                bw.write(TABULATION + "public" + SPACE + method.getReturnType().getCanonicalName() + SPACE + method.getName() + getParameters(method) + SPACE);
                String exceptions = getExceptions(method);
                if (!exceptions.isEmpty()) {
                    bw.write("throws " + exceptions + SPACE);
                }
                bw.write(OPENING);
                if (method.getReturnType() != void.class) {
                    bw.write(TABULATION.repeat(2) + "return " + getReturnValue(method.getReturnType()) + SEMICOLON);
                }
                bw.write(TABULATION + ENDING);
            }
            bw.write(ENDING);
        } catch (IOException e) {
            System.err.println("Error occured while writing! " + e);
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Produces <var>.jar</var> file implementing class or interface specified by provided <var>token</var>.
     * <p>
     * Generated class' name should be the same as the class name of the type token with <var>Impl</var> suffix
     * added.
     *
     * @param token   type token to create implementation for.
     * @param jarFile target <var>.jar</var> file.
     * @throws ImplerException when implementation cannot be generated.
     */
    @Override
    public void implementJar(Class<?> token, Path jarFile) throws ImplerException {
        implement(token, DIR);
        compile(token);
        createJar(token, jarFile);
    }

    /**
     * The entry point of the program.
     *
     * <p>
     * This method processes command-line arguments and either generates
     * a Java source file implementing the given interface or creates a
     * JAR file containing the compiled implementation.
     * </p>
     *
     * <p>
     * Expected usage:
     * <ul>
     *   <li>{@code java Implementor full.class.name} - Generates an implementation source file.</li>
     *   <li>{@code java Implementor -jar full.class.name output.jar} - Generates a compiled implementation and packages it into a JAR.</li>
     * </ul>
     *
     * @param args The command-line arguments. It must be either:
     *             <ul>
     *               <li>A single argument specifying the fully qualified name of the interface to implement.</li>
     *               <li>Three arguments: {@code -jar}, the fully qualified name of the interface, and the output JAR file path.</li>
     *             </ul>
     */
    public static void main(String[] args) {
        if (args == null || (args.length == 1 && args[0].isEmpty()) || (args.length == 3 && (!args[0].equals("-jar") || args[1].isEmpty() || args[2].isEmpty()))) {
            System.err.println("Wrong input!");
            return;
        }
        Implementor impl = new Implementor();
        try {
            Path root = DIR;
            if (args.length == 1) {
                impl.implement(Class.forName(args[0]), root);
            } else if (args.length == 3) {
                root = Path.of(args[2]);
                impl.implementJar(Class.forName(args[1]), root);
            }
        } catch (InvalidPathException e) {
            System.err.println("Error getting path! " + args[2]);
        } catch (ClassNotFoundException e) {
            System.err.println("Can't find class " + args[0]);
        } catch (ImplerException e) {
            System.err.println(e.getMessage());
        }
    }
}
