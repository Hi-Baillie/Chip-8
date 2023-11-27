import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Panel extends JPanel implements KeyListener {
    private CPU CPU;
    static int[] keys = new int[16];

    public Panel(CPU CPU){
        this.CPU = CPU;
        setKeys();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    public void paint(Graphics g){
        byte[][] display = CPU.getDisplay();
        for(int i = 0; i < display.length; i++){
            for(int j = 0; j < display[0].length; j++){
                if(display[i][j] == 0){
                    g.setColor(Color.BLACK);
                }
                else{
                    g.setColor(Color.white);
                }
                g.fillRect(i*10,j*10,10,10);
            }
        }


    }

    public static void setKeys(){
        Arrays.fill(keys, -1);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case 49:
                keys[0] = 1;
                break;
            case 50:
                keys[1] = 1;
                break;
            case 51:
                keys[2] = 1;
                break;
            case 52:
                keys[3] = 1;
                break;
            case 81:
                keys[4] = 1;
                break;
            case 87:
                keys[5] = 1;
                break;
            case 69:
                keys[6] = 1;
                break;
            case 82:
                keys[7] = 1;
                break;
            case 65:
                keys[8] = 1;
                break;
            case 83:
                keys[9] = 1;
                break;
            case 68:
                keys[10] = 1;
                break;
            case 70:
                keys[11] = 1;
                break;
            case 90:
                keys[12] = 1;
                break;
            case 88:
                keys[13] = 1;
                break;
            case 67:
                keys[14] = 1;
                break;
            case 86:
                keys[15] = 1;
                break;
            default:
                System.out.println("Invalid key " + e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case 49:
                keys[0] = 0;
                break;
            case 50:
                keys[1] = 0;
                break;
            case 51:
                keys[2] = 0;
                break;
            case 52:
                keys[3] = 0;
                break;
            case 81:
                keys[4] = 0;
                break;
            case 87:
                keys[5] = 0;
                break;
            case 69:
                keys[6] = 0;
                break;
            case 82:
                keys[7] = 0;
                break;
            case 65:
                keys[8] = 0;
                break;
            case 83:
                keys[9] = 0;
                break;
            case 68:
                keys[10] = 0;
                break;
            case 70:
                keys[11] = 0;
                break;
            case 90:
                keys[12] = 0;
                break;
            case 88:
                keys[13] = 0;
                break;
            case 67:
                keys[14] = 0;
                break;
            case 86:
                keys[15] = 0;
                break;
            default:
                System.out.println("Invalid key " + e.getKeyChar());
        }
    }

    @Override
    public boolean isFocusable() {
        return true;
    }

    public static boolean isPressed(){
        for(int i = 0; i < keys.length; i++){
            if(keys[i] == 1){
                return true;
            }
        }
        return false;
    }

    public static boolean isReleased(){
        for(int i = 0; i < keys.length; i++){
            if(keys[i] == 0){
                return true;
            }
        }
        return false;
    }

    public static int getKey(){
        for(int i = 0; i < keys.length; i++){
            if(keys[i] == 1){
                return i;
            }
        }
        return -1;
    }


}
