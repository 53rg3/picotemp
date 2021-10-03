# Building the JAR



## Build manually

Compilation is done via `maven-assembly-plugin`. 

Note that you need to adjust the config in `pom.xml`, e.g. `<mainClass>picotemp.Main</mainClass>` & `<argument>picotemp.Main</argument>`.

```bash
mvn clean package assembly:single
```

Run via:

```bash
java -jar ./target/picotemp-0.1.jar run "pwd | ls -la"
```



## Build via `make`

Running `make build` will run:

```bash
mkdir --parents "bin"
mv "target"/"picotemp".jar "bin"/"picotemp".jar
mv "target"/"autocomplete.sh" "bin"/"autocomplete.sh"
```

You might want to adjust the JAR name. Don't forget the `pom.xml`



## Build autocomplete

The autocomplete script is automatically generated via Maven. You can find it at `bin/autocomplete.sh`. Using `make build` will show you some help what to do with it, e.g.

```bash
Test via:
    java -jar /home/cc/Desktop/Programming/Repos/picotemp/bin/picotemp.jar
Put into ~/.bashrc:
    alias picotemp="java -jar /home/cc/Desktop/Programming/Repos/picotemp/bin/picotemp.jar"
    source /home/cc/Desktop/Programming/Repos/picotemp/bin/autocomplete.sh
```



# YAML for config

Parsing is done via Jackson via the helper class `YAML`:

```java
String personYaml = TestUtils.loadResourceFileAsString("person.yaml");

PersonPojo person = YAML.fromYaml(personYaml, PersonPojo.class);

String yaml = YAML.toYaml(person);
```

See `YAMLTest.java` for example.



## Using the `ConfigLoader`

There's a helper `ConfigLoader` which is configured to load `./bin/config.yml` into the `Config` POJO.

```
public static Config config = ConfigLoader.load();
```





# ANSI-colored output

Use helper `Print`:

```java
Print.info("Some message in standard output color");
Print.success("Some message in green");
Print.warning("Some message in yellow");
Print.error("Some message in red");
```





#  Testing



## Using `TestUtils.testCommand()`

`System.out` & `System.err` do NOT produce testable output or you will be forced to use `System.setOut(new PrintStream(out))` (see [docs](https://picocli.info/#_testing_the_output)). Use the `Print` helper because of that.

For testing use the helper `TestUtils.testCommand()` which executes your command and returns the results in the `CommandResult` POJO. E.g.

```java
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
```

See tests for more examples.



## Parsing console output

Testing console output is simplified via the `Print` helper. Whenever you need to parse console output, you can redirect all output via `Print.useReadableStreams()` to `OutputStreams` which can be read afterwards. Internally this uses `System.setOut()` & `System.setErr()`. Concurrency in JUnit is disabled via `junit-platform.properties`, so there shouldn't be any problem.

:exclamation:Overwriting `System.out` is tricky. Make sure to always reset it to its default via `Print.useSystemOut()`.

```java
@Test
public void readableStreams() {
    System.out.println("Before using Print.useReadableStreams()");

    Print.useReadableStreams();
    System.out.println("Message via System.out.println()");
    Print.success("Message to Print.success()");
    Print.error("Message to Print.error()");

    Print.useSystemOut();
    System.out.println("After turning back to Print.useSystemOut()");

    String stdout = Print.getStdOut();
    String stderr = Print.getStdErr();
    assertFalse(stdout.contains("Before using Print.useReadableStreams()"));
    assertTrue(stdout.contains("Message via System.out.println()"));
    assertTrue(stdout.contains("Message to Print.success()"));
    assertTrue(stderr.contains("Message to Print.error()"));
    assertFalse(stdout.contains("After turning back to Print.useSystemOut()"));

    stdout = Print.getStdOut(); // After stream has been flushed, it should be empty
    assertEquals("", stdout);
}
```



## Parsing `ProcessBuilder` output

The `Print` helper let's you parse output from terminal commands conveniently. You just can feed its `InputStreams` into `Print` and store the output there. Note that you need to use `Print.useReadableStreams()` beforehand, because otherwise the `InputStreams` will be fed to the default `System.out` & `System.err` and be printed to console.

```java
Print.useReadableStreams();
Print.feedToSystemOut(process.getInputStream());
Print.feedToSystemErr(process.getErrorStream());
```

So you can test like this:

```java
@Test
public void feedProcessBuilderInputStream() throws Exception {
    Print.useReadableStreams();
    final Process process = new ProcessBuilder("cat", "readme.md").start();
    Print.feedToSystemOut(process.getInputStream());
    assertTrue(Print.getStdOut().contains("# PicoTemp"));
}
```



## Testing commands which use `ProcessBuilder`

See "Parsing `ProcessBuilder` output"  for  details.

Let's say you have this command, which uses `ProcessBuilder`.

```java
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
```

In the test you just need to use `Print.useReadableStreams()`, then execute the `CommandLine` instance and then get the output from either `Print.getStdOut()` or `Print.getStdErr()`. E.g.

```java
@Test
void run() {
    Print.useReadableStreams();
    new CommandLine(new Main()).execute("run", "pwd | ls -la");

    String stdout = Print.getStdOut();
    assertTrue(Pattern.compile("-rw-rw-r--.*readme.md").matcher(stdout).find());
}
```





## Turn off ANSI coloring for testing

To circumvent ANSI coloring noise in the output, you can turn it off:

```java
@Test
public void ansiColoring() {
    Print.useReadableStreams();

    Print.enableAnsiColoring();
    Print.success("With ANSI colors");
    assertTrue(Print.getStdOut().contains("\u001B[32mWith ANSI colors\u001B[39m\u001B[0m"));

    Print.disableAnsiColoring();
    Print.success("Without ANSI colors");
    assertTrue(Print.getStdOut().contains("Without ANSI colors\n"));
}
```

