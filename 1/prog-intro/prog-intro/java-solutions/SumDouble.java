public class SumDouble {
    public static void main(String[] args) {
        double summa = 0;
        for (String str : args) {
            int start = -1;
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    if (start == -1) {
                        start = i;
                    }
                    if (i == str.length() - 1) {
                        summa += Double.parseDouble(str.substring(start));
                    }
                } else {
                    if (start != -1) {
                        summa += Double.parseDouble(str.substring(start, i));
                        start = -1;
                    }
                }
            }
        }
        System.out.println(summa);
    }
}