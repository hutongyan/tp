package seedu.address.ui;

import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;

/**
 * A helper class for testing JavaFX classes.
 */
public class JavaFxInitializer {
    private static boolean initialized = false;

    /**
     * Initializes an instance of JavaFX for testing purposes.
     */
    public static void init() {
        if (initialized) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            // JavaFX toolkit initialized
            latch.countDown();
        });

        try {
            latch.await();
            initialized = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
