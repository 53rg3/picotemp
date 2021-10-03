package picotemp.commands.run;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;
import picotemp.Main;
import picotemp.utils.Print;

import static org.junit.jupiter.api.Assertions.*;

class ShowOptionsTest {

    @Test
    public void run() {
        Print.useReadableStreams();
        new CommandLine(new Main()).execute("show_options", "--arr", "aaaa", "--brr", "bbbb", "-f");

        String output = Print.getStdOut();
        assertTrue(output.contains("--arr: aaaa"));
        assertTrue(output.contains("--brr: bbbb"));
        assertTrue(output.contains("--limit: 10"));
        assertTrue(output.contains("--flag: true"));

        Print.useSystemOut();
    }

}
