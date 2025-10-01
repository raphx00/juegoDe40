public class main {

    public static void main(String[] args) throws Exception {
        System.out.println("Bienvenido al juego de 40 de tres jugadores!");
        Game game = new Game();

        Player p1 = new Player();
        Player p2 = new Player();
        Player p3 = new Player();

        game.play40(p1, p2, p3);
        
    }
}
    