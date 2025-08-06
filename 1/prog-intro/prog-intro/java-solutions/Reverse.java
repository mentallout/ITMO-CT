import java.util.Arrays;
import java.io.*;

public class Reverse {
    public static void main(String[] args) {
        try {
            MyScanner scanner = new MyScanner(System.in);
            int[][] lines = new int[100][];
            int i = 0;
            while (scanner.hasNextLine()) {
                if (lines.length <= i) {
                    lines = Arrays.copyOf(lines, lines.length * 2);
                }
                MyScanner sc = new MyScanner(scanner.nextLine());
                int[] numbers = new int[100];
                int j = 0;
                while (sc.hasNextInt()) {
                    if (numbers.length <= j) {
                        numbers = Arrays.copyOf(numbers, numbers.length * 2);
                    }
                    numbers[j] = sc.nextInt();
                    j++;
                }
                sc.close();
                numbers = Arrays.copyOf(numbers, j);
                lines[i] = numbers;
                i++;
            }
            scanner.close();
            lines = Arrays.copyOf(lines, i);
            for (int k = lines.length - 1; k >= 0; k--) {
                for (int p = lines[k].length - 1; p >= 0; p--) {
                    System.out.print(lines[k][p] + " ");
                }
                if ((k < lines.length - 1)) {
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}