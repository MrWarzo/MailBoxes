package fr.mrwarzo.mailboxes;

import fr.mrwarzo.mailboxes.managers.Managers;
import org.bukkit.plugin.java.JavaPlugin;

public final class MailBoxes extends JavaPlugin {
    Managers managers = new Managers();

    @Override
    public void onEnable() {
        managers.load(this);

    }

    @Override
    public void onDisable() {
        managers.unload(this);
    }
}
