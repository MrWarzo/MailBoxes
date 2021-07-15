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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class MbMenu implements InventoryProvider {
    private static float vol;
    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("mbMenu")
            .provider(new MbMenu())
            .type(InventoryType.HOPPER)
            .title("MailBox : ")
            .build();

    private MbMenu() {
        FileConfiguration cfg = Managers.getConfigManager().getConfigurationFile("mailboxes.yml");
        ConfigurationSection cfgSection = cfg.getConfigurationSection("configs");

        if (cfgSection.getBoolean("sound-on-click")) {
            vol = ((float) cfgSection.getDouble("volume"));
        } else {
            MbMenu.vol = 0;
        }
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        final float volume = MbMenu.vol;

        contents.set(0, 0, ClickableItem.of(new ItemBuilder(Material.CHEST)
                        .setName("§a§lMa boîte mail")
                        .toItemStack()
                , e -> openPersMenu(player)));
        contents.set(0, 1, ClickableItem.of(new ItemBuilder(Material.CHEST_MINECART)
                        .setName("§a§lEnvoyer un mail à quelqu'un")
                        .toItemStack()
                , e -> openSendMenu(player)));
        contents.set(0, 2, ClickableItem.of(new ItemBuilder(Material.SHIELD)
                        .setName("§c§lBloquer un joueur")
                        .toItemStack()
                , e -> openBlockMenu(player)));

        contents.set(0, 3, ClickableItem.of(new ItemStack(Material.BLACK_STAINED_GLASS_PANE), e -> player.playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, volume, 1)));
        contents.set(0, 4, ClickableItem.of(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(player.getName()).toItemStack(), e -> player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH, volume, 1)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }

    private void openPersMenu(Player player) {
        final float volume = MbMenu.vol;

        player.playSound(player.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, volume, 1);
        MbPersonalMenu.INVENTORY.open(player);
    }

    private void openSendMenu(Player player) {
        final float volume = MbMenu.vol;

        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_ELYTRA, volume, 1);
        MbSendMenu.INVENTORY.open(player);
    }

    private void openBlockMenu(Player player) {
        final float volume = MbMenu.vol;

        player.playSound(player.getLocation(), Sound.ENTITY_SKELETON_HORSE_AMBIENT, volume, 1);
        MbSendMenu.INVENTORY.open(player);
    }
}
