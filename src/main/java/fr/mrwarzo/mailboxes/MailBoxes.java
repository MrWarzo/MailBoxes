package fr.mrwarzo.mailboxes;

import fr.mrwarzo.mailboxes.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MailBoxes extends JavaPlugin {
    private static Map<Player, List<ItemStack>> boxes;

    Managers managers = new Managers();

    @Override
    public void onEnable() {
        boxes = new HashMap<>();

        managers.load(this);
    }

    @Override
    public void onDisable() {
        managers.unload(this);
    }

    public Map<Player, List<ItemStack>> getBoxes() {
        return boxes;
    }

    public void saveData() throws IOException {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");
        ConfigurationSection bxSection = cfg.getConfigurationSection("player-boxes");

        for (Map.Entry<Player,List<ItemStack>> entry:boxes.entrySet()) {
            ConfigurationSection player = bxSection.createSection(entry.getKey().getUniqueId().toString());
            player.addDefault("box",entry.getValue());
        }
    }

    public void restoreData() {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection bxSection = cfg.getConfigurationSection("player-boxes");

        bxSection.getKeys(false).forEach(key -> {
            List<ItemStack> content = (List<ItemStack>) bxSection.getList("box");
            boxes.put(Bukkit.getPlayer(key), content);
        });
    }
}
