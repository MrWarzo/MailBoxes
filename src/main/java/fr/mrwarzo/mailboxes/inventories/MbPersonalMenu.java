package fr.mrwarzo.mailboxes.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import fr.mrwarzo.mailboxes.MailBoxes;
import fr.mrwarzo.mailboxes.managers.Managers;
import fr.mrwarzo.mailboxes.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Pagination pagination = contents.pagination();
        pagination.setItems(getPlayerBox(player));
        pagination.setItemsPerPage(27);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        contents.set(MbPersonalMenu.rows - 1, 3, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Page précédente")
                .toItemStack(), e -> changePage(player, contents, 0)));
        contents.set(MbPersonalMenu.rows - 1, 4, ClickableItem.of(new ItemBuilder(Material.BARRIER)
                        .setName("§c§lFermer")
                        .toItemStack()
                , e -> closeInventory(player)));
        contents.set(MbPersonalMenu.rows - 1, 5, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Page suivante")
                .toItemStack(), e -> changePage(player, contents, 1)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void changePage(Player player, InventoryContents contents, int direction) {
        final float volume = MbPersonalMenu.vol;

        if (direction == 0) {
            INVENTORY.open(player, contents.pagination().previous().getPage());
        } else {
            INVENTORY.open(player, contents.pagination().next().getPage());
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, volume, 1);
    }

    private void closeInventory(Player player) {
        final float volume = MbPersonalMenu.vol;

        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_CLOSE, volume, 1);
        INVENTORY.close(player);
        MbMenu.INVENTORY.open(player);
    }

    private ClickableItem[] getPlayerBox(Player player) {
        /*FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection bxSection = cfg.getConfigurationSection("player-boxes");
        ConfigurationSection bxPlayer;*/
        Map<Player, List<ItemStack>> boxes = Managers.getInstance().getBoxes();
        ClickableItem[] clickableItemList;

        if (boxes.isEmpty()) {
            clickableItemList = new ClickableItem[1];
            clickableItemList[0] = ClickableItem.empty(new ItemStack(Material.AIR));
            return clickableItemList;
        }

        List<ItemStack> itemStackList = new ArrayList<>(boxes.get(player));

        /*
        if(bxSection.contains(player.getUniqueId().toString())) {
            bxPlayer = bxSection.getConfigurationSection(player.getUniqueId().toString());
        }
        else {
            bxSection.createSection(player.getUniqueId().toString());
            bxPlayer = bxSection.getConfigurationSection(player.getUniqueId().toString());
            bxPlayer.createSection("box");
        }
        */

        clickableItemList = new ClickableItem[itemStackList.size()];

        int i = 0;
        for (
                ItemStack is : itemStackList) {
            ClickableItem ci = ClickableItem.of(is, e -> getItemInInv(player));
            clickableItemList[i] = ci;
            i++;
        }

        return clickableItemList.clone();
    }

    private void getItemInInv(Player player) {
        player.sendMessage("c good");
    }
}
