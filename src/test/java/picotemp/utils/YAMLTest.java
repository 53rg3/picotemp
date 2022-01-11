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
import picotemp.testhelpers.TestUtils;
import picotemp.utils.assets.PersonPojo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class YAMLTest {

    private static final String personYaml = TestUtils.loadResourceFileAsString("person.yaml");

    @Test
    void fromAndToYaml() {

        // FROM YAML
        final PersonPojo person = YAML.fromYaml(personYaml, PersonPojo.class);

        assertEquals(person.name, "Klaus");
        assertEquals(person.age, 33);
        assertEquals(person.biography, "Klaus is a dude and does things.\n" +
                "Sometimes he does this and sometimes something else.\n");
        assertTrue(person.hobbies.containsAll(List.of(
                "work in the morning",
                "work in the afternoon",
                "work in the evening")));
        assertTrue(person.isAlive);


        // TO YAML
        final String yaml = YAML.toYaml(person);

        // todo can we get that format with `|`? See person.yaml
        assertEquals(yaml, "" +
                "---\n" +
                "name: \"Klaus\"\n" +
                "age: 33\n" +
                "biography: \"Klaus is a dude and does things.\\nSometimes he does this and sometimes\\\n" +
                "  \\ something else.\\n\"\n" +
                "hobbies:\n" +
                "- \"work in the morning\"\n" +
                "- \"work in the afternoon\"\n" +
                "- \"work in the evening\"\n" +
                "isAlive: true\n");

    }
}
