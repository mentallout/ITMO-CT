import java.util.*;

public class ManagingDifficulties {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int testCases = scanner.nextInt();
            for (int p = 0; p < testCases; p++) {
                Map<Integer, Integer> C = new HashMap<>();
                int count = 0;
                int n = scanner.nextInt();
                int[] a = new int[n];
                for (int x = 0; x < n; x++) {
                    a[x] = scanner.nextInt();
                }
                for (int j = n - 1; j >= 1; j--) {
                    for (int i = 0; i < j; i++) {
                        count += C.getOrDefault(2 * a[j] - a[i], 0);
                    }
                    C.put(a[j], C.getOrDefault(a[j], 0) + 1);
                }
                System.out.println(count);
            }
        }
    }
}
