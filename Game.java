//Este Juego es 40 para 3 jugadores.
import java.util.Collections;
import java.util.Scanner;
import java.util.Arrays;

public class Game {

    private Deck deck;
    private Card lastPlayedCard = null;
    private Card playedCard;
    private Player[]  players = new Player[3];
    private Table table = new Table();
    private int round = 0;
    private Card[] pair;
    private Player temp_Player;
    private Scanner scanner;


    public void play40(Player p1, Player p2, Player p3) {
        scanner = new Scanner(System.in);

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;

        // We shuffle the players so that its random who goes first
        Collections.shuffle(Arrays.asList(players));
        
        while(p1.getScore() < 40 && p2.getScore() < 40 && p3.getScore() < 40){
            // Rotate players
            temp_Player = players[0];
            players[0] = players[1];
            players[1] = players[2];
            players[2] = temp_Player;

            deck = new Deck();
            
            // Play rounds 1 and 2 (5 cards each)
            while(round < 2){ 
                playRound(round + 1, 5, false);
                round += 1;
            }
        
            // Play round 3 (3 cards each + 1 card on table)
            playRound(3, 3, true);
            round += 1;
            
            System.out.println("El ganador es: ");
            if(p1.getScore() >= 40) System.out.println(p1.getNombre());
            if(p2.getScore() >= 40) System.out.println(p2.getNombre());
            if(p3.getScore() >= 40) System.out.println(p3.getNombre());
        }
        
        scanner.close();
    }
    
    // Method to deal cards to all players
    private void dealCards(int cardsPerPlayer) {
        for(Player player: players){
            for(int i = 0; i < cardsPerPlayer; i++){
                player.addCardToHand(deck.repartir());
            }
        }
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
                takeBySum(player);
            }
        } else if(canTakeByValue) {
            takeByValue(player, showCaidaMessage);
        } else if(canTakeBySum) {
            takeBySum(player);
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
        // Check for "Caida" (playing same card as last played)
        if(playedCard.getValor().equals(lastPlayedCard != null ? lastPlayedCard.getValor() : "")) {
            if(showCaidaMessage) {
                System.out.println("LA CAIDA SI FUNCIONA!!!!!!!");
            }
            player.agregarPuntos(2);
        }
        
        // Take the cards
        player.addCardToCarton(playedCard);
        player.addCardToCarton(table.removeCardByValue(playedCard.getValor()));

        // Check for table clear bonus
        if(table.verificarLimpia() && player.getScore() < 38) {
            player.agregarPuntos(2);
        }
    }
    
    // Method to take cards by sum
    private void takeBySum(Player player) {
        pair = table.removePairBySum(playedCard);
        if(pair != null){
            // Check for "Caida" 
            if(playedCard.getValor().equals(lastPlayedCard != null ? lastPlayedCard.getValor() : "")) {
                player.agregarPuntos(2);
            }

            // Take the cards
            player.addCardToCarton(playedCard);
            player.addCardToCarton(pair[0]);
            player.addCardToCarton(pair[1]);

            // Check for table clear bonus
            if(table.verificarLimpia() && player.getScore() < 38) {
                player.agregarPuntos(2);
            }
        } else {
            // This shouldn't happen
            System.out.println("Error: No se pudo encontrar un par que sume el valor de la carta jugada.");
            table.agregarCarta(playedCard);
        }
    }
}

