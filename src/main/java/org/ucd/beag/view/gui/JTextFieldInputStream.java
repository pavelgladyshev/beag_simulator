package org.ucd.beag.view.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JTextField;


public class JTextFieldInputStream extends InputStream {
    byte[] contents;
    int pointer = 0;

    OutputStream echo;

    public JTextFieldInputStream(final JTextField text, OutputStream echoStream) {
        JTextFieldInputStream x = this;
        echo = echoStream;
        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    synchronized (x) {
                        contents = (text.getText()+"\n").getBytes();
                        pointer = 0;
                        x.notify();
                    }
                    try {
                        echo.write((text.getText()+"\n").getBytes());
                    } catch (IOException ex) {
                    }
                    text.setText("");
                }
                super.keyReleased(e);
            }
        });
    }

    @Override
    public int read() throws IOException {
        synchronized (this) {
            if (null == contents) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (pointer >= contents.length) {
                contents = null;
                return -1;
            } else {
                return this.contents[pointer++];
            }

        }
    }

    @Override
    public int available() {
        return contents == null ? 0 : (pointer >= contents.length ? 0 : contents.length - pointer);
    }

}

