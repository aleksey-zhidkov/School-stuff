package com.dob.demo.file;

import com.dob.resources.*;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ResourceManagerApp extends JFrame implements IPCHandlerListener {

    private final Logger log = new Logger();

    private final FileMonitor fileMonitor = new FileMonitor(new File(".\\demo.txt"));

    private Thread monitorThread;

    private int order = 1;

    public ResourceManagerApp() throws HeadlessException {
        super("Resource manager App");
    }

    private void init() {
        GraphicsConfiguration gc = getGraphicsConfiguration();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.getWidth() / 3);

        setExtendedState(MAXIMIZED_VERT);
        setBounds(new Rectangle(width * order, 0, width, (int) screenSize.getHeight() - insets.bottom));

        getContentPane().setLayout(new GridLayout(2, 1));

        getContentPane().add(log);
        getContentPane().add(fileMonitor);

        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        SocketIPCHandler server = new SocketIPCHandler();
        server.setListener(this);
        server.init();
    }

    public static void main(String[] args) {
        ResourceManagerApp rmApp = new ResourceManagerApp();
        rmApp.init();
    }

    public void connectorOpened() {
        log.addEvent("��������� ������");
    }

    public void newLink() {
        log.addEvent("������� ����� �����������");
    }

    public void delegateRecieved(ResourceConsumer conumer) {
        log.addEvent("������� �������, [" + conumer.getAppId() +
                ", " + conumer.getPriority() + "]");
    }

    public void releaseMessageReceived() {
        log.addEvent("�������� ��������� �� ������������ �������");
    }

    public void resourceRequested(ResourceDescriptor resource, ResourceConsumer consumer) {
        log.addEvent("������� ������ �� ������ � \"" + resource.getDescriptor() + "\" �� " + consumer.getAppId());
    }

    public void releaseRequestDenied() {
        log.addEvent("������������ ����� � �������");
    }

    public void releaseRequestAccepted() {
        log.addEvent("������������ ����������� �� ������");
        if (monitorThread == null) {
            monitorThread = new Thread(fileMonitor);
            monitorThread.start();
        }
    }
}
