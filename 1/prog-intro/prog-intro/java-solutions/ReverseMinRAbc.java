import java.util.*;
import java.io.*;

public class ReverseMinRAbc {
    public static boolean isIntableABC(String s) {
        if ((s.charAt(0) == '-') || (s.charAt(0) == '+') || (Character.isLetter(s.charAt(0)))) {
            for (int i = 1; i < s.length(); i++) {
                if (!Character.isLetter(s.charAt(i))) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static int abcToNum(String abc) {
        StringBuilder num = new StringBuilder();
        for (char c : abc.toCharArray()) {
            if (Character.isLetter(c)) {
                num.append(c - 'a');
            } else {
                num.append(c);
            }
        }
        return Integer.parseInt(num.toString());
    }

    public static String numToAbc(int num) {
        StringBuilder abc = new StringBuilder();
        String s = Integer.toString(num);
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                abc.append((char) (Character.getNumericValue(c) + 'a'));
            } else {
                abc.append(c);
            }
        }
        return abc.toString();
    }

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
                while (sc.hasNext()) {
                    String temp = sc.next();
                    if (isIntableABC(temp)) {
                        if (numbers.length <= j) {
                            numbers = Arrays.copyOf(numbers, numbers.length * 2);
                        }
                        numbers[j] = abcToNum(temp);
                        j++;
                    } else {
                        break;
                    }
                }
                sc.close();
                numbers = Arrays.copyOf(numbers, j);
                lines[i] = numbers;
                i++;
            }
            scanner.close();
            lines = Arrays.copyOf(lines, i);
            for (int k = 0; k < lines.length; k++) {
                int Min = Integer.MAX_VALUE;
                for (int p = 0; p < lines[k].length; p++) {
                    if (lines[k][p] < Min) {
                        Min = lines[k][p];
                    }
                    System.out.print(numToAbc(Min) + " ");
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