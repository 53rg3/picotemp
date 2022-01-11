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
