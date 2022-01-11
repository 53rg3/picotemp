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

import picotemp.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConfigLoader {

    private static final String CONFIG_PATH = "./bin/config.yml";

    public static Config load() {
        try {
            return YAML.fromYaml(Files.readString(Paths.get(CONFIG_PATH)), Config.class);
        } catch (final IOException e) {
            throw new IllegalStateException("Failed to load file: " + CONFIG_PATH);
        }
    }

}
