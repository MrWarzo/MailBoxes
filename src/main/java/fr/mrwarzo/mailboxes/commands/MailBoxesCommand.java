package fr.mrwarzo.mailboxes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.mrwarzo.mailboxes.inventories.MbMenu;
import fr.mrwarzo.mailboxes.managers.Managers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("config.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        try {
            Managers.getConfigManager().setupFiles();

            player.sendMessage(cfgSection.getString("reload-success"));
        } catch (Exception e) {
            player.sendMessage(cfgSection.getString("reload-failed"));
        }
    }

    @Subcommand("send")
    @Syntax("/mailbox send [player]")
    public static void onSend(Player player, String[] args) {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("config.yml");
        FileConfiguration mb = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");
        ConfigurationSection mbSection = cfg.getConfigurationSection("player-boxes");

        try {
            ItemStack item = player.getInventory().getItemInMainHand();

            if (item.getType().equals(Material.AIR)) {
                throw new Exception();
            }

            Player reciever = Bukkit.getPlayer(args[0]);
            Map<Player, List<ItemStack>> boxes = Managers.getInstance().getBoxes();

            if (boxes.containsKey(reciever)) {
                boxes.get(reciever).add(item);
            } else {
                List<ItemStack> items = new ArrayList<>();
                items.add(item);
                boxes.put(reciever, items);
            }

            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.GREEN + "Vous avez envoyé " + ChatColor.GOLD + item.getAmount() + " * "
                    + item.getI18NDisplayName() + ChatColor.GREEN + " à " + ChatColor.GOLD + reciever.getName());

            //ConfigurationSection bxReciever = mbSection.getConfigurationSection(reciever.getUniqueId().toString());
            //bxReciever.createSection("box");

        } catch (Exception e) {
            player.sendMessage(cfgSection.getString("no-player-arg"));
        }
    }

    @Subcommand("map")
    @Syntax("/mailbox map")
    public static void onMap(Player player, String[] args) {
        //FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        //ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        for (Map.Entry<Player, List<ItemStack>> entry : Managers.getInstance().getBoxes().entrySet()) {
            player.sendMessage(Managers.getInstance().getBoxes().size() + " d " + Managers.getInstance().getBoxes().toString());
            player.sendMessage(entry.getKey().toString() + " ----- " + entry.getValue().toString());
        }
    }

    @Default
    @Syntax("/mailbox")
    public static void onMb(Player player, String[] args) {
        MbMenu.INVENTORY.open(player);
    }
}
