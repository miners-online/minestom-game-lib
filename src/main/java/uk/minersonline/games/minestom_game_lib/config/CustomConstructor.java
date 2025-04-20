package uk.minersonline.games.minestom_game_lib.config;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.LoaderOptions;

import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomConstructor extends Constructor {
    public CustomConstructor(Class<?> theRoot, LoaderOptions options) {
        super(theRoot, options);
    }

    @Override
    protected Object constructObject(Node node) {
        Class<?> type = node.getType();
        if (type.isRecord()) {
            try {
                // Get the canonical constructor of the record
                java.lang.reflect.Constructor<?> canonicalConstructor = type.getDeclaredConstructor(
                        Arrays.stream(type.getRecordComponents())
                                .map(RecordComponent::getType)
                                .toArray(Class<?>[]::new)
                );

                // Construct a map of field names to values
                Map<Object, Object> rawValues = constructMapping((MappingNode) node);
                Map<String, Object> values = rawValues.entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey().toString(), // Convert keys to String
                                Map.Entry::getValue
                        ));

                // Map the record components to their corresponding values
                Object[] args = Arrays.stream(type.getRecordComponents())
                        .map(rc -> values.get(rc.getName()))
                        .toArray();

                // Instantiate the record using its canonical constructor
                return canonicalConstructor.newInstance(args);
            } catch (Exception e) {
                throw new RuntimeException("Failed to construct record: " + type, e);
            }
        }
        return super.constructObject(node);
    }
}