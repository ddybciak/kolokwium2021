public class Main {
    public static void main(String[] args) {
        new Thread(Server::start).start();
        new App().setVisible(true);
    }
}
