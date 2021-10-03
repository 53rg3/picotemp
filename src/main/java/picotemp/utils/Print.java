package picotemp.utils;

import picocli.CommandLine.Help.Ansi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class Print {
    private Print() {
    }

    public static final ReadableStream readableOut = new ReadableStream();
    private static final ReadableStream readableErr = new ReadableStream();
    private static final PrintStream systemOut = System.out;
    private static final PrintStream systemErr = System.err;

    static {
        enableAnsiColoring();
    }

    /**
     * Enabled by default
     */
    public static void enableAnsiColoring() {
        System.setProperty("picocli.ansi", "true");
    }

    /**
     * No ANSI noise when working with command line outputs as strings
     */
    public static void disableAnsiColoring() {
        System.setProperty("picocli.ansi", "false");
    }

    /**
     * Sets System.out & System.err to the original stream provided by the JVM
     */
    public static void useSystemOut() {
        System.setOut(systemOut);
        System.setErr(systemErr);
    }

    /**
     * Sets System.out & System.err to ReadableStreams which you read via getStdOut() & getStdErr()
     */
    public static void useReadableStreams() {
        System.setOut(readableOut.asPrintStream());
        System.setErr(readableErr.asPrintStream());
    }

    /**
     * Gets the console output as string, if you enabled useReadableStreams(), otherwise this will be empty.
     */
    public static String getStdOut() {
        return readableOut.flushToString();
    }

    /**
     * Gets the console output as string, if you enabled useReadableStreams(), otherwise this will be empty.
     */
    public static String getStdErr() {
        return readableErr.flushToString();
    }

    public static void feedToSystemOut(final InputStream inputStream) {
        try {
            final byte[] buf = new byte[8192];
            int n;
            while ((n = inputStream.read(buf)) > 0) {
                System.out.write(buf, 0, n);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void feedToSystemErr(final InputStream inputStream) {
        try {
            final byte[] buf = new byte[8192];
            int n;
            while ((n = inputStream.read(buf)) > 0) {
                System.err.write(buf, 0, n);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void success(final String text) {
        System.out.printf(
                Ansi.AUTO.string("@|fg(green) %s|@\n"),
                text);
    }

    public static void error(final String text) {
        System.err.printf(
                Ansi.AUTO.string("@|fg(red) %s|@\n"),
                text);
    }

    public static void info(final String text) {
        System.out.println(text);
    }

    public static void warning(final String text) {
        System.out.printf(
                Ansi.AUTO.string("@|fg(yellow) %s|@\n"),
                text);
    }

    private static class ReadableStream {

        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        private final PrintStream printStream = new PrintStream(this.baos);

        public PrintStream asPrintStream() {
            return this.printStream;
        }

        public String flushToString() {
            final String stream = this.baos.toString();
            this.baos.reset(); // flush() on OutputStream does nothing, see JavaDoc comments
            return stream;
        }
    }

}
