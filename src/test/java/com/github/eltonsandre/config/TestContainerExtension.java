package com.github.eltonsandre.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * @author elton.andre
 */
public class TestContainerExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!started) {
            started = true;
            // Your "before all tests" startup logic goes here
            // The following line registers a callback hook when the root test context is shut down
            context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put("any unique name", this);
        }
    }

    @Override
    public void close() {
        // Your "after all tests" logic goes here
    }
}
