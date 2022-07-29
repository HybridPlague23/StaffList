package me.hybridplague.stafflist.inventories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.hybridplague.stafflist.StaffList;
import net.md_5.bungee.api.ChatColor;

public class ModList {
	private StaffList main = StaffList.getInstance();
	
	private Inventory inv;
	
	@SuppressWarnings("deprecation")
	public void menu(Player p) {
		inv = Bukkit.createInventory(null, 36, main.colorize("&#fe7201Moderator"));
		
		p.openInventory(inv);
		main.gui.add(inv);
		
		List<String> v = new ArrayList<String>();
		List<String> o = new ArrayList<String>();
		List<String> sa = new ArrayList<String>();
		List<String> a = new ArrayList<String>();
		List<String> m = new ArrayList<String>();
		List<String> tm = new ArrayList<String>();
		List<String> h = new ArrayList<String>();
		
		v.add(ChatColor.translateAlternateColorCodes('&', "&aYou are currently viewing this tab!"));
		o.add(main.colorize("&7&oClick to view the &#a447ffOwner &7&olist"));
		sa.add(main.colorize("&7&oClick to view the &#cd0812Senior Administrator &7&olist"));
		a.add(main.colorize("&7&oClick to view the &#ff3c01Administrator &7&olist"));
		m.add(main.colorize("&7&oClick to view the &#fe7201Moderator &7&olist"));
		tm.add(main.colorize("&7&oClick to view the &#fe9a01Trial Moderator &7&olist"));
		h.add(main.colorize("&7&oClick to view the &#ffd000Helper &7&olist"));
		
		inv.setItem(0, main.ih.createItem(Material.MAGENTA_STAINED_GLASS_PANE, o, main.colorize("&#a447ffOwner"), false));
		inv.setItem(1, main.ih.createItem(Material.RED_STAINED_GLASS_PANE, sa, main.colorize("&#cd0812Senior Administrator"), false));
		inv.setItem(2, main.ih.createItem(Material.PINK_STAINED_GLASS_PANE, a, main.colorize("&#ff3c01Administrator"), false));
		inv.setItem(3, main.ih.createItem(Material.ORANGE_STAINED_GLASS_PANE, v, main.colorize("&#fe7201Moderator"), true));
		inv.setItem(4, main.ih.createItem(Material.ORANGE_STAINED_GLASS_PANE, tm, main.colorize("&#fe9a01Trial Moderator"), false));
		inv.setItem(5, main.ih.createItem(Material.YELLOW_STAINED_GLASS_PANE, h, main.colorize("&#ffd000Helper"), false));
		
		if (main.getConfig().getStringList("Moderator") != null && main.getConfig().getStringList("Moderator").isEmpty()) return;
		List<String> users = new ArrayList<String>();
		
		for (int i = 0; i < main.getConfig().getStringList("Moderator").size(); i++) {
			UUID id = UUID.fromString(main.getConfig().getStringList("Moderator").toArray()[i].toString());
			OfflinePlayer op = Bukkit.getOfflinePlayer(id);
			users.add(op.getName());
		}
		
		Collections.sort(users);
		
		for (int i = 0; i < users.size(); i++) {
			inv.setItem(i+9, main.ih.createSkull(Bukkit.getOfflinePlayer(users.toArray()[i].toString()).getUniqueId(), main.ih.hasPerm(p)));
		}
		
	}
}
