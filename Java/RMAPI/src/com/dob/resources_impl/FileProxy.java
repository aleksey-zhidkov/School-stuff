package com.dob.resources_impl;

import com.dob.resources.manager.AbstractResourceProxy;
import com.dob.resources.ResourceDescriptor;
import org.davic.resources.ResourceClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public class FileProxy extends AbstractResourceProxy {

    private final File delegate;

    private OutputStream outputStream;
    private boolean append = false;

    FileProxy(ResourceClient client, File delegate, ResourceDescriptor descriptor) throws FileNotFoundException {
        super(client, descriptor);

        this.delegate = delegate;
    }

    public OutputStream getOutput() throws FileNotFoundException {
        if (!hasLink) {
            throw new IllegalStateException("Proxy isn't connected to link");
        }
        if (outputStream == null) {
            System.out.println("Creating deleage");
            outputStream = new OutputStreamWrapper();
        }
        return outputStream;
    }

    void setAppend(boolean append) {
        this.append = append;
    }

    protected void close() {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException ignore) {                
            }
            outputStream = null;
        }
    }

    private class OutputStreamWrapper extends OutputStream implements Serializable {

        private OutputStream delegate;

        public OutputStreamWrapper() throws FileNotFoundException {
            System.out.println("Create output stream, append: " + append);
            delegate = new FileOutputStream(FileProxy.this.delegate, append);
        }

        public void write(int b) throws IOException {
            delegate.write(b);
        }

        public void close() throws IOException {
            System.out.println("file proxy closed");
            delegate.close();
        }
    }

}
