package org.ucd.beag.view.gui;

import org.ucd.beag.model.Display;

import javax.swing.*;
import java.awt.*;

public class WindowDisplay extends Display {

    JFrame window;
    JTextArea textArea;
    JTextAreaOutputStream out;

    public WindowDisplay() {
        window = new JFrame("Beag Display");
        textArea = new JTextArea(8,32);
        textArea.setFont(new Font("Monospaced",Font.PLAIN,24));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(false);
        textArea.setEditable(false);
        out = new JTextAreaOutputStream(textArea);
        window.setLayout(new BorderLayout());
        window.add(textArea,BorderLayout.CENTER);
        //window.setAlwaysOnTop(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
    public void printChar(int c) {
        out.write(c & 0x7f);
        out.flush();
    }
}
