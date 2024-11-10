package org.ucd.beag;

import org.ucd.beag.model.*;
import org.ucd.beag.view.Command;
import org.ucd.beag.view.UI;
import org.ucd.beag.view.gui.WindowDisplay;
import org.ucd.beag.view.gui.WindowKeyboard;
import org.ucd.beag.view.gui.WindowUI;
import org.ucd.beag.view.text.TextUI;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws IOException {

        ROM16 programRom;
        File romFile;

        if (args.length < 1) {
            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
            if (fc.showDialog(null, "Open Logisim v2.0 ROM file")==JFileChooser.APPROVE_OPTION) {
                romFile = fc.getSelectedFile();
            } else {
                System.out.println("Usage: java -jar beag_simulator-1.0.jar <logisim_v2.0_memory_file>");
                return;
            }
        } else {
            romFile = new File(args[0]);
        }

        programRom = new ROM16(0x2000,romFile);

        MemorySystem16 dataMemory = new MemorySystem16();
        dataMemory.add(0x2000,0x2000,new RAM16(256));
        dataMemory.add(0x4000,1,new WindowDisplay());
        dataMemory.add(0x4001,2,new WindowKeyboard());

        CPU cpu = new CPU(new int[]{0,0,0,0,0,0,0,0}, 0, programRom,dataMemory);

        // for console operation
        // UI ui = new TextUI(System.in,System.out);
        UI ui = new WindowUI();

        Command c;

        ui.displayWelcome();
        do {
            ui.displayState(cpu);
            c = ui.getCommand();
            switch (c.cmd) {
                case Command.STEP -> cpu.step();
                case Command.DISPLAY_MEM -> ui.displayMemory(dataMemory,c.address,c.size);
                case Command.HELP -> ui.displayHelp();
                case Command.CONTINUE -> {
                    while(ui.getCommand().cmd == Command.NONE) {
                        cpu.step();
                        try {
                            sleep(30);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                default -> {}
            }
        } while (c.cmd != Command.QUIT);
        System.exit(0);
    }
}