package game;


public interface Position {
    boolean isValid(Move move);

    int getM();

    int getN();
}
