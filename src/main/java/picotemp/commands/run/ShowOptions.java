package picotemp.commands.run;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picotemp.utils.Print;

@Command(name = "show_options", description = "Echo the options provided")
final public class ShowOptions implements Runnable {

    @Option(names = {"-a", "--arr"}, description = "Arr some string")
    String arr = "";

    @Option(names = {"-b", "--brr"}, description = "Brr some string")
    String brr = "";

    @Option(names = {"-l", "--limit"}, description = "An int with default: 10")
    int limit = 10;

    @Option(names = {"-f", "--flag"}, description = "Just a flag, no value")
    boolean flag;


    @Override
    public void run() {

        Print.success("Here are your flags:");
        Print.info("--arr: " + this.arr);
        Print.info("--brr: " + this.brr);
        Print.info("--limit: " + this.limit);
        Print.info("--flag: " + this.flag);

    }

}
