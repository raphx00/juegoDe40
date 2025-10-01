import java.util.ArrayList;

public class Player{
    private ArrayList<Card> carton;
    private Hand mano;
    private int puntuacion;
    private int caidas;


    //Creates a new player with an empty hand and pile
    public Player(){
        carton = new ArrayList<>();
        mano =  new Hand();
        puntuacion = 0;
        caidas = 0;
    }

    // Adds a card to its own card pile
    public void addCardToCarton(Card carta) {
        carton.add(carta);
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

    public void showHand(){
        mano.mostrarCartas();
    }
}
