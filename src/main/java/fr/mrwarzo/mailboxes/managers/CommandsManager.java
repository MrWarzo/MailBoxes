package fr.mrwarzo.mailboxes.managers;

import co.aikar.commands.PaperCommandManager;
import fr.mrwarzo.mailboxes.MailBoxes;
import fr.mrwarzo.mailboxes.commands.MailBoxesCommand;
import fr.mrwarzo.mailboxes.tools.CommandCompletionUtils;

public class CommandsManager {
    public static void loadCommands(MailBoxes instance) {
        PaperCommandManager cmdManager = new PaperCommandManager(instance);

        cmdManager.registerCommand(new MailBoxesCommand());
        cmdManager.getCommandCompletions().registerAsyncCompletion(
                "sendMail",
                context -> CommandCompletionUtils.playersName()
        );
    }
}
