package de.othr.vs.quizarena.net;

import java.util.Locale;

public final class ProtocolParser {
    private ProtocolParser() {
    }

    public static class Command {
        private final String name;
        private final String[] args;

        public Command(String name, String[] args) {
            this.name = name;
            this.args = args;
        }

        public String getName() {
            return name;
        }

        public String[] getArgs() {
            return args;
        }
    }

    public static Command parse(String line) {
        String[] parts = line.split(" ", -1);
        String name = parts[0].trim().toUpperCase(Locale.ROOT);
        String[] args = new String[Math.max(0, parts.length - 1)];

        if (parts.length > 1) {
            System.arraycopy(parts, 1, args, 0, parts.length - 1);
            for (int i = 0; i < args.length; i++) {
                args[i] = args[i].trim();
            }
        }
        return new Command(name, args);
    }

    public static String safeField(String value) {
        return value == null ? "" : value.replace("|", "/");
    }
}
