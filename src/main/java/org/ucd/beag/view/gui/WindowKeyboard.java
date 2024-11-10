package org.ucd.beag.view.gui;

import org.ucd.beag.model.Keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class WindowKeyboard extends Keyboard {

    JFrame window;
    JTextField textField;

    LinkedList<Character> input = new LinkedList<Character>();

    public WindowKeyboard() {
        window = new JFrame("Beag Keyboard");
        textField = new JTextField("",32);
        textField.setFont(new Font("Monospaced",Font.PLAIN,24));
        textField.setEnabled(true);
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getComponent() == textField) {
                  input.add(e.getKeyChar());
                  e.consume();
                }
            }
        });
        window.setLayout(new BorderLayout());
        window.add(textField,BorderLayout.CENTER);
        //window.setAlwaysOnTop(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        Point loc = window.getLocation();
        loc.translate(0,350);
        window.setLocation(loc);
        window.setVisible(true);
    }


    @Override
    public int available() {
        return input.size();
    }

    @Override
    public int readChar() {
        String text = textField.getText();
        if (!text.isEmpty()) {
            textField.setText(text.substring(1));
        }
        if (input.isEmpty()) {
            return 0;
        } else {
            Character c = input.poll();
            return c & 0x7f;
        }
    }
}


