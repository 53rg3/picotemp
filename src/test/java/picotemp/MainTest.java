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
