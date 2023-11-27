import java.io.*;
import java.util.Random;

public class CPU {
    private Random random = new Random();
    private char opcode;
    private char[] memory;
    private char[] v;
    private char I;
    private char pc;
    private byte[][] display;
    private int delay_timer;
    private int sound_timer;
    private int sp;
    private char[] stack;
    private int[] keys = new int[16];
    private boolean needRedraw;

    public CPU(){
        opcode = 0;
        memory = new char[4096];
        v = new char[16];
        I = 0;
        pc = 0x200;
        display = new byte[64][32];
        delay_timer = 0;
        sound_timer = 0;
        sp = 0;
        stack = new char[16];
        needRedraw = false;
        loadFont();
    }

    public void start(){

        char hi = memory[pc++];
        char lo = memory[pc++];

        System.out.println(Integer.toBinaryString(hi));
        System.out.println(Integer.toBinaryString(lo));

        char op  = (char) ((hi << 8) | lo);
        char nnn = (char) (op & 0xFFF);

        char x   = (char) (hi & 15);
        char y   = (char) (lo >> 4);
        char n   = (char) (lo & 15);

        System.out.println("Instruction " + Integer.toHexString(op));

        switch (hi >> 4) {
            case 0x0:
                switch (nnn) {
                    case 0x0E0:
                        for(int i = 0; i < display.length; i++){
                            for(int j = 0; j < display[0].length; j++){
                                display[i][j] = 0;
                            }
                        }
                        needRedraw = true;
                        break;
                    case 0x0EE:
                        pc = stack[--sp];
                        break;
                    default:
                        System.out.println("INVALID OPCODE: " + Integer.toHexString(op));
                        break;
                }
                break;
            case 0x1:
                pc = nnn;
                break;
            case 0x2:
                stack[sp++] = pc;
                pc = nnn;
                break;
            case 0x3:
                if (v[x] == lo)
                    pc += 2;
                break;
            case 0x4:
                if (v[x] != lo)
                    pc += 2;
                break;
            case 0x5:
                if (v[x] == v[y])
                    pc += 2;
                break;
            case 0x6:
                v[x] = lo;
                break;
            case 0x7:
                v[x] = (char) ((v[x] + lo) & 0xFF);
                break;
            case 0x8:
                switch (n) {
                    case 0x0:
                        v[x] = v[y];
                        break;
                    case 0x1:
                        v[x] |= v[y];
                        break;
                    case 0x2:
                        v[x] &= v[y];
                        break;
                    case 0x3:
                        v[x] ^= v[y];
                        break;
                    case 0x4:
                        char vf = (char) (v[x] + v[y]);
                        v[x]    = (char) (vf & 0xFF);
                        v[0xF]  = (char) (vf >> 8);
                        break;
                    case 0x5:
                        vf = (v[x] >= v[y]) ? '\u0001' : '\u0000';
                        v[x]    = (char) ((v[x] - v[y]) & 0xFF);
                        v[0xF]  = (char) vf;
                        break;
                    case 0x6:
                        vf = (char) (v[y] & 1);
                        v[x]    = (char) (v[y] >> 1);
                        v[0xF]  = (char) vf;
                        break;
                    case 0x7:
                        vf = (v[y] >= v[x]) ? '\u0001' : '\u0000';
                        v[x]    = (char) ((v[y] - v[x]) & 0xFF);
                        v[0xF]  = (char) vf;
                        break;
                    case 0xE:
                        vf = (char) (v[y] >> 7);
                        v[x]    = (char) ((v[y] << 1) & 0xFF);
                        v[0xF]  = (char) vf;
                        break;
                    default:
                        System.out.println("INVALID OPCODE: " + Integer.toHexString(op));
                        break;
                }
                break;
            case 0x9:
                switch (n) {
                    case 0x0:
                        if (v[x] != v[y])
                            pc += 2;
                        break;
                    default:
                        System.out.println("INVALID OPCODE: " + Integer.toHexString(op));
                        break;
                }
                break;
            case 0xA:
                I = nnn;
                break;
            case 0xB:
                pc = (char) (nnn + v[0]);
                break;
            case 0xC:
                char rand = (char) random.nextInt(256);
                v[x] = (char) (rand & lo);
                break;
            case 0xD:
                char vx = (char) (v[x] & 0x3F);
                char vy = (char) (v[y] & 0x1F);

                v[0xF] = 0;

                for (char row = 0; row < n; ++row) {
                    if ((row + vy) >= 32) break;
                    char data = memory[I + row];

                    for (char col = 0; col < 8; ++col) {
                        if ((col + vx) >= 64) break;
                        if (((data >> (7 - col)) & 0x1) != 1) continue;

                        y = (char) (vy + row);
                        x = (char) (vx + col);

                        if (display[x][y] == 1)
                            v[0xF] = 1;

                        display[x][y] ^= 1;
                    }
                }
                needRedraw = true;
                break;
            case 0xE:
                switch (lo) {
                    case 0x9E:
                        if(keys[v[x]] == 1)
                            pc+=2;
                        break;
                    case 0xA1:
                        if(keys[v[x]] == -1)
                            pc+=2;
                        break;
                    default:
                        System.out.println("INVALID OPCODE: " + Integer.toHexString(op));
                        break;
                }
                break;
            case 0xF:
                switch (lo) {
                    case 0x07:
                        v[x] = (char) delay_timer;
                        break;
                    case 0x0A:
                        boolean keyPressed = Panel.isPressed();
                        boolean keyReleased = Panel.isReleased();

                        if(keyReleased){
                            if(keyReleased){
                                v[x] = (char) Panel.getKey();
                                pc+=2;
                                break;
                            }
                        }

                        if(!keyPressed){
                            pc-=2;
                            break;
                        }
                        needRedraw = true;
                        break;
                    case 0x15:
                        delay_timer = v[x];
                        break;
                    case 0x18:
                        sound_timer = v[x];
                        break;
                    case 0x1E:
                        I += v[x];
                        break;
                    case 0x29:
                        I = (char) ((v[x] & 0xF) * 5);
                        needRedraw = true;
                        break;
                    case 0x33:
                        memory[I + 0] = (char) (v[x] / 100);
                        memory[I + 1] = (char) ((v[x] / 10) % 10);
                        memory[I + 2] = (char) (v[x] % 10);
                        break;
                    case 0x55:
                        for(int i = 0; i <= x; ++i) {
                            memory[i + I] = (char) v[i];
                            //++I;
                        }
                        break;
                    case 0x65:
                        for(int i = 0; i <= x; i++) {
                            v[i] = (char) (memory[i + I]);
                            // ++I;
                        }
                        break;
                    default:
                        System.out.println("INVALID OPCODE: " + Integer.toHexString(op));
                        break;
                }
                break;
        }

    }



    public byte[][] getDisplay(){
        return display;
    }

    public void loadGame(String file) throws IOException {

        DataInputStream input = new DataInputStream(new FileInputStream(new File(file)));
        try {
            int i = 0;
            while(input.available() > 0){
                memory[0x200 + i] = (char) (input.readByte() & 0xFF);
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }




    }

    public void removeDrawFlag() {
        needRedraw = false;
    }

    public boolean needRedraw() {
        return needRedraw;
    }

    public void loadFont(){
        for(int i = 0; i < Font.font.length; i++){
            memory[0x50 + i] = (char) (Font.font[i] & 0xFF);
        }
    }

    public void loadKeys(){
        for (int i = 0; i < 0xF; i++){
            keys[i] = Panel.keys[i];
        }
    }


    public void timers(){
        if(delay_timer > 0){
            delay_timer--;
        }
        if(sound_timer > 0){
            sound_timer--;
        }
    }

}

