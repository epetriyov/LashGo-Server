package com.lashgo.filters;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * Created by Eugene on 06.03.2015.
 */
public class LashGoResponseWrapper extends HttpServletResponseWrapper {
    private TeeServletOutputStream tee;
    private ByteArrayOutputStream bos;
    private PrintWriter teeWriter;

    public LashGoResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public String getContent() {
        return bos.toString();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (tee == null) {
            bos = new ByteArrayOutputStream();
            tee = new TeeServletOutputStream(getResponse().getOutputStream(), bos);
        }
        return tee;

    }

    @Override
    public void flushBuffer() throws IOException {
        if (tee != null) {
            tee.flush();
        }
        if (teeWriter != null) {
            teeWriter.flush();
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (teeWriter == null) {
            teeWriter = new PrintWriter(new OutputStreamWriter(getOutputStream()));
        }
        return teeWriter;
    }

    public class TeeServletOutputStream extends ServletOutputStream {

        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int arg0) throws IOException {
            this.targetStream.write(arg0);
        }

        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

}

