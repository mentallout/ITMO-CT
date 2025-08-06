import java.util.Scanner;

public class AccurateMovement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        scanner.close();
        System.out.println((int) (2 * Math.ceil((double) (n - b) / (b - a)) + 1));
    }
}