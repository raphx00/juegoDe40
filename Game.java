//Este Juego es 40 para 3 jugadores.
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
// import java.util.Random; // removed: no automatic mode

public class Game {

    private Deck deck;
    private Card lastPlayedCard = null;
    private Card playedCard;
    private Player[]  players = new Player[3];
    private Table table = new Table();
    // no usamos campo 'round' ahora que cada mano itera explícitamente
    private Card[] pair;
    private Player temp_Player;
    private Scanner scanner;
    // Stats tracking per player for summary
    private java.util.Map<Player, ActionStats> stats = new java.util.HashMap<>();
    private int handsPlayed = 0;

    private static class ActionStats {
        int caidaCount = 0;
        int caidaPoints = 0;
        int escobaCount = 0;
        int escobaPoints = 0;
    }


    public void play40(Player p1, Player p2, Player p3) {
        scanner = new Scanner(System.in);

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;

        // We shuffle the players so that its random who goes first
        Collections.shuffle(Arrays.asList(players));
        
        while(p1.getScore() < 40 && p2.getScore() < 40 && p3.getScore() < 40){
            // Rotate players (who starts first rotates each hand)
            temp_Player = players[0];
            players[0] = players[1];
            players[1] = players[2];
            players[2] = temp_Player;

            // New deck for this hand
            deck = new Deck();

            // Play rounds 1 and 2 (5 cards each)
            for (int r = 1; r <= 2; r++) {
                playRound(r, 5, false);
            }

            // Play round 3 (3 cards each + 1 card on table)
            playRound(3, 3, true);

            // End of hand housekeeping: clear table and last played card so next hand starts fresh
            table = new Table();
            lastPlayedCard = null;

            // count this hand
            handsPlayed += 1;

            System.out.println("El ganador es: ");
            if(p1.getScore() >= 40) System.out.println(p1.getNombre());
            if(p2.getScore() >= 40) System.out.println(p2.getNombre());
            if(p3.getScore() >= 40) System.out.println(p3.getNombre());
        }
        
        // print final summary and cleanup
        printSummary(p1, p2, p3);
        scanner.close();
    }
    
    // Method to play a complete round
    private void playRound(int roundNumber, int cardsPerPlayer, boolean addCardToTable) {
        showScores();
        System.out.println("Ronda " + roundNumber);
        
        // Deal cards to players
        dealCards(cardsPerPlayer);
        
        // Add one card to table if it's the last round
        if(addCardToTable) {
            table.agregarCarta(deck.repartir());
        }
        
        // Play all cards in hand
        for(int i = 0; i < cardsPerPlayer; i++){
            for(Player player: players){
                playPlayerTurn(player, roundNumber == 3);
            }
        }
    }

    // Method to deal cards to all players
    private void dealCards(int cardsPerPlayer) {
        for(Player player: players){
            for(int i = 0; i < cardsPerPlayer; i++){
                Card c = deck.repartir();
                if (c == null) {
                    throw new IllegalStateException("No hay más cartas en el mazo al repartir. Revisar lógica de reparto.");
                }
                player.addCardToHand(c);
            }
        }
    }

    // Method to display current scores
    private void showScores() {
        System.out.println("Puntajes: ");
        for(Player player: players){
            System.out.println(player.getNombre() + ": " + player.getScore());
        }
    }

    // Method to handle a single player's turn
    private void playPlayerTurn(Player player, boolean showLastCard) {
        // Show last played card for round 3 only
        if(showLastCard) {
            if(lastPlayedCard != null)
                System.out.println("La última carta jugada fue: " + lastPlayedCard.toString());
            else
                System.out.println("Nadie ha jugado una carta aún.");
        }

        // Show table and player's hand
        System.out.println("Cartas en la mesa:");
        table.mostrarCartas();
        System.out.println("Tus cartas:");
        player.showHand();

        // Get player's card choice
        int choice = getPlayerChoice(player);
        playedCard = player.getMano().playCardByIndex(choice);

        // Process the played card
        processPlayedCard(player, showLastCard);
        
        lastPlayedCard = playedCard;
    }

    // Method to get valid input from player
    private int getPlayerChoice(Player player) {
        int choice;
        if(player.getMano().getSize() == 1) {
            System.out.print("Elige una carta para jugar [0-0]: ");
        } else {
            System.out.print("Elige una carta para jugar [0-" + (player.getMano().getSize() - 1) + "]: ");
        }
        while(true){
            if(!scanner.hasNextInt()){
                System.out.print("Entrada inválida. Introduce un número: ");
                scanner.next(); // consumir token inválido
                continue;
            }
            choice = scanner.nextInt();
            if(choice < 0 || choice >= player.getMano().getSize()) {
                System.out.print("Esa carta no existe. Elige de nuevo: ");
            } else {
                break;
            }
        }
        return choice;
    }
    
    // Method to process the played card logic
    private void processPlayedCard(Player player, boolean showCaidaMessage) {
        boolean canTakeByValue = table.verificarCarta(playedCard);
        boolean canTakeBySum = table.verifyByPairSum(playedCard);
        
        if(canTakeByValue && canTakeBySum) {
            // Player can choose between value or sum
            int choice = getValueOrSumChoice();
            if(choice == 1) {
                takeByValue(player, showCaidaMessage);
            } else {
                takeBySum(player, showCaidaMessage);
            }
        } else if(canTakeByValue) {
            takeByValue(player, showCaidaMessage);
        } else if(canTakeBySum) {
            takeBySum(player, showCaidaMessage);
        } else {
            // Card goes to table
            table.agregarCarta(playedCard);
        }
    }
    
    // Method to get player's choice between value or sum
    private int getValueOrSumChoice() {
        System.out.println("La carta que jugaste puede ser tomada por valor o por suma. ¿Cómo quieres tomarla?");
        System.out.println("1- Por valor");
        System.out.println("2- Por suma");
        System.out.print("Elige una opción: ");
        int choice;
        while(true){
            if(!scanner.hasNextInt()){
                System.out.print("Entrada inválida. Introduce 1 o 2: ");
                scanner.next();
                continue;
            }
            choice = scanner.nextInt();
            if(choice != 1 && choice != 2) {
                System.out.print("Esa opción no existe. Elige de nuevo: ");
            } else {
                break;
            }
        }
        return choice;
    }
    
    // Method to take cards by value
    private void takeByValue(Player player, boolean showCaidaMessage) {
    // Check for "Caida": any card on table with same value
    boolean isCaida = table.hasCardWithValue(playedCard.getValor());
            if(isCaida) {
                // Always announce caida so the player sees it in console
                player.registrarCaida(true);
            player.agregarPuntos(2);
            // record stats
            stats.putIfAbsent(player, new ActionStats());
            stats.get(player).caidaCount += 1;
            stats.get(player).caidaPoints += 2;
        }
        
        // Take the cards
        player.addCardToCarton(playedCard);
        Card captured = table.removeFirstCapturableCard(playedCard);
        if (captured != null) {
            player.addCardToCarton(captured);
        } else {
            // fallback: try removing by exact value
            Card fallback = table.removeCardByValue(playedCard.getValor());
            if (fallback != null) player.addCardToCarton(fallback);
        }

        // Check for table clear bonus
        if(table.verificarLimpia() && player.getScore() < 38) {
            player.agregarPuntos(2);
            stats.putIfAbsent(player, new ActionStats());
            stats.get(player).escobaCount += 1;
            stats.get(player).escobaPoints += 2;
        }
    }
    
    // Method to take cards by sum
    private void takeBySum(Player player, boolean showCaidaMessage) {
        pair = table.removePairBySum(playedCard);
        if(pair != null){
            // Check for "Caida": any card on table with same value
            boolean isCaida = table.hasCardWithValue(playedCard.getValor());
            if(isCaida) {
                // Always announce caida so the player sees it in console
                player.registrarCaida(true);
                player.agregarPuntos(2);
                stats.putIfAbsent(player, new ActionStats());
                stats.get(player).caidaCount += 1;
                stats.get(player).caidaPoints += 2;
            }

            // Take the cards
            player.addCardToCarton(playedCard);
            player.addCardToCarton(pair[0]);
            player.addCardToCarton(pair[1]);

            // Check for table clear bonus
            if(table.verificarLimpia() && player.getScore() < 38) {
                player.agregarPuntos(2);
                stats.putIfAbsent(player, new ActionStats());
                stats.get(player).escobaCount += 1;
                stats.get(player).escobaPoints += 2;
            }
        } else {
            // This shouldn't happen
            System.out.println("Error: No se pudo encontrar un par que sume el valor de la carta jugada.");
            table.agregarCarta(playedCard);
        }
    }

    private void printSummary(Player p1, Player p2, Player p3){
        StringBuilder out = new StringBuilder();
        out.append("\n--- Resumen final de la partida ---\n");
        out.append("Manos jugadas: " + handsPlayed + "\n");

        Player[] arr = new Player[]{p1,p2,p3};
        java.util.List<Player> list = new java.util.ArrayList<>();
        for (Player p: arr) list.add(p);
        // sort by score descending
        list.sort((a,b) -> Integer.compare(b.getScore(), a.getScore()));

        Player winner = list.get(0);
        out.append("Ganador: " + winner.getNombre() + " (" + winner.getScore() + " pts)\n\n");

        for(Player p: list){
            ActionStats s = stats.getOrDefault(p, new ActionStats());
            out.append("Jugador: " + p.getNombre() + "\n");
            out.append("  Puntos totales: " + p.getScore() + "\n");
            out.append("  Caídas: " + s.caidaCount + " (" + s.caidaPoints + " pts)\n");
            out.append("  Escobas: " + s.escobaCount + " (" + s.escobaPoints + " pts)\n\n");
        }

        String summary = out.toString();
        System.out.println(summary);

        // also save to summary.txt
        try(java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter("summary.txt", false))){
            pw.print(summary);
        } catch (java.io.IOException e){
            System.err.println("No se pudo escribir summary.txt: " + e.getMessage());
        }
    }
}

