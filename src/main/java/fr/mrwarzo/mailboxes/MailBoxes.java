package fr.mrwarzo.mailboxes;

import fr.mrwarzo.mailboxes.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.*;

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
        FileConfiguration mb = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection mbSection = mb.getConfigurationSection("player-boxes");

        for (var entry : boxes.entrySet()) {
            Player p = entry.getKey();
            String UUID = p.getUniqueId().toString();
            mbSection.createSection(UUID);

            ConfigurationSection pSection = mb.getConfigurationSection("player-boxes." + UUID);
            int k = 0;
            for (ItemStack i : entry.getValue()) {
                pSection.set(Integer.toString(k), i);
                k++;
            }
        }
    }

    public void restoreData() {
        FileConfiguration mb = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection mbSection = mb.getConfigurationSection("player-boxes");

        mbSection.getKeys(false).forEach(key -> {
            ConfigurationSection pSection = mbSection.getConfigurationSection(key);

            List<ItemStack> content = new ArrayList<>();
            pSection.getKeys(false).forEach(i -> {
                content.add(pSection.getItemStack(i));
            });

            boxes.put((Player)Bukkit.getOfflinePlayer(UUID.fromString(key)), content);
        });
    }
}
