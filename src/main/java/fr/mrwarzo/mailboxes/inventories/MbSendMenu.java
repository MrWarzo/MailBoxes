package fr.mrwarzo.mailboxes.inventories;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import fr.mrwarzo.mailboxes.managers.Managers;
import fr.mrwarzo.mailboxes.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.List;

public class MbSendMenu implements InventoryProvider {
    private static float vol;
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mbSendMenu")
            .provider(new MbSendMenu())
            .type(InventoryType.CHEST)
            .title("Choisir un destinataire : ")
            .manager(Managers.getInventoryManager())
            .build();

    private MbSendMenu() {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        if (cfgSection.getBoolean("sound-on-click")) {
            vol = ((float) cfgSection.getDouble("volume"));
        } else {
            MbSendMenu.vol = 0;
        }
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();
        pagination.setItems(generatePlayersHead(player));
        pagination.setItemsPerPage(45);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        contents.set(5, 3, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Page précédente")
                .toItemStack(), e -> changePage(player, contents, 0)));
        contents.set(5, 4, ClickableItem.of(new ItemBuilder(Material.SUNFLOWER)
                        .setName("§6§lRaffraichis la page")
                        .toItemStack()
                , e -> refreshInventory(player)));
        contents.set(5, 5, ClickableItem.of(new ItemBuilder(Material.PAPER)
                .setName("§6Page suivante")
                .toItemStack(), e -> changePage(player, contents, 1)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void changePage(Player player, InventoryContents contents, int direction) {
        final float volume = MbSendMenu.vol;

        if (direction == 0) {
            INVENTORY.open(player, contents.pagination().previous().getPage());
        } else {
            INVENTORY.open(player, contents.pagination().next().getPage());
        }

        player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, volume, 1);
    }

    private void refreshInventory(Player player) {
        final float volume = MbSendMenu.vol;

        player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, volume, 1);
        INVENTORY.close(player);
        MbMenu.INVENTORY.open(player);
    }

    private ClickableItem[] generatePlayersHead(Player player) {
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        ClickableItem[] playerHeads = new ClickableItem[playerList.size()];

        for(int i = 0; i < playerList.size(); i++) {
            int finalI = i;
            playerHeads[i] = ClickableItem.of(new ItemBuilder(Material.PLAYER_HEAD)
                            .setSkullOwner(playerList.get(i).getName())
                            .toItemStack()
                    , e -> openPackageMenu(player, playerList.get(finalI).getUniqueId().toString()));
        }

        return playerHeads;
    }

    private void openPackageMenu(Player sender, String uniqueID) {
        Player reciever = Bukkit.getPlayer(uniqueID);


        MbPackageMenu.INVENTORY.open(sender);
    }
}
