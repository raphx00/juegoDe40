import java.util.ArrayList;

public class Player{
    private ArrayList<Card> carton;
    private String nombre;
    private Hand mano;
    private int puntuacion;
    private int caidas;

    public Player(String nombre){
        this.nombre = nombre;
        carton = new ArrayList<>();
        mano =  new Hand();
        puntuacion = 0;
        caidas = 0;
    }

    // Adds a card to its own card pile
    public void addCardToCarton(Card carta) {
        if (carta == null) {
            System.err.println("Warning: intento de agregar una carta nula al carton del jugador '" + nombre + "'. Ignorando.");
            return;
        }
        carton.add(carta);
    }
    public String getNombre(){
        return nombre;
    }
    // Adds card to hand
    public void addCardToHand(Card card){
        mano.agregarCarta(card);
    }

    public Hand getMano(){
        return mano;
    }

    public ArrayList<Card> getCartas() {
        return carton;
    }

    public void agregarPuntos(int puntos) {
        this.puntuacion = this.puntuacion + puntos;
    }

    public boolean ganador(){
        return puntuacion >= 40 || mano.verificarMesa() || caidas == 4;
    }


    public void puntosCarton(){
        int puntos = 0;
        if(carton.size() > 13){
            for ( int i = carton.size() ; i > 13 ; i--){
                puntos++;
            }
            if(puntos % 2 == 1){
                puntos++;
            }
            agregarPuntos(puntos);
        }
    }

    public int getScore(){
        return puntuacion;
    }

    public void showHand(){
        mano.mostrarCartas();
    }

    // Register a "caída" (fall): increment counter and optionally print a message
    public void registrarCaida(boolean imprimir) {
        this.caidas++;
        if (imprimir) {
            System.out.println(this.nombre + " hizo CAÍDA!");
        }
    }
}
