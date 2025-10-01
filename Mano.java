import java.util.ArrayList;

public class Mano {

    private ArrayList<Carta> cartas;

    public Mano() {
        cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    public void jugarCarta(Carta carta) {
        cartas.remove(carta);
    }

    public void mostrarCartas() {
        for (Carta carta : cartas) {
            System.out.println(carta);
        }
    }

    public boolean verificarMesa(){
        boolean haymesa = true;
        Carta carta = cartas.get(0);
        for( Carta c: cartas){
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
