package com.dob.demo.file;

import javax.swing.*;
import java.awt.*;

public class Logger extends JPanel {

    private final JTextArea log = new JTextArea();

    public Logger() {
        super(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Журнал событий"));
        log.setEditable(false);
        log.setLineWrap(true);
        add(new JScrollPane(log), BorderLayout.CENTER);
    }

    public void addEvent(String event) {
        log.insert(" - " + event + "\n", 0);
    }

}
