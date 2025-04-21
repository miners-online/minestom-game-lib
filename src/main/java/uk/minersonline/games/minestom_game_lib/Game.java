package uk.minersonline.games.minestom_game_lib;

import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.MinestomAdventure;
import uk.minersonline.games.minestom_game_lib.config.Config;
import uk.minersonline.games.minestom_game_lib.feature.Feature;
import uk.minersonline.games.minestom_game_lib.feature.FeatureManager;
import uk.minersonline.games.minestom_game_lib.commands.ServerCLI;
import uk.minersonline.games.minestom_game_lib.commands.StopCommand;


import java.util.List;

public abstract class Game {
    private final ServerCLI CLI;
    private final FeatureManager featureManager;
    private final int port;
    private final String address;

    public Game(int port, String address) {
        this.CLI = new ServerCLI();
        this.featureManager = new FeatureManager();
        this.port = port;
        this.address = address;
    }

    public void start() {
        MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION = true;

        // Setup and start the Minestom server
        MinecraftServer minecraftServer = MinecraftServer.init();

        // Start the CLI in a separate thread
        CLI.start();

        // Enable all features
        for (Feature<? extends Config> feature : getFeatures()) {
            featureManager.registerFeature(feature);
        }
        featureManager.enableAll();

        MinecraftServer.getCommandManager().register(new StopCommand(CLI));

        Runtime.getRuntime().addShutdownHook(new Thread(featureManager::disableAll));
        // Start the server on the designated address and port (adjust if necessary)
        minecraftServer.start(address, port);
    }

    protected abstract List<Feature<? extends Config>> getFeatures();
}
