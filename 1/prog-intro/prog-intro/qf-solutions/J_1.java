import java.util.*;

public class JustTheLastDigit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] cnt = new int[n][n];
        for (int i = 0; i < n; i++) {
            String s = scanner.next();
            for (int j = 0; j < n; j++) {
                cnt[i][j] = (s.charAt(j) - '0');
            }
        }
        scanner.close();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (cnt[i][j] > 0) {
                    cnt[i][j] = 1;
                    for (int k = j - 1; k < n; k++) {
                        cnt[i][k] = (cnt[i][k] - cnt[j][k] + 10) % 10;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(cnt[i][j]);
            }
            System.out.println();
        }
    }
}