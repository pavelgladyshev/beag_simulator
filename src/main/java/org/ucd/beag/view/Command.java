package org.ucd.beag.view;

public class Command {

    public static final int NONE = 0;
    public static final int STEP = 1;
    public static final int CONTINUE = 2;
    public static final int STOP = 3;
    public static final int DISPLAY_MEM = 4;
    public static final int QUIT = 5;

    public static final int HELP = 6;


    public int cmd;
    public int address;
    public int size;

    public Command(int cmd, int address, int size) {
        this.cmd = cmd;
        this.address = address;
        this.size = size;
    }
}
