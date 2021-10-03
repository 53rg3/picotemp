package picotemp.commands.run;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picotemp.utils.Print;

import java.io.IOException;

@Command(name = "run", description = "Execute arbitrary commands, piping also works")
final public class Run implements Runnable {

    @Parameters(index = "0", description = "Some command")
    private String command;

    @Override
    public void run() {

        try {
            final Process process = new ProcessBuilder("/bin/bash", "-c", this.command)
                    .start();
            Print.feedToSystemOut(process.getInputStream());

            final int exitCode = process.waitFor();
            if (exitCode != 0) {
                Print.feedToSystemErr(process.getErrorStream());
                System.exit(exitCode);
            }

        } catch (final IOException | InterruptedException e) {
            throw new IllegalStateException("Failed to run command: " + this.command);
        }
    }

}
