package dev.ender.finalmarket.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class AbstractGUI implements Listener {

    public Player player;
    public Inventory gui;
    public String name;
    public Integer size;
    public ItemStack blank;

    public static HashMap<Player, AbstractGUI> GUI = new HashMap<>();

    /**
     * Buttons and their positions.
     */
    private final HashMap<Integer, ItemStack> buttons = new HashMap<>();


    public AbstractGUI(Player player, String name, Integer size) {
        this.player = player;
        this.name = name;
        this.size = size;
    }

    /**
     * Use only for event handler registry
     */
    public AbstractGUI() {

    }

    public void open() {
        this.player.openInventory(this.gui);
    }

    public void close() {
        this.player.closeInventory();
    }

    public void clean() {
        for (int i = 0; i < this.size; i++) {
            this.gui.setItem(i, new ItemStack(Material.AIR));
        }
    }

    public void refresh() {
        this.clean();
        this.create();
    }

    public AbstractGUI setButton(Integer buttonPos, ItemStack button) {
        buttons.put(buttonPos, button);
        return this;
    }

    public AbstractGUI create() {
        this.gui = Bukkit.createInventory(player, size, name);

        for (Integer key : buttons.keySet()) {
            this.gui.setItem(key, buttons.get(key));
        }

        if (blank != null) {
            for (int i = 0; i < this.size; i++) {
                if (this.gui.getItem(i) == null) {
                    this.gui.setItem(i, this.blank);
                }
            }
        }

        GUI.put(this.player, this);
        return this;
    }

    public abstract void leftClick(Integer pos);

    public abstract void rightClick(Integer pos);

    public void setBlank(ItemStack blankItemStack) {
        this.blank = blankItemStack;
    }

    @EventHandler
    public static void onPlayerClickGUI(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();

        AbstractGUI gui = GUI.get(p);
        if (gui == null) return;

        event.setCancelled(true);

        if (event.isLeftClick()) {
            gui.leftClick(event.getSlot());
        }
        if (event.isRightClick()) {
            gui.rightClick(event.getSlot());
        }
    }

    @EventHandler
    public static void onPlayerCloseGUI(InventoryCloseEvent event) {
        Player p = (Player) event.getPlayer();
        if (GUI.get(p) != null) {
            GUI.remove(p);
        }
    }

    @EventHandler
    public static void onPlayerQuitDuringGUI(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (GUI.get(p) != null) {
            GUI.remove(p);
        }
    }

}
