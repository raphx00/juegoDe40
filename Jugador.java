import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> carton;
    private Mano mano;
    private int puntuacion;
    private int caidas;

    public Jugador(String nombre) {
        this.nombre = nombre;
        carton = new ArrayList<>();
        mano =  new Mano();
        puntuacion = 0;
        caidas = 0;
    }

    public void agregarCarta(Carta carta) {
        carton.add(carta);
    }

    public Mano getMano(){
        return mano;
    }

    public ArrayList<Carta> getCartas() {
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
