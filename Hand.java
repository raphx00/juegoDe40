import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cartas;

    public Hand() {
        cartas = new ArrayList<>();
    }

    public void agregarCarta(Card carta) {
        if (carta == null) {
            System.err.println("Warning: intento de agregar una carta nula a la mano. Ignorando.");
            return;
        }
        cartas.add(carta);
    }

    public void jugarCarta(Card carta) {
        cartas.remove(carta);
    }

    public Card playCardByIndex(int index){
        return cartas.remove(index);
    }

    public void mostrarCartas() {
        for (Card carta : cartas) {
            if (carta == null) {
                System.out.println("<carta nula>");
                continue;
            }
            System.out.println(carta.toString());
        }
    }

    public boolean verificarMesa(){
        boolean haymesa = true;
        Card carta = cartas.get(0);
        for( Card c: cartas){
            if(c.getValor().equals(carta.getValor()))
                haymesa = true;
            else{
                haymesa = false;
                break;
            }
        }
        return haymesa;
    }

    public int getSize(){
        return cartas.size();
    }
}
