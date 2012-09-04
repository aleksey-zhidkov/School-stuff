package com.dob.demo.file;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FileMonitor extends JPanel implements Runnable {

    private final JTextArea data = new JTextArea();

    private final File file;

    public FileMonitor (File file) {
        super(new BorderLayout());
        this.file = file;
        data.setEditable(false);
        data.setLineWrap(true);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Τΰιλ " + file.getName()));
        final JScrollPane scrollPane = new JScrollPane(data);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setValue(String old) {
        data.setText(old);
        repaint();
    }

    public void run() {

        while (true) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(file));
                StringBuffer value = new StringBuffer();
                String line;
                while ((line = br.readLine()) != null) {
                    value.insert(0, line + "\n");
                }
                setValue(value.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
