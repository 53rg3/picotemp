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

package picotemp.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintTest {

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

    @Test
    public void feedProcessBuilderInputStream() throws Exception {
        Print.useReadableStreams();
        final Process process = new ProcessBuilder("cat", "readme.md").start();
        Print.feedToSystemOut(process.getInputStream());
        Print.feedToSystemErr(process.getErrorStream());
        assertTrue(Print.getStdOut().contains("# PicoTemp"));
        Print.useSystemOut();
    }

    @Test
    public void ansiColoring() {
        Print.useReadableStreams();

        Print.enableAnsiColoring();
        Print.success("With ANSI colors");
        assertTrue(Print.getStdOut().contains("\u001B[32mWith ANSI colors\u001B[39m\u001B[0m"));

        Print.disableAnsiColoring();
        Print.success("Without ANSI colors");
        assertTrue(Print.getStdOut().contains("Without ANSI colors\n"));
        Print.useSystemOut();
    }

}
