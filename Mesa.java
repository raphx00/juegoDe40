import java.util.Stack;

public class Mesa {
    private Stack<Carta> cartasEnMesa;

    public Mesa() {
        cartasEnMesa = new Stack<>();
    }

    public void agregarCarta(Carta carta) {
        cartasEnMesa.push(carta);
    }

    public void mostrarCartas() {
        for (Carta carta : cartasEnMesa) {
            System.out.println(carta);
        }
    }

}
