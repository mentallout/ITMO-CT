package game;

import java.util.*;


public class Main {
    private static int getAmount(int amount, int m, int n, int k, List<String> nicks, List<String> table, String nick1, String nick2, Game game) {
        int result;
        do {
            result = game.play(new MnkBoard(m, n, k));
            if (result == 1) {
                nicks.remove(nick2);
                nicks.remove(nick1);
                table.remove(nick2);
                table.add(amount - 1, nick2);
                amount--;
            } else if (result == 2) {
                nicks.remove(nick1);
                nicks.remove(nick2);
                table.remove(nick1);
                table.add(amount - 1, nick1);
                amount--;
            }
        } while (result == -1 || result == 0);
        return amount;
    }

    private static Game getGame(LinkedHashMap<String, String> players, String nick1, String nick2) {
        Player type1 = createPlayer(players.get(nick1));
        Player type2 = createPlayer(players.get(nick2));
        System.out.println(nick1 + " is for 'X's, " + nick2 + " is for '0's");
        return new Game(true, type1, type2, nick1, nick2);
    }

    private static Player createPlayer(String type) {
        return switch (type) {
            case "R" -> new RandomPlayer();
            case "S" -> new SequentialPlayer();
            case "H" -> new HumanPlayer();
            default -> throw new IllegalArgumentException("Invalid player type: " + type);
        };
    }

    public static void main(String[] args) {
        int amount;
        int m, n, k;
        String mode;
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter 3 values for m, n, k ( m - number of rows, n - number of columns, k - number of marks in a row to win): ");
                m = sc.nextInt();
                n = sc.nextInt();
                k = sc.nextInt();
                if ((m <= 0 || n <= 0 || k <= 0) || (k > m && k > n)) {
                    throw new Error("Values should be positive numbers. Reminder: k should be greater than m or n so game makes sense. ");
                }
                break;
            } catch (Exception e) {
                if (!sc.hasNext()) {
                    System.out.println("You lost. EOT found. ");
                    return;
                } else {
                    System.out.println("Incorrect input. Try again. ");
                    sc.nextLine();
                }
            } catch (Error e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println("What mode? Enter '1x1' or 'tournament': ");
                mode = sc.next().toLowerCase();
                if (!(mode.equals("1x1") || mode.equals("tournament"))) {
                    throw new Error("Enter '1x1' or 'tournament': ");
                }
                break;
            } catch (Exception e) {
                if (!sc.hasNext()) {
                    System.out.println("You lost. EOT found. ");
                    return;
                } else {
                    System.out.println("Incorrect input. Try again. ");
                    sc.nextLine();
                }
            } catch (Error e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        amount = 2;
        if (mode.equals("tournament")) {
            while (true) {
                try {
                    System.out.println("How many players are going to play? ");
                    amount = sc.nextInt();
                    if (amount <= 2) {
                        throw new Error("There should be at least 3 players in tournament! ");
                    }
                    break;
                } catch (Exception e) {
                    if (!sc.hasNext()) {
                        System.out.println("You lost. EOT found. ");
                        return;
                    } else {
                        System.out.println("Incorrect input. Try again. ");
                        sc.nextLine();
                    }
                } catch (Error e) {
                    System.out.println(e.getMessage());
                    sc.nextLine();
                }
            }
        }
        LinkedHashMap<String, String> players = new LinkedHashMap<>();
        String nickname, type;
        while (amount > 0) {
            while (true) {
                System.out.println("Enter nickname and a type of player ( 'H' - human, 'R' - random, 'S' - sequential ( nickname should be a word and type should be a letter h, r or s; for example 'player1 s'): ");
                try {
                    nickname = sc.next();
                    type = sc.next().toUpperCase();
                    sc.nextLine();
                    if (!(type.equals("S") || type.equals("R") || type.equals("H")) || players.containsKey(nickname)) {
                        throw new Error("Player with this nickname already exist or type is incorrect. ");
                    }
                    break;
                } catch (Exception e) {
                    if (!sc.hasNext()) {
                        System.out.println("You lost. EOT found. ");
                        return;
                    } else {
                        System.out.println("Incorrect input. Try again. ");
                        sc.nextLine();
                    }
                } catch (Error e) {
                    System.out.println(e.getMessage());
                }
            }
            players.put(nickname, type);
            amount--;
        }
        if (mode.equals("1x1")) {
            List<String> nicks = new ArrayList<>(players.keySet());
            Collections.shuffle(nicks);
            String nick1 = "";
            String nick2 = "";
            for (String name : nicks) {
                if (nick1.isEmpty()) {
                    nick1 = name;
                } else {
                    nick2 = name;
                }
            }
            Player type1 = createPlayer(players.get(nick1));
            Player type2 = createPlayer(players.get(nick2));
            System.out.println(nick1 + " is for 'X's, " + nick2 + " is for '0's");
            final Game game = new Game(true, type1, type2, nick1, nick2);
            int result;
            do {
                result = game.play(new MnkBoard(m, n, k));
            } while (result == -1);

        } else {
            amount = players.size();
            List<String> nicks = new ArrayList<>(players.keySet());
            List<String> table = new ArrayList<>(players.keySet());
            Collections.shuffle(nicks);
            while (Integer.bitCount(amount) != 1) {
                System.out.println("Season!");
                String nick1 = "";
                String nick2 = "";
                for (String name : nicks) {
                    if (nick1.isEmpty()) {
                        nick1 = name;
                    } else {
                        nick2 = name;
                    }
                }
                Collections.shuffle(nicks);
                Player type1 = createPlayer(players.get(nick1));
                Player type2 = createPlayer(players.get(nick2));
                System.out.println(nick1 + " is for 'X's, " + nick2 + " is for '0's");
                Game game = new Game(true, type1, type2, nick1, nick2);
                int result;
                amount = getAmount(amount, m, n, k, nicks, table, nick1, nick2, game);
                nicks.clear();
                nicks.addAll(table.subList(0, amount));

                int rounds = (int) (Math.log(amount) / Math.log(2));
                for (int i = 1; i <= rounds; i++) {
                    System.out.println("Round " + i);
                    int amountInRound = amount;
                    for (int j = 0; j < (int) (Math.log(amountInRound) / Math.log(2)); j++) {
                        nick1 = "";
                        nick2 = "";
                        for (String name : nicks) {
                            if (nick1.isEmpty()) {
                                nick1 = name;
                            } else {
                                nick2 = name;
                            }
                        }
                        Collections.shuffle(nicks);

                        game = getGame(players, nick1, nick2);
                        amount = getAmount(amount, m, n, k, nicks, table, nick1, nick2, game);
                        if (amount == 3 && table.size() >= 4) {
                            System.out.println("Bonus game for 3rd place! ");
                            nick1 = table.get(2);
                            nick2 = table.get(3);
                            game = getGame(players, nick1, nick2);
                            do {
                                result = game.play(new MnkBoard(m, n, k));
                                if (result == 1) {
                                    table.remove(nick1);
                                    table.add(2, nick1);
                                } else if (result == 2) {
                                    table.remove(nick2);
                                    table.add(2, nick2);
                                }
                            } while (result == -1 || result == 0);

                        }
                    }
                    nicks.clear();
                    nicks.addAll(table.subList(0, amount));
                }
                System.out.println("Table:");
                for (int p = 1; p <= table.size(); p++) {
                    System.out.println(p + " " + table.get(p - 1));
                }
            }
        }
    }
}
