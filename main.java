public class main {

    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenido al juego de 40 de tres jugadores!");
        Game game = new Game();

        Player p1 = new Player("Nombre1");
        Player p2 = new Player("Nombre2");
        Player p3 = new Player("Nombre3");

        // Always run interactive console play
        game.play40(p1, p2, p3);
        
    }
}
    