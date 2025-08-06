import java.util.Scanner;

public class AccurateMovement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        scanner.close();
        System.out.println(2 * Math.round((float) (n - b) / (b - a)) + 1);
    }
}