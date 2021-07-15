package fr.mrwarzo.mailboxes.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.mrwarzo.mailboxes.managers.Managers;
import fr.mrwarzo.mailboxes.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MbPersonalMenu implements InventoryProvider {
    private static int rows;
    private static float vol;
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mbPersoMenu")
            .provider(new MbPersonalMenu())
            .size(MbPersonalMenu.rows, 9)
            .title("Ma mailbox : ")
            .build();

    private MbPersonalMenu() {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");
        MbPersonalMenu.rows = cfgSection.getInt("box-rows");

        if (MbPersonalMenu.rows > 6) {
            MbPersonalMenu.rows = 6;
        } else if (MbPersonalMenu.rows < 2) {
            MbPersonalMenu.rows = 2;
        }

        if (cfgSection.getBoolean("sound-on-click")) {
            vol = ((float) cfgSection.getDouble("volume"));
        } else {
            MbPersonalMenu.vol = 0;
        }
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(MbPersonalMenu.rows - 1, 3, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Page précédente")
                .toItemStack(), e -> changePage(player, 0)));
        contents.set(MbPersonalMenu.rows - 1, 4, ClickableItem.of(new ItemBuilder(Material.BARRIER)
                        .setName("§c§lFermer")
                        .toItemStack()
                , e -> closeInventory(player)));
        contents.set(MbPersonalMenu.rows - 1, 5, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Page suivante")
                .toItemStack(), e -> changePage(player, 1)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void changePage(Player player, int direction) {
        final float volume = MbPersonalMenu.vol;

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, volume, 1);
    }

    private void closeInventory(Player player) {
        final float volume = MbPersonalMenu.vol;

        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, volume, 1);
        INVENTORY.close(player);
        MbMenu.INVENTORY.open(player);
    }
}
