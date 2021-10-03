package picotemp;

import org.junit.jupiter.api.Test;
import picotemp.testhelpers.TestUtils;
import picotemp.testhelpers.TestUtils.CommandResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    @Test
    public void showHelp() {
        final CommandResult result = TestUtils.testCommand(new Main(), "-h");

        assertTrue(result.stdout.contains("Just a template for creating CLIs with PicoCLI."));
        assertEquals(0, result.exitCode);
    }

    @Test
    public void noArgsProvided() {
        final CommandResult result = TestUtils.testCommand(new Main());

        assertTrue(result.stderr.contains("ERROR: No subcommand provided. See help above for usage."));
        assertEquals(1, result.exitCode);
    }

}
