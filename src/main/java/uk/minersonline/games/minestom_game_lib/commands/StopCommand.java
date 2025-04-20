package uk.minersonline.games.minestom_game_lib.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.builder.Command;

public class StopCommand extends Command {
    public StopCommand(ServerCLI cli) {
        super("stop");

        // Executed if no other executor can be used
        setDefaultExecutor((sender, context) -> {
            if (sender instanceof ServerSender) {
                MinecraftServer.stopCleanly();
                cli.stop();
            } else {
                sender.sendMessage("You are not allowed to use this command.");
            }
        });
    }
}
