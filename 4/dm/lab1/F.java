import java.util.*;

public class F {
    private static final int MOD = 1000000007;
    private static final Map<Integer, Integer> cache = new HashMap<>();


    public static long calculate(int i, int[] c, long[] dp) {
        if (dp[i] == -1) {
            dp[i] = 0;
            for (int ci : c) {
                dp[i] = (dp[i] + cache.computeIfAbsent(i - ci, (sum) -> {
                    long res = 0;
                    for (int l = 0; l <= sum; l++) {
                        res = (res + calculate(l, c, dp) * calculate(sum - l, c, dp) % MOD) % MOD;
                    }
                    return (int) res;
                })) % MOD;
            }
        }
        return dp[i];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int k = sc.nextInt();
        int m = sc.nextInt();
        int[] c = new int[k];
        for (int i = 0; i < k; i++) {
            c[i] = sc.nextInt();
        }
        sc.close();

        long[] dp = new long[m + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        for (int i = 1; i <= m; i++) {
            calculate(i, c, dp);
        }

        for (int i = 1; i <= m; i++) {
            System.out.print(dp[i] + " ");
        }
    }
}