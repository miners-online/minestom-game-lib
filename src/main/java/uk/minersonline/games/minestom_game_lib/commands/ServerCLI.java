package uk.minersonline.games.minestom_game_lib.commands;

import net.minestom.server.MinecraftServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerCLI {
    private Thread CLI;
    private volatile boolean running = false;

    public ServerCLI() {
        CLI = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String prompt = "minestom> ";
                System.out.print(prompt);

                while (running) {
                    String line = reader.readLine();
                    if (line == null) {
                        break; // End of input
                    }
                    line = line.trim();
                    MinecraftServer.getCommandManager().executeServerCommand(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void start() {
        running = true;
        CLI.start();
    }

    public void stop() {
        running = false;
        if (CLI != null && CLI.isAlive()) {
            try {
                CLI.interrupt();
                CLI.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}