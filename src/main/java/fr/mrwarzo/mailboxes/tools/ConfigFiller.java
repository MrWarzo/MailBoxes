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
        cfgSection.set("reload-success", "&l&a[MailBoxes] &r&aReload reussi");
        cfgSection.set("reload-failed", "&l&a[MailBoxes] &r&aReload échoué");
        cfgSection.set("no-player-arg", "§cVous devez choisir un joueur valide !");
        cfgSection.set("no-item-arg", "§cVous devez avoir un item en main !");
        cfgSection.set("unknown-error", "§4Erreur inconnue, veuillez contacter un administrateur.");
        // 1 -> Seulement la consultation de la mailbox
        // 2 -> Consultation + Menu d'envoi
        // 3 -> Consultation + Menu de blocage joueur
        // 4 -> Tout les menus
        cfgSection.set("menu-mode", 1);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
}
