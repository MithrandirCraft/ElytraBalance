package io.nyaruko.elytrabalance;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.nyaruko.elytrabalance.listeners.BoostListener;
import io.nyaruko.elytrabalance.listeners.BreakListener;
import io.nyaruko.elytrabalance.listeners.EatListener;
import io.nyaruko.elytrabalance.listeners.FixListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ElytraBalance extends JavaPlugin {
    private static final int CONFIG_VERSION = 4;

    private static final String VERSION = "1.4.0";

    private static Config config;

    public void onEnable() {
        initConfig();
        registerEvents();
        if (isEnabled()) {
            getLogger().log(Level.INFO, "ElytraBalance v{0} successfully loaded.", "1.4.0");
        } else {
            getLogger().log(Level.SEVERE, "ElytraBalance v{0} failed to load.", "1.4.0");
        }
    }

    private void initConfig() {
        if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
            getLogger().log(Level.SEVERE, "Failed to create data folder.");
            setEnabled(false);
        }
        File configFile = new File(getDataFolder() + "/config.json");
        if (configFile.exists()) {
            try {
                Reader reader = new FileReader(configFile);
                try {
                    config = (Config)(new Gson()).fromJson(reader, Config.class);
                    reader.close();
                } catch (Throwable throwable) {
                    try {
                        reader.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                    throw throwable;
                }
            } catch (IOException e) {
                getLogger().log(Level.SEVERE, e.getMessage());
                setEnabled(false);
                return;
            }
            if (config.configVersion < 4) {
                config.configVersion = 4;
                saveConfig(configFile);
            }
            return;
        }
        try {
            if (configFile.createNewFile()) {
                config = new Config(4);
                saveConfig(configFile);
            } else {
                getLogger().log(Level.SEVERE, "Failed to create plugin config.");
                setEnabled(false);
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, e.getMessage());
            setEnabled(false);
        }
    }

    private void saveConfig(File configFile) {
        try {
            Writer writer = Files.newBufferedWriter(configFile.toPath(), new java.nio.file.OpenOption[0]);
            try {
                Gson file = (new GsonBuilder()).setPrettyPrinting().create();
                file.toJson(config, writer);
                if (writer != null)
                    writer.close();
            } catch (Throwable throwable) {
                if (writer != null)
                    try {
                        writer.close();
                    } catch (Throwable throwable1) {
                        throwable.addSuppressed(throwable1);
                    }
                throw throwable;
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save plugin config: {0}", e.getMessage());
            setEnabled(false);
        }
    }

    private void registerEvents() {
        if (!config.canConsumeFoodInFlight)
            getServer().getPluginManager().registerEvents((Listener)new EatListener(), (Plugin)this);
        if (config.removeElytraOnBreak)
            getServer().getPluginManager().registerEvents((Listener)new BreakListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new BoostListener(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new FixListener(), (Plugin)this);
    }

    public static void sendConfigMessage(Player player, String configMessage) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', configMessage));
    }

    public static Config getConfigModel() {
        return config;
    }
}
