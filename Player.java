import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> carton;
    private Hand mano;
    private int puntuacion;
    private int caidas;


    public Player(String nombre) {
        this.name = nombre;
        carton = new ArrayList<>();
        mano =  new Hand();
        puntuacion = 0;
        caidas = 0;
    }

    public Player(){
        this.name = "Jugador";
        carton = new ArrayList<>();
        mano =  new Hand();
        puntuacion = 0;
        caidas = 0;
    }

    public void agregarCarta(Card carta) {
        carton.add(carta);
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
}
