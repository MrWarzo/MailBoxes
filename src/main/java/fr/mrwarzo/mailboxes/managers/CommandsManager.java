package fr.mrwarzo.mailboxes.managers;

import co.aikar.commands.PaperCommandManager;
import fr.mrwarzo.mailboxes.MailBoxes;
import fr.mrwarzo.mailboxes.commands.MailBoxesCommand;

public class CommandsManager {
    public static void loadCommands(MailBoxes instance) {
        PaperCommandManager cmdManager = new PaperCommandManager(instance);

        cmdManager.registerCommand(new MailBoxesCommand());
    }
}
