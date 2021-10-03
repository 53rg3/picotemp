package picotemp.utils;

import picocli.CommandLine.Model.CommandSpec;

/**
 * Helper for checking @Spec related stuff, e.g. originally provided args before parsing
 */
public class SpecUtil {
    private SpecUtil() {
    }

    public static boolean noArgsWereProvided(final CommandSpec spec) {
        return spec.commandLine().getParseResult().originalArgs().isEmpty();
    }
}
