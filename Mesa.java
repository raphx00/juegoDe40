import java.util.ArrayList;

public class Mesa {
    private ArrayList<Carta> cartasEnMesa;

    public Mesa() {
        cartasEnMesa = new ArrayList<>();
    }

    public void agregarCarta(Carta carta) {
        cartasEnMesa.add(carta);
    }

    public Carta quitarCarta(Carta carta) {
        if (cartasEnMesa.remove(carta)) {
            return carta;
        }
        return null;
    }

    public void mostrarCartas() {
        for (Carta carta : cartasEnMesa) {
            System.out.println(carta);
        }
    }

}
