package com.dob.demo.file;

import com.dob.resources.ResourceConsumer;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import com.dob.resources.ResourceTypes;
import com.dob.resources.manager.ResourceManager;
import com.dob.resources.manager.ResourceManagerRegistryFactory;
import com.dob.resources_impl.FileProxy;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import org.davic.resources.ResourceProxy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClientApp extends JFrame implements ResourceConsumer {

    public static int APP_ID = 1;
    public static int order;

    private final Logger log = new Logger();

    private final WorkerThread workerThread = new WorkerThread();

    private final ResourceDescriptor descriptor = new ResourceDescriptor(".\\demo.txt", ResourceTypes.RESOURCE_FILE);
    private final ResourceManager resourceManager;

    private FileWriterThread writerThread;
    private FileProxy proxy;

    public ClientApp() throws HeadlessException, IOException {
        super("App " + APP_ID);
        resourceManager = ResourceManagerRegistryFactory.getRegistry().getResourceManager(ResourceTypes.RESOURCE_FILE);
    }

    private void init() throws IOException {

        GraphicsConfiguration gc = getGraphicsConfiguration();

        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(screenSize.getWidth() / 3);

        setExtendedState(MAXIMIZED_VERT);
        setBounds(new Rectangle(width * order, 0, width, (int)screenSize.getHeight() - insets.bottom));

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(log, BorderLayout.CENTER);

        final JButton request = new JButton("Запросить");
        request.setPreferredSize(new Dimension(width, 25));
        request.setMinimumSize(new Dimension(width, 25));
        request.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workerThread.addTask(new Request());
            }
        });

        final JButton release = new JButton("Освободить");
        release.setPreferredSize(new Dimension(width, 25));
        release.setMinimumSize(new Dimension(width, 25));
        release.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workerThread.addTask(new ReleaseTask());
            }
        });

        final JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonPanel.add(request, BorderLayout.NORTH);
        buttonPanel.add(release, BorderLayout.SOUTH);
        buttonPanel.setMinimumSize(new Dimension(width, 50));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setVisible(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        workerThread.start();
    }

    public static void main(String[] args) throws IOException {
        ClientApp.APP_ID = Integer.parseInt(args[0]);
        ClientApp.order = Integer.parseInt(args[1]);
        ClientApp cApp = new ClientApp();
        cApp.init();
    }

    private class Request implements Runnable {

        public void run() {
            try {
                log.addEvent("Отправлен запрос на доступ к \".\\demo.txt\"");
                proxy = (FileProxy)resourceManager.reserve(ClientApp.this, descriptor);
                if (proxy != null) {                    
                    log.addEvent("Получен доступ к ресусру");
                    writerThread = new FileWriterThread(new BufferedOutputStream(proxy.getOutput()));
                    writerThread.start();
                } else {
                    log.addEvent("Получен отказ в доступе к ресусру");
                }
            } catch (ResourceException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private class ReleaseTask implements Runnable {

        public void run() {
            release(proxy);
            try {
                resourceManager.notifyReleased(descriptor);
            } catch (ResourceException e) {
                e.printStackTrace();
            }
        }
    }

    public int getAppId() {
        return APP_ID;
    }

    public int getPriority() {
        return order;
    }

    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        log.addEvent("Получен запрос на освобождение ресурса");
        final boolean answer = JOptionPane.showConfirmDialog(null, "Освободить ресурс?", "Освободить ресурс?",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        if (answer) {
            System.out.println("Release resource");
            release(proxy);
        }

        if (answer) {
            log.addEvent("Согласие отправлено");
        } else {
            log.addEvent("Отказ отправлен");
        }
        return answer;
    }

    public void release(ResourceProxy proxy) {
        writerThread.stopThread();
        try {
            writerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.addEvent("Ресурс освобождён");
    }

    public void notifyReleased(ResourceProxy proxy) {
        try {
            resourceManager.notifyReleased(descriptor);
        } catch (ResourceException e) {
            e.printStackTrace();
        }
    }
}
