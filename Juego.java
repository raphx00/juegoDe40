public class Juego {
    private Baraja baraja;
    private Jugador[] jugadores;

    public Juego(String[] nombresJugadores) {
        baraja = new Baraja();
        jugadores = new Jugador[3];
        for (int i = 0; i < 3; i++) {
            jugadores[i] = new Jugador(nombresJugadores[i]);
        }
        repartirCartas();
    }

    public void repartirCartas() {
        for (int i = 0; i < 40; i++) {
            jugadores[i % 3].agregarCarta(baraja.repartir());
        }
    }

    public void jugar() {
        int turno = 0;
        while (turno < 40) {
            System.out.println("\nTurno " + (turno + 1));
            for (int i = 0; i < 3; i++) {
                jugadores[i].getMano().mostrarCartas();
            }
            turno++;
            // Aquí puedes agregar más reglas para determinar las jugadas de los jugadores
        }
    }

    public static void main(String[] args) {
        String[] nombres = {"Jugador 1", "Jugador 2", "Jugador 3"};
        Juego juego = new Juego(nombres);
        juego.jugar();
    }
}
