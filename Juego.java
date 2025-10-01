//Este Juego es 40 para 3 jugadores.
import java.util.Collections;
import java.util.Arrays;

public class Juego {

    private Baraja deck;
    private Jugador[]  players = new Jugador[3];
    private Mesa mesa = new Mesa();
    private boolean winner = false; 
    private int round = 0;

    public void play40(){

        // We will be using base 0 so this would be 3 rounds.
        while(round < 3){
            
            // We shuffle the players so that its random who goes first
            Collections.shuffle(Arrays.asList(players));

            // We have to deal the cards, 5 for each player
            for(Jugador player: players){
                for(int i = 0; i < 5; i++){
                    player.agregarCarta(deck.repartir());
                }
            }

            // Now every player has to finish their cards doing one action per turn.
            // At the end of every action we check if the player hasnt won yet.
            for(int i = 0; i < 5; i++){
                for(Jugador player: players){

                    // Here we do the game logic.

                    // We show the player the table and its hand.
                    // We let the player choose a card from his hand to be played
                    
                    // If the played card is in the table by both sum and number we let the player choose which to take
                    // If they play by number and it has the same value as lastplayed, then they get 2 points
                    
                    // If the played card is in the table either by number or by a sum the player takes them
                        // If they play by number and it has the same value as lastplayed, then they get 2 points
                    
                        // Then we check if the next number is in the table, and if it is: the player also takes it
                        // Finally if the table is empty the player earns 2 points if under 38 points.

                    // Else it gets added and removed form the player hand.

                    // We check if the player hasnt won yet
                    // We refresh a Last played card variable.
                }
            }

            round += 1;
        }

        // This is the final round
        // We have to deal the cards, 3 for each player
        for(Jugador player: players){
            for(int i = 0; i < 5; i++){
                player.agregarCarta(deck.repartir());
            }
        }
        // We add the last card to the table.
        mesa.agregarCarta(deck.repartir());

        // We repeat game logic but just 3 times
        for(int i = 0; i < 3; i++){
                for(Jugador player: players){

                }
            }


 }

}
