package org.vismayb.mocha.logging;

import org.vismayb.mocha.GlobalConstants;

/**
 * To be implemented by classes that need information logged at runtime, but do not require a string representation
 * @implNote This has been created in Java in order to make use of the {@code default} keyword until GUI code gets
 * converted to Kotlin as well.
 */
public interface Loggable {
    /**
     * Calls {@link Loggable#logImpl()} only if dev logging is enabled by {@link GlobalConstants#isLoggingEnabled}
     */
    default void log() {
        if (GlobalConstants.Companion.isLoggingEnabled()) {
            logImpl();
        }
    }

    /**
     * Implementation of the logging. Should use print statements to print out necessary details in a readable manner.
     */
    void logImpl();
}
