package fr.mrwarzo.mailboxes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.mrwarzo.mailboxes.inventories.MbMenu;
import fr.mrwarzo.mailboxes.managers.CommandsManager;
import fr.mrwarzo.mailboxes.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@CommandAlias("mb|mbox|mailbox")
public class MailBoxesCommand extends BaseCommand {
    @Subcommand("info")
    @Syntax("/mailbox info")
    public static void onInfo(Player player, String[] args) {
        player.sendMessage("§6------------------------");
        player.sendMessage("§6Nom:      MailBox");
        player.sendMessage("§6Auteur:   MrWarzo");
        player.sendMessage("§6Version:  0.0.1");
        player.sendMessage("§6------------------------");
    }

    @Subcommand("reload")
    @Syntax("/mailbox reload")
    public static void onReload(Player player, String[] args) {
        try {
            Managers.getConfigManager().setupFiles();

            player.sendMessage(ChatColor.GREEN + "[MailBoxes] Reload reussi");
        } catch (Exception e) {
            player.sendMessage(ChatColor.DARK_RED + "[MailBoxes] Reload échoué");
        }
    }

    @Default
    @Syntax("/mailbox")
    public static void onMb(Player player, String[] args) {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        MbMenu.INVENTORY.open(player);
    }
}
