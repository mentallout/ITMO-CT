import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class H {
    static final long MOD = 998244353;

    static long power(long base) {
        long result = 1;
        long power = 998244351L;
        while (power > 0) {
            if (power % 2 == 1) {
                result = ((result * base) % MOD + MOD) % MOD;
            }
            base = ((base * base) % MOD + MOD) % MOD;
            power /= 2;
        }
        return result;
    }

    static long factorial(int n, List<Long> factorialCache) {
        while (factorialCache.size() <= n) {
            int size = factorialCache.size();
            long nextFactorial = (factorialCache.get(size - 1) * size) % MOD;
            factorialCache.add(nextFactorial);
        }
        return factorialCache.get(n);
    }

    private static void assignFactorials(List<Long> factorialCache, long[] a, int i, int j) {
        long factI = factorial(i, factorialCache);
        long factJ = factorial(j, factorialCache);
        long factIminusJ = factorial(i - j, factorialCache);

        a[j] = ((((factI * power((factJ * factIminusJ % MOD + MOD) % MOD)) % MOD + MOD) % MOD) * (long) Math.pow(-1, j) % MOD + MOD) % MOD;
    }

    public static void main(String[] args) {
        List<Long> factorialCache = new ArrayList<>();
        factorialCache.add(1L);

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        sc.close();

        long[] a = new long[k];
        long[] b = new long[k];
        for (int i = n - 2, j = 0; i >= j; i--, j++) {
            assignFactorials(factorialCache, a, i, j);
        }
        for (int i = n - 1, j = 0; i >= j; i--, j++) {
            assignFactorials(factorialCache, b, i, j);
        }
        long[] trees = new long[k];
        trees[0] = ((a[0] * power(b[0])) % MOD + MOD) % MOD;
        for (int i = 1; i < k; i++) {
            long tree = 0;
            for (int j = 1; j <= i; j++) {
                tree = ((tree + (((b[j] * trees[i - j]) % MOD + MOD) % MOD)) % MOD + MOD) % MOD;
            }
            trees[i] = ((a[i] - tree) % MOD + MOD) % MOD;
        }

        for (int i = 0; i < k; i++) {
            System.out.println(trees[i]);
        }
    }
}