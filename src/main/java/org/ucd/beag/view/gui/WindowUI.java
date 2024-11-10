package org.ucd.beag.view.gui;

import org.ucd.beag.model.CPU;
import org.ucd.beag.model.Memory;
import org.ucd.beag.view.Command;
import org.ucd.beag.view.UI;
import org.ucd.beag.view.text.TextUI;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class WindowUI extends JFrame implements UI {

    JTextArea outputArea;
    JTextField inputField;
    JTextAreaOutputStream out;
    JTextFieldInputStream in;
    TextUI ui;

    public WindowUI() {

        setTitle("Beag simulator console");
        setLayout(new BorderLayout());

        Font f = new Font("Monospaced",Font.PLAIN,24);

        outputArea = new JTextArea(25,80);
        outputArea.setFont(f);
        outputArea.setEditable(false);
        outputArea.setWrapStyleWord(false);
        outputArea.setLineWrap(true);
        out = new JTextAreaOutputStream(outputArea);
        add(outputArea,BorderLayout.CENTER);
        add( new JScrollPane( outputArea ),BorderLayout.EAST  );

        JPanel p=new JPanel();
        p.setLayout(new BorderLayout());
        JLabel l= new JLabel("> ");
        l.setFont(f);
        p.add(l,BorderLayout.WEST);
        inputField = new JTextField(78);
        inputField.setFont(f);
        in = new JTextFieldInputStream(inputField,out);
        p.add(inputField,BorderLayout.CENTER);
        add(p,BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Point loc = getLocation();
        loc.translate(600,0);
        setLocation(loc);
        setVisible(true);
        inputField.grabFocus();
        ui=new TextUI(in,out);

    }

    @Override
    public void displayWelcome() {
        ui.displayWelcome();
    }

    @Override
    public void displayState(CPU cpu) throws IOException {
        ui.displayState(cpu);
    }

    @Override
    public void displayMemory(Memory mem, int start, int size) throws IOException {
        ui.displayMemory(mem,start,size);
    }

    @Override
    public void displayHelp() {
        ui.displayHelp();
    }


    @Override
    public Command getCommand() throws IOException {
        return ui.getCommand();
    }
}
