package uk.minersonline.games.minestom_game_lib.feature;

import java.util.ArrayList;
import java.util.List;

public class FeatureManager {

    private final List<Feature<?>> features = new ArrayList<>();

    /**
     * Register a new feature.
     */
    public void registerFeature(Feature<?> feature) {
        features.add(feature);
    }

    /**
     * Enable all registered features.
     */
    public void enableAll() {
        for (Feature<?> feature : features) {
            feature.enable();
        }
    }

    /**
     * Disable all registered features.
     */
    public void disableAll() {
        for (Feature<?> feature : features) {
            feature.disable();
        }
    }

    /**
     * Retrieve the list of registered features.
     */
    public List<Feature<?>> getFeatures() {
        return features;
    }
}