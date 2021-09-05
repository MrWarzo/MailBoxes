package fr.mrwarzo.mailboxes.tools;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MailBoxesFiller implements CFGFiller{
    @Override
    public void fill(FileConfiguration fileConfiguration) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ConfigurationSection boxesSection = fileConfiguration.createSection("player-boxes");

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    public static void fillWithMap(Map<Player, List<ItemStack>> boxes, FileConfiguration fileConfiguration) {
        ConfigurationSection mbSection = fileConfiguration.getConfigurationSection("player-boxes");

        for (var entry: boxes.entrySet()) {
            Player p = entry.getKey();
            String UUID = p.getUniqueId().toString();
            mbSection.createSection(UUID);

            ConfigurationSection pSection = fileConfiguration.getConfigurationSection("player-boxes.UUID");
            int k = 0;
            for (ItemStack i:entry.getValue()) {
                pSection.set(Integer.toString(k), i.toString());
            }
        }
    }
}
