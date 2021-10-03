package picotemp;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import picotemp.commands.run.Run;
import picotemp.commands.run.ShowOptions;
import picotemp.utils.ConfigLoader;
import picotemp.utils.Print;

import static picotemp.utils.SpecUtil.noArgsWereProvided;

@Command(name = "picotemp",
        mixinStandardHelpOptions = true,
        version = "picotemp - Version 0.1",
        description = "Just a template for creating CLIs with PicoCLI.",
        subcommands = {
                Run.class,
                ShowOptions.class
        })
public class Main implements Runnable {

    @Spec
    CommandSpec spec;

    public static Config config = ConfigLoader.load();

    @Override
    public void run() {

        if (noArgsWereProvided(this.spec)) {
            this.spec.commandLine().execute("-h");
            Print.error("ERROR: No subcommand provided. See help above for usage.");
            System.exit(1);
        }
    }

    public static void main(final String... args) {
        final int exitCode = new CommandLine(new Main()).execute(args);
        System.out.println();
        System.exit(exitCode);
    }
}
