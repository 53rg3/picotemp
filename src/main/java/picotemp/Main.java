/*
 * Copyright 2022 Sergej Schaefer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
