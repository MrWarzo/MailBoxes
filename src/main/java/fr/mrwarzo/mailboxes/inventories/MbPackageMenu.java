package fr.mrwarzo.mailboxes.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.mrwarzo.mailboxes.managers.Managers;
import fr.mrwarzo.mailboxes.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public class MbPackageMenu implements InventoryProvider {
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mbSendMenu2")
            .provider(new MbPackageMenu())
            .type(InventoryType.WORKBENCH)
            .title("Pose le contenu de ton mail : ")
            //.listener(new InventoryClickEvent(InventoryView.OUTSIDE, InventoryType.SlotType.CRAFTING, 1, ClickType.LEFT, InventoryAction.MOVE_TO_OTHER_INVENTORY))
            .build();

    //public InventoryClickEvent(@NotNull InventoryView view, @NotNull SlotType type, int slot, @NotNull ClickType click, @NotNull InventoryAction action) {
    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(0,0, ClickableItem.of(new ItemBuilder(Material.ELYTRA)
                .setName("§6§lEnvoyer le mail")
                .toItemStack()
                , e-> sendMail(player, contents)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void sendMail(Player reciever, InventoryContents contents) {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection bxSection = cfg.getConfigurationSection("player-boxes");
        ConfigurationSection bxReciever;

        if(bxSection.contains(reciever.getUniqueId().toString())) {
             bxReciever = bxSection.getConfigurationSection(reciever.getUniqueId().toString());
        }
        else {
            bxSection.createSection(reciever.getUniqueId().toString());
            bxReciever = bxSection.getConfigurationSection(reciever.getUniqueId().toString());
            bxReciever.createSection("box");
        }

        for(int i = 1; i < 4; i++) {
            for(int j = 1; j < 4; j++) {
                bxReciever.getConfigurationSection("box").addDefault(reciever.getUniqueId().toString(), contents.get(i,j));
            }
        }
    }
}
