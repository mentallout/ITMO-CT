package game;


public class Game {
    private final boolean log;
    private final Player player1, player2;
    private final String nickname1, nickname2;

    public Game(final boolean log, final Player player1, final Player player2, String nickname1, String nickname2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
        this.nickname1 = nickname1;
        this.nickname2 = nickname2;
    }

    public int play(Board board) {
        while (true) {
            final int result1 = move(board, player1, 1);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(board, player2, 2);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        String nick;
        if (no == 1) {
            nick = nickname1;
        } else {
            nick = nickname2;
        }
        log("Player " + nick + " move: " + move);
        log("Position:" + System.lineSeparator() + board);
        if (result == Result.WIN) {
            log("Player " + nick + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + nick + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
