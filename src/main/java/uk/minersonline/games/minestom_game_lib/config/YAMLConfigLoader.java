package uk.minersonline.games.minestom_game_lib.config;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.InputStream;

public class YAMLConfigLoader<T extends Config> {
    private final Class<T> configClass;

    public YAMLConfigLoader(Class<T> configClass) {
        this.configClass = configClass;
    }

    public T loadConfig(InputStream inputStream) {
        LoaderOptions options = new LoaderOptions();
        Constructor constructor = new CustomConstructor(configClass, options);

        Yaml yaml = new Yaml(constructor);
        return yaml.load(inputStream);
    }

    public static <T extends Config> T load(String path, Class<T> configClass) {
        try (InputStream inputStream = new FileInputStream(path)) {
            YAMLConfigLoader<T> loader = new YAMLConfigLoader<>(configClass);
            return loader.loadConfig(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load configuration from path: " + path, e);
        }
    }
}