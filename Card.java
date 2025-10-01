
// manejo de cada carta y sus porpiedades
public class Card {
    private String palo;
    private String valor;

    public Card(String valor, String palo) {
        this.valor = valor;
        this.palo = palo;
    }

    public String getPalo() {
        return palo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor + " de " + palo;
    }
}

