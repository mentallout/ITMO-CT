package game;

import java.io.PrintStream;
import java.util.Scanner;


public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    public void printing(final Position position, final Cell cell) {
        out.println("Position");
        out.println(position);
        out.println(cell + "'s move");
        out.println("Enter row and column");
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            printing(position, cell);
            int r;
            int c;
            while (true) {
                try {
                    r = Integer.parseInt(in.next());
                    c = Integer.parseInt(in.next());
                    in.nextLine();
                    break;
                } catch (Exception e) {
                    if (!in.hasNext()) {
                        out.println("You lost. Don't break the game");
                        System.exit(0);
                    } else {
                        out.println("Move is invalid. Please, enter integer numbers for row and column");
                        in.nextLine();
                        printing(position, cell);
                    }
                }
            }
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}

