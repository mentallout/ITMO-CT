import java.util.Scanner;

public class I {
    static final int MOD = 104857601;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        long n = sc.nextLong() - 1;
        int[] a = new int[2 * k];
        int[] c = new int[k + 1];
        for (int i = 0; i < k; i++) {
            a[i] = sc.nextInt();
        }
        for (int i = 1; i <= k; i++) {
            long ci = -sc.nextInt();
            ci %= MOD;
            if (ci < 0) ci += MOD;
            c[i] = (int) ci;
        }
        c[0] = 1;
        sc.close();

        while (n >= k) {
            for (int i = k; i < 2 * k; i++) {
                long ai = 0;
                for (int j = 1; j <= k; j++) {
                    ai = (ai - (long) c[j] * a[i - j]) % MOD;
                }
                if (ai < 0) ai += MOD;
                a[i] = (int) ai;
            }
            int[] plusminus = new int[k + 1];
            for (int i = 0; i <= 2 * k; i += 2) {
                long an = 0;
                for (int j = 0; j <= i; j++) {
                    int qj = j <= k ? c[j] : 0;
                    int qn = (i - j) <= k ? ((i - j) % 2 == 0 ? c[i - j] : -c[i - j]) : 0;
                    an = (an + (long) qj * qn) % MOD;
                }
                an %= MOD;
                if (an < 0) an += MOD;
                plusminus[i / 2] = (int) an;
            }
            int seq = 0;
            for (int i = 0; i < 2 * k; i++) {
                if ((i % 2) == (n % 2)) {
                    a[seq++] = a[i];
                }
            }
            c = plusminus;
            n /= 2;
        }
        System.out.println(a[(int) n]);
    }
}
