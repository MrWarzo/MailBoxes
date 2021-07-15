package fr.mrwarzo.mailboxes.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.mrwarzo.mailboxes.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class MbSendMenu2 implements InventoryProvider {
    private static float vol;
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mbSendMenu2")
            .provider(new MbSendMenu2())
            .type(InventoryType.WORKBENCH)
            .title("Pose le contenu de ton mail : ")
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(0,0, ClickableItem.of(new ItemBuilder(Material.ELYTRA)
                .setName("§6§lEnvoyer le mail")
                .toItemStack()
                , e-> sendMail()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void sendMail() {

    }
}
