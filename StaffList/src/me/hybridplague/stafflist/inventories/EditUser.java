package me.hybridplague.stafflist.inventories;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.hybridplague.stafflist.StaffList;
import net.md_5.bungee.api.ChatColor;

public class EditUser {

	private StaffList main = StaffList.getInstance();
	
	private Inventory inv;
	
	public void menu(Player p, UUID id) {
		
		OfflinePlayer op = Bukkit.getOfflinePlayer(id);
		String name = op.getName();
		
		inv = Bukkit.createInventory(null, 45, main.colorize("&4Edit " + name));
		main.gui.add(inv);
		main.editing.put(p, id);
		p.openInventory(inv);
		
		for (int i = 0; i < 45; i++) {
			inv.setItem(i, main.ih.createItem(Material.GRAY_STAINED_GLASS_PANE, null, " ", false));
		}
		
		List<String> sa = new ArrayList<String>();
		List<String> a = new ArrayList<String>();
		List<String> m = new ArrayList<String>();
		List<String> tm = new ArrayList<String>();
		List<String> h = new ArrayList<String>();
		
		sa.add(main.colorize("&7&oClick to set " + name + " as a &#cd0812Senior Administrator"));
		a.add(main.colorize("&7&oClick to set " + name + " as an &#ff3c01Administrator"));
		m.add(main.colorize("&7&oClick to set " + name + " as a &#fe7201Moderator"));
		tm.add(main.colorize("&7&oClick to set " + name + " as a &#fe9a01Trial Moderator"));
		h.add(main.colorize("&7&oClick to set " + name + " as a &#ffd000Helper"));
		
		inv.setItem(13, main.ih.createSkull(id, false));
		inv.setItem(29, main.ih.createItem(Material.RED_STAINED_GLASS_PANE, sa, main.colorize("&#cd0812Senior Administrator"), false));
		inv.setItem(30, main.ih.createItem(Material.PINK_STAINED_GLASS_PANE, a, main.colorize("&#ff3c01Administrator"), false));
		inv.setItem(31, main.ih.createItem(Material.ORANGE_STAINED_GLASS_PANE, m, main.colorize("&#fe7201Moderator"), false));
		inv.setItem(32, main.ih.createItem(Material.ORANGE_STAINED_GLASS_PANE, tm, main.colorize("&#fe9a01Trial Moderator"), false));
		inv.setItem(33, main.ih.createItem(Material.YELLOW_STAINED_GLASS_PANE, h, main.colorize("&#ffd000Helper"), false));
		inv.setItem(36, main.ih.createItem(Material.ARROW, null, ChatColor.DARK_RED + "Back", false));
	}
	
	
}
