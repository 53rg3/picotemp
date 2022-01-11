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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YAML {
    private YAML() {
    }

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
    }

    public static <T> T fromYaml(final String yaml, final Class<T> clazz) {
        try {
            return mapper.readValue(yaml, clazz);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to parse provided YAML", e);
        }
    }

    public static String toYaml(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new IllegalArgumentException("Failed to convert provided object to YAML", e);
        }
    }
}
