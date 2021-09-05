package fr.mrwarzo.mailboxes.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import fr.mrwarzo.mailboxes.inventories.MbMenu;
import fr.mrwarzo.mailboxes.managers.Managers;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CommandAlias("mb|mbox|mailbox")
public class MailBoxesCommand extends BaseCommand {
    @Subcommand("info")
    @CommandPermission("mailbox.info")
    public static void onInfo(Player player, String[] args) {
        player.sendMessage("§6------------------------");
        player.sendMessage("§6Nom:      MailBox");
        player.sendMessage("§6Auteur:   MrWarzo");
        player.sendMessage("§6Version:  1.0.0");
        player.sendMessage("§6------------------------");
    }

    @Subcommand("reload")
    @CommandPermission("mailbox.reload")
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
    @CommandCompletion("@sendMail")
    @Syntax("<playerName> <lore>")
    @CommandPermission("mailbox.send")
    public static void onSend(Player player, String[] args) {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("config.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        try {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            List<String> lores = new ArrayList<>();

            if (meta.hasLore()) {
                StringBuilder s = new StringBuilder();
                for (Component c : meta.lore()) {
                    s.append(c);
                }
                lores.add(s.toString());
            }
            lores.add(ChatColor.GOLD + "De la part de " + ChatColor.DARK_PURPLE + player.getName());

            if (item.getType().equals(Material.AIR)) {
                throw new Exception("EmptyHand");
            }

            Player reciever;

            if (args.length == 0 || args[0].isEmpty()) {
                throw new Exception("EmptyPlayer");
            } else {
                reciever = Bukkit.getPlayer(args[0]);
                if (reciever == null) {
                    throw new Exception("EmptyPlayer");
                }
            }

            if (args.length >= 2) {
                StringBuilder lore = new StringBuilder();

                for (int i = 1; i < args.length; i++) {
                    lore.append(ChatColor.BLUE + args[i] + " ");
                }

                lores.add(lore.toString());
            }

            meta.setLore(lores);
            item.setItemMeta(meta);

            Map<UUID, List<ItemStack>> boxes = Managers.getInstance().getBoxes();

            if (boxes.containsKey(reciever.getUniqueId())) {
                boxes.get(reciever.getUniqueId()).add(item);
            } else {
                List<ItemStack> items = new ArrayList<>();
                items.add(item);
                boxes.put(reciever.getUniqueId(), items);
            }

            player.getInventory().setItemInMainHand(null);
            player.sendMessage(ChatColor.GREEN + "Vous avez envoyé " + ChatColor.GOLD + item.getAmount() + " * "
                    + item.getI18NDisplayName() + ChatColor.GREEN + " à " + ChatColor.GOLD + reciever.getName());

        } catch (Exception e) {
            if (e.getMessage().equals("EmptyHand")) {
                player.sendMessage(cfgSection.getString("no-item-arg"));
            } else if (e.getMessage().equals("EmptyPlayer")) {
                player.sendMessage(cfgSection.getString("no-player-arg"));
            } else {
                player.sendMessage(cfgSection.getString("unknown-error"));
            }
        }
    }

    @Subcommand("see")
    @CommandCompletion("@sendMail")
    @Syntax("<playerName>")
    @CommandPermission("mailbox.see")
    public static void onSee(Player player, String[] args) {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("config.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        try {
            Player p = Bukkit.getPlayer(args[0]);
            //MbPersonalMenu.INVENTORY.open(p);
            player.sendMessage(Managers.getInstance().getBoxes().get(p).toString());
        } catch (Exception e) {
            player.sendMessage(cfgSection.getString("unknown-error"));
        }
    }

    @Subcommand("map")
    @CommandPermission("mailbox.map")
    public static void onMap(Player player, String[] args) {
        for (Map.Entry<UUID, List<ItemStack>> entry : Managers.getInstance().getBoxes().entrySet()) {
            player.sendMessage(Managers.getInstance().getBoxes().size() + " ->\n " + Managers.getInstance().getBoxes());
        }
    }

    @Default
    @CommandPermission("mailbox.open")
    public static void onMb(Player player, String[] args) {
        MbMenu.INVENTORY.open(player);
    }
}
