package fr.mrwarzo.mailboxes.managers;

import fr.mrwarzo.mailboxes.MailBoxes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Managers {
    private static MailBoxes instance;
    private static Managers managers;
    private static ConfigManager cfgManager;

    public void load(MailBoxes mailBoxes) {
        Managers.instance = mailBoxes;
        Managers.managers = this;
        Managers.cfgManager = new ConfigManager(instance);

        try {
            CommandsManager.loadCommands(instance);
            cfgManager.setupFiles();

            // Envoie d'un message de validation à la console au démarrage
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[MailBoxes] Activation reussie");
        } catch (Exception e) {

            // Envoie d'un message d'erreur à la console
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[MailBoxes] Activation interrompue");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + e.toString());
        }
    }

    public void unload(MailBoxes mailBoxes) {
        try {
            cfgManager.saveFiles();
            // Envoie d'un message de validation à la console à la fermeture
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[MailBoxes] Desactivation reussie");
        } catch (Exception e) {
            // Envoie d'un message d'erreur à la console
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "[MailBoxes] Desactivation interrompue");
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + e.toString());
        }
    }

    public static MailBoxes getInstance() {
        return instance;
    }

    public static Managers getManagers() {
        return managers;
    }

    public static ConfigManager getConfigManager() {
        return cfgManager;
    }
}
