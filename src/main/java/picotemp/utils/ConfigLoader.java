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
