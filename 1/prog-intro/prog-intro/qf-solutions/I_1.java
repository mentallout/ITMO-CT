import java.util.*;

public class IdealPyramid {
    public static void main(String[] args) {
        int xl = Integer.MAX_VALUE;
        int xr = Integer.MIN_VALUE;
        int yl = Integer.MAX_VALUE;
        int yr = Integer.MIN_VALUE;
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            int h = scanner.nextInt();
            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }
        scanner.close();
        int x = (xl + xr) / 2;
        int y = (yl + yr) / 2;
        int h = (int) Math.ceil((double) Math.max(xr - xl, yr - yl) / 2);
        System.out.println(x + " " + y + " " + h);
    }
}

