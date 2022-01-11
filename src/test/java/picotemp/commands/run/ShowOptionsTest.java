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
