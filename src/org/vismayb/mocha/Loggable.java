package org.vismayb.mocha;

public interface Loggable {
    default void performLogging() {
        if (GlobalConstants.LOGGING_ENABLED) {
            log();
        }
    }

    void log();
}
