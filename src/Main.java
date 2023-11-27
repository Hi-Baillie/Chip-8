import java.io.IOException;

public class Main extends Thread {

    private CPU CPU;
    private Frame frame;

    public Main() throws IOException {
        CPU = new CPU();
        CPU.loadGame("rom/pong2.c8");
        frame = new Frame(CPU);
    }

    @Override
    public void run() {
        while (true){
            CPU.start();
            CPU.loadKeys();
            Panel.setKeys();
            if(CPU.needRedraw()){
                frame.repaint();
                CPU.removeDrawFlag();
            }
            try {
                Thread.sleep(16);
                CPU.timers();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.start();
    }
}
