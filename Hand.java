import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cartas;

    public Hand() {
        cartas = new ArrayList<>();
    }

    public void agregarCarta(Card carta) {
        cartas.add(carta);
    }

    public void jugarCarta(Card carta) {
        cartas.remove(carta);
    }

    public void mostrarCartas() {
        for (Card carta : cartas) {
            System.out.println(carta);
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
}
