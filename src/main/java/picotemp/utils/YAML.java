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
