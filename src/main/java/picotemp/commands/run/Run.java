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
