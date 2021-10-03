package picotemp.commands.run;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import picotemp.Main;
import picotemp.utils.Print;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RunTest {

    @Test
    void run() {
        Print.useReadableStreams();
        new CommandLine(new Main()).execute("run", "pwd | ls -la");

        String stdout = Print.getStdOut();
        assertTrue(Pattern.compile("-rw-rw-r--.*readme.md").matcher(stdout).find());

        Print.useSystemOut();
    }

}
