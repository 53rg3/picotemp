package picotemp.utils;

import org.junit.jupiter.api.Test;
import picotemp.Config;

import static org.junit.jupiter.api.Assertions.*;

class ConfigLoaderTest {

    @Test
    public void load() {
        Config config = ConfigLoader.load();
        assertEquals("0.1", config.version);
    }

}
