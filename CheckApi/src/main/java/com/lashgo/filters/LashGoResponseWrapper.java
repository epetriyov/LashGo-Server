package com.lashgo.filters;

import com.lashgo.model.CheckApiHeaders;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class LashGoResponseWrapper extends HttpServletResponseWrapper {
    private TeeServletOutputStream mTeeOutputStream;

    public String getContent() {
        return new String(mByteArrayOutputStream.toByteArray());
    }

    private static class TeeOutputStream extends OutputStream {
        private OutputStream mChainStream;
        private OutputStream mTeeStream;

        public TeeOutputStream(OutputStream chainStream, OutputStream teeStream) {
            mChainStream = chainStream;
            mTeeStream = teeStream;
        }

        @Override
        public void write(int b) throws IOException {
            mChainStream.write(b);
            mTeeStream.write(b);
            mTeeStream.flush();
        }

        @Override
        public void close() throws IOException {
            flush();
            mChainStream.close();
            mTeeStream.close();
        }

        @Override
        public void flush() throws IOException {
            mChainStream.close();
        }
    }

    public class TeeServletOutputStream extends ServletOutputStream {
        private final TeeOutputStream targetStream;

        public TeeServletOutputStream(OutputStream one, OutputStream two) {
            targetStream = new TeeOutputStream(one, two);
        }

        @Override
        public void write(int b) throws IOException {
            this.targetStream.write(b);
        }

        @Override
        public void flush() throws IOException {
            super.flush();
            this.targetStream.flush();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.targetStream.close();
        }
    }

    private ByteArrayOutputStream mByteArrayOutputStream;

    public LashGoResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        mByteArrayOutputStream = new ByteArrayOutputStream();
        mTeeOutputStream = new TeeServletOutputStream(super.getResponse().getOutputStream(), mByteArrayOutputStream);
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return super.getResponse().getWriter();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return mTeeOutputStream;
    }

}