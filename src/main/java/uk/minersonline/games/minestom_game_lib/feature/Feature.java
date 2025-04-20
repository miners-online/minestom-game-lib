package uk.minersonline.games.minestom_game_lib.feature;

import uk.minersonline.games.minestom_game_lib.config.Config;

public abstract class Feature<T extends Config> {

    protected T config;
    private boolean enabled;

    public Feature(T config) {
        this.config = config;
        this.enabled = false;
    }

    /**
     * When the feature is enabled, execute initialisation logic.
     */
    public abstract void onEnable();

    /**
     * When the feature is disabled, perform cleanup.
     */
    public abstract void onDisable();

    /**
     * Enable the feature. Calls onEnable if not already enabled.
     */
    public void enable() {
        if (!enabled) {
            onEnable();
            enabled = true;
        }
    }

    /**
     * Disable the feature. Calls onDisable if currently enabled.
     */
    public void disable() {
        if (enabled) {
            onDisable();
            enabled = false;
        }
    }

    /**
     * Indicates whether the feature is currently enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Retrieve the feature configuration.
     */
    public T getConfig() {
        return config;
    }
}