import java.util.*;

public class ReverseMinR {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] lines = new int[100][];
        int i = 0;
        while (scanner.hasNextLine()) {
            if (lines.length <= i) {
                lines = Arrays.copyOf(lines, lines.length * 2);
            }
            Scanner sc = new Scanner(scanner.nextLine());
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
        for (int[] line : lines) {
            int Min = Integer.MAX_VALUE;
            for (int j : line) {
                if (j < Min) {
                    Min = j;
                }
                System.out.print(Min + " ");
            }
            System.out.println();
        }
    }
}