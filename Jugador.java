import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> cartas;

    public Jugador(String nombre) {
        this.nombre = nombre;
        cartas = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    public void mostrarCartas() {
        System.out.println(nombre + " tiene las siguientes cartas:");
        for (Carta carta : cartas) {
            System.out.println(carta);
        }
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }
}
