package fr.mrwarzo.mailboxes.tools;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigFiller implements CFGFiller {
    @Override
    public void fill(FileConfiguration fileConfiguration) {
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ConfigurationSection cfgSection = fileConfiguration.createSection("configs");

        cfgSection.set("box-rows", 6);
        cfgSection.set("sound-on-click", true);
        cfgSection.set("volume", 25);
        cfgSection.set("free-mails", true);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ConfigurationSection boxesSection = fileConfiguration.createSection("boxes");


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
