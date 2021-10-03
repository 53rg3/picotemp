package picotemp.testhelpers;

import com.github.stefanbirkner.systemlambda.SystemLambda;
import picocli.CommandLine;
import picotemp.utils.Print;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
    private TestUtils() {
    }

    public static String loadResourceFileAsString(final String resourceFileName) {
        try {
            return Files.readString(Paths.get(ClassLoader.getSystemResource(resourceFileName).toURI()));
        } catch (final IOException | URISyntaxException e) {
            throw new IllegalArgumentException("Failed to load resource file.", e);
        }
    }

    public static CommandResult testCommand(final Object command, final String... args) {

        final CommandResult commandResult = new CommandResult();
        final int exitCode;
        try {
            exitCode = SystemLambda.catchSystemExit(() -> {
                Print.useReadableStreams();
                final CommandLine commandLine = new CommandLine(command);
                commandLine.execute(args);

                // Not possible otherwise, because lambda has no return
                commandResult.commandLine = commandLine;
                commandResult.stdout = Print.getStdOut();
                commandResult.stderr = Print.getStdErr();
                Print.useSystemOut();
            });
            commandResult.exitCode = exitCode;
        } catch (final Exception e) {
            throw new IllegalStateException("Failed to exeucte SystemLambda.catchSystemExit()");
        } catch (final Throwable e) {
            // NO OP - SystemLambda.catchSystemExit throws if System.exit() is not called
        }

        return commandResult;
    }

    public static class CommandResult {
        public CommandLine commandLine;
        public String stdout;
        public String stderr;
        public int exitCode;

        // No args constructor because it's needed for SystemLambda.catchSystemExit(Runnable)
        public CommandResult() {
        }
    }

}
