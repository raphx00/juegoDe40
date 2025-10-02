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


    public void play40(Player p1, Player p2, Player p3) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        players[0] = p1;
        players[1] = p2;
        players[2] = p3;

        // We will be using base 0 so this would be 3 rounds.

        // We shuffle the players so that its random who goes first
        /* At the moment we dont have a way to identify players so we just shuffle the array */
        Collections.shuffle(Arrays.asList(players));
        
        while(p1.getScore() < 40 && p2.getScore() < 40 && p3.getScore() < 40){
            

            temp_Player = players[0];
            players[0] = players[1];
            players[1] = players[2];
            players[2] = temp_Player;

            deck = new Deck();
            while(round < 3){ 
                
                System.out.println("Puntajes: ");
                for(Player player: players){
                    System.out.println(player.getNombre() + ": " + player.getScore());
                }

                System.out.println("Ronda " + (round + 1));
                for(Player player: players){
                    for(int i = 0; i < 5; i++){
                        player.addCardToHand(deck.repartir());
                    }
                }
                // Now every player has to finish their cards doing one action per turn.
                // At the end of every action we check if the player hasnt won yet.
                for(int i = 0; i < 5; i++){
                    for(Player player: players){

                        // Here we do the game logic.

                        // We show the player the table and its hand.
                        System.out.println("Cartas en la mesa:");
                        table.mostrarCartas();

                        System.out.println("Tus cartas:");
                        player.showHand();

                        // We let the player choose a card from his hand to be played
                        System.out.print("Elige una carta para jugar: "); 
                        while(true){
                            choice = scanner.nextInt();
                            if(choice < 0 || choice >= player.getMano().getSize()) System.out.print("Esa carta no existe. Elige de nuevo.");
                            else break;
                        }

                        playedCard = player.getMano().playCardByIndex(choice);

                        // If the played card is in the table by both sum and number we let the player choose which to take
                        if(table.verificarCarta(playedCard) && table.verifyByPairSum(playedCard)){
                            System.out.println("La carta que jugaste puede ser tomada por valor o por suma. ¿Cómo quieres tomarla?");
                            System.out.println("1- Por valor");
                            System.out.println("2- Por suma");
                            System.out.print("Elige una opción: ");
                            while(true){
                                choice = scanner.nextInt();
                                if(choice != 1 && choice != 2) System.out.print("Esa opción no existe. Elige de nuevo.");
                                else break;
                            }
                            if(choice == 1){
                                // If the played card is the last played one they get 2 points (Caida)
                                if(playedCard == lastPlayedCard) player.agregarPuntos(2);
                                
                                // Takes the played card and the one in the table
                                player.addCardToCarton(playedCard);
                                player.addCardToCarton(table.removeCardByValue(playedCard.getValor()));

                                // We only check when something was removed from the table
                                if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);
                            }
                            else{
                                pair = table.removePairBySum(playedCard);
                                if(pair != null){
                                    // If the played card is the last played one they get 2 points (Caida)
                                    if(playedCard == lastPlayedCard) player.agregarPuntos(2);

                                    // Takes the played card and the ones in the table
                                    player.addCardToCarton(playedCard);
                                    player.addCardToCarton(pair[0]);
                                    player.addCardToCarton(pair[1]);

                                    // We only check when something was removed from the table
                                    if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);
                                }

                                // THIS ELSE SHOULDNT HAPPEN!!!
                                else{
                                    System.out.println("Error: No se pudo encontrar un par que sume el valor de la carta jugada.");
                                    table.agregarCarta(playedCard);
                                }
                            }
                        }
                        
                        // If they play by value as lastplayed, then they get 2 points
                        else if(table.verificarCarta(playedCard)){
                            if(playedCard == lastPlayedCard){
                                player.agregarPuntos(2);
                            }
                                // Takes the played card and the one in the table
                            player.addCardToCarton(playedCard);
                            player.addCardToCarton(table.removeCardByValue(playedCard.getValor()));


                            // We only check when something was removed from the table
                            if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);

                        }
                        else if(table.verifyByPairSum(playedCard)){


                            pair = table.removePairBySum(playedCard);
                                if(pair != null){
                                    // If the played card is the last played one they get 2 points (Caida)
                                    if(playedCard == lastPlayedCard) player.agregarPuntos(2);

                                    // Takes the played card and the ones in the table
                                    player.addCardToCarton(playedCard);
                                    player.addCardToCarton(pair[0]);
                                    player.addCardToCarton(pair[1]);

                                    // We only check when something was removed from the table
                                    if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);
                                }

                                // THIS ELSE SHOULDNT HAPPEN!!!
                                else{
                                    System.out.println("Error: No se pudo encontrar un par que sume el valor de la carta jugada.");
                                    table.agregarCarta(playedCard);
                                }
                        }
                        else table.agregarCarta(playedCard);
                    
                        lastPlayedCard = playedCard;

                        // If the played card is in the table either by number or by a sum the player takes them
                            // Then we check if the next number is in the table, and if it is: the player also takes it
                        // Finally if the table is empty the player earns 2 points if under 38 points.

                        // Else it gets added and removed form the player hand.

                        // We check if the player hasnt won yet
                        // We refresh a Last played card variable.
                    }
                }

                round += 1;
            }
        

            // Last round
            System.out.println("Ronda 4");
                for(Player player: players){
                    for(int i = 0; i < 3; i++){
                        player.addCardToHand(deck.repartir());
                    }
                }
                table.agregarCarta(deck.repartir());
                // Now every player has to finish their cards doing one action per turn.
                // At the end of every action we check if the player hasnt won yet.
                for(int i = 0; i < 3; i++){
                    for(Player player: players){

                        // Here we do the game logic.

                        // We show the player the table and its hand.
                        System.out.println("Cartas en la mesa:");
                        table.mostrarCartas();

                        System.out.println("Tus cartas:");
                        player.showHand();

                        // We let the player choose a card from his hand to be played
                        System.out.print("Elige una carta para jugar: "); 
                        while(true){
                            choice = scanner.nextInt();
                            if(choice < 0 || choice >= player.getMano().getSize()) System.out.print("Esa carta no existe. Elige de nuevo.");
                            else break;
                        }

                        playedCard = player.getMano().playCardByIndex(choice);

                        // If the played card is in the table by both sum and number we let the player choose which to take
                        if(table.verificarCarta(playedCard) && table.verifyByPairSum(playedCard)){
                            System.out.println("La carta que jugaste puede ser tomada por valor o por suma. ¿Cómo quieres tomarla?");
                            System.out.println("1- Por valor");
                            System.out.println("2- Por suma");
                            System.out.print("Elige una opción: ");
                            while(true){
                                choice = scanner.nextInt();
                                if(choice != 1 && choice != 2) System.out.print("Esa opción no existe. Elige de nuevo.");
                                else break;
                            }
                            if(choice == 1){
                                // If the played card is the last played one they get 2 points (Caida)
                                if(playedCard == lastPlayedCard) player.agregarPuntos(2);
                                
                                // Takes the played card and the one in the table
                                player.addCardToCarton(playedCard);
                                player.addCardToCarton(table.removeCardByValue(playedCard.getValor()));

                                // We only check when something was removed from the table
                                if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);
                            }
                            else{
                                pair = table.removePairBySum(playedCard);
                                if(pair != null){
                                    // If the played card is the last played one they get 2 points (Caida)
                                    if(playedCard == lastPlayedCard) player.agregarPuntos(2);

                                    // Takes the played card and the ones in the table
                                    player.addCardToCarton(playedCard);
                                    player.addCardToCarton(pair[0]);
                                    player.addCardToCarton(pair[1]);

                                    // We only check when something was removed from the table
                                    if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);
                                }

                                // THIS ELSE SHOULDNT HAPPEN!!!
                                else{
                                    System.out.println("Error: No se pudo encontrar un par que sume el valor de la carta jugada.");
                                    table.agregarCarta(playedCard);
                                }
                            }
                        }
                        
                        // If they play by value as lastplayed, then they get 2 points
                        else if(table.verificarCarta(playedCard)){
                            if(playedCard == lastPlayedCard){
                                player.agregarPuntos(2);
                            }
                            // Takes the played card and the one in the table
                            player.addCardToCarton(playedCard);
                            player.addCardToCarton(table.removeCardByValue(playedCard.getValor()));


                            // We only check when something was removed from the table
                            if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);

                        }
                        else if(table.verifyByPairSum(playedCard)){


                            pair = table.removePairBySum(playedCard);
                                if(pair != null){
                                    // If the played card is the last played one they get 2 points (Caida)
                                    if(playedCard == lastPlayedCard) player.agregarPuntos(2);

                                    // Takes the played card and the ones in the table
                                    player.addCardToCarton(playedCard);
                                    player.addCardToCarton(pair[0]);
                                    player.addCardToCarton(pair[1]);

                                    // We only check when something was removed from the table
                                    if(table.verificarLimpia() && player.getScore() < 38) player.agregarPuntos(2);
                                }

                                // THIS ELSE SHOULDNT HAPPEN!!!
                                else{
                                    System.out.println("Error: No se pudo encontrar un par que sume el valor de la carta jugada.");
                                    table.agregarCarta(playedCard);
                                }
                        }
                        else table.agregarCarta(playedCard);
                    
                        lastPlayedCard = playedCard;

                        // If the played card is in the table either by number or by a sum the player takes them
                            // Then we check if the next number is in the table, and if it is: the player also takes it
                        // Finally if the table is empty the player earns 2 points if under 38 points.

                        // Else it gets added and removed form the player hand.

                        // We check if the player hasnt won yet
                        // We refresh a Last played card variable.
                    }
                }

                round += 1;
            }
            System.out.println("El ganador es: ");

            if(p1.getScore() > 40) System.out.println(p1.getNombre());
            if(p2.getScore() > 40) System.out.println(p2.getNombre());
            if(p3.getScore() > 40) System.out.println(p3.getNombre());

            scanner.close();
        }
    }

