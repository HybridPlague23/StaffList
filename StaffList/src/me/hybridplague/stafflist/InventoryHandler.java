package me.hybridplague.stafflist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryHandler implements Listener {

	private StaffList main = StaffList.getInstance();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (main.gui.contains(e.getInventory())) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			if (e.getCurrentItem() == null) return;
			if (!e.getCurrentItem().hasItemMeta()) return;
			if (!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
			
			String name = e.getCurrentItem().getItemMeta().getDisplayName();
			
			if (e.getCurrentItem().getType().equals(Material.ARROW) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Back")) {
				switch(main.previous.get(p)) {
				case "sa":
					main.sal.menu(p);
					return;
				case "a":
					main.al.menu(p);
					return;
				case "m":
					main.ml.menu(p);
					return;
				case "tm":
					main.tml.menu(p);
					return;
				case "h":
					main.hl.menu(p);
					return;
				}
			}
			
			if (e.getView().getTitle().contains("Edit ")) {
				if (ChatColor.stripColor(name).equals("Senior Administrator")) {
					main.remove(p, main.editing.get(p));
					main.add(p, main.editing.get(p), "sa");
				}
				if (ChatColor.stripColor(name).equals("Administrator")) {
					main.remove(p, main.editing.get(p));
					main.add(p, main.editing.get(p), "admin");
				}
				if (ChatColor.stripColor(name).equals("Trial Moderator")) {
					main.remove(p, main.editing.get(p));
					main.add(p, main.editing.get(p), "tmod");
				}
				if (ChatColor.stripColor(name).equals("Moderator")) {
					main.remove(p, main.editing.get(p));
					main.add(p, main.editing.get(p), "mod");
				}
				if (ChatColor.stripColor(name).equals("Helper")) {
					main.remove(p, main.editing.get(p));
					main.add(p, main.editing.get(p), "helper");
				}
				switch(main.previous.get(p)) {
				case "sa":
					main.sal.menu(p);
					return;
				case "a":
					main.al.menu(p);
					return;
				case "m":
					main.ml.menu(p);
					return;
				case "tm":
					main.tml.menu(p);
					return;
				case "h":
					main.hl.menu(p);
					return;
				}
				return;
			}
			
			ClickType click = e.getClick();
			
			if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD) &&
					e.getCurrentItem().getItemMeta().getLore().get(0).equals(ChatColor.translateAlternateColorCodes('&', "&eLeft-click &7to edit user"))) {
				if (!hasPerm(p)) {
					return;
				}
				if (click.equals(ClickType.LEFT)) {
					
					switch(ChatColor.stripColor(e.getView().getTitle())) {
					case "Senior Administrator":
						main.previous.put(p, "sa");
						break;
					case "Administrator":
						main.previous.put(p, "a");
						break;
					case "Moderator":
						main.previous.put(p, "m");
						break;
					case "Trial Moderator":
						main.previous.put(p, "tm");
						break;
					case "Helper":
						main.previous.put(p, "h");
						break;
					}
					
					String user = ChatColor.stripColor(name);
					main.eu.menu(p, Bukkit.getOfflinePlayer(user).getUniqueId());
					return;
				}
				if (click.equals(ClickType.RIGHT)) {
					String user = ChatColor.stripColor(name);
					main.remove(p, Bukkit.getOfflinePlayer(user).getUniqueId());
					
					switch(ChatColor.stripColor(e.getView().getTitle())) {
					case "Senior Administrator":
						main.sal.menu(p);
						break;
					case "Administrator":
						main.al.menu(p);
						break;
					case "Moderator":
						main.ml.menu(p);
						break;
					case "Trial Moderator":
						main.tml.menu(p);
						break;
					case "Helper":
						main.hl.menu(p);
						break;
					}
					return;
				}
				
			}
			
			if (name.contains("Owner")) {
				main.ol.menu(p);
				return;
			}
			if (name.contains("Senior Administrator")) {
				main.sal.menu(p);
				return;
			}
			if (name.contains("Administrator")) {
				main.al.menu(p);
				return;
			}
			if (name.contains("Trial Moderator")) {
				main.tml.menu(p);
				return;
			}
			if (name.contains("Moderator")) {
				main.ml.menu(p);
				return;
			}
			if (name.contains("Helper")) {
				main.hl.menu(p);
				return;
			}
			
			return;
		}
	}
	
	public boolean hasPerm(Player p) {
		if (p.hasPermission("businesscraft.admin")) return true;
		return false;
	}
	
	public ItemStack createSkull(UUID uuid, @Nullable boolean isAdmin) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		List<String> itemLore = new ArrayList<String>();
		
		if (isAdmin) {
			
			itemLore.add(ChatColor.translateAlternateColorCodes('&', "&eLeft-click &7to edit user"));
			itemLore.add(ChatColor.translateAlternateColorCodes('&', "&eRight-click &7to remove user"));
			
			meta.setLore(itemLore);
		}
		
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
		meta.setDisplayName(ChatColor.YELLOW + "" + Bukkit.getOfflinePlayer(uuid).getName());
		item.setItemMeta(meta);
		return item;
	}
	
	public static String format(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	
	public ItemStack createItem(Material material, @Nullable List<String> lore, String displayName, boolean shiny) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		if (lore != null) {
			List<String> l = lore;
			meta.setLore(l);
		}
		if (shiny == true) {
			meta.addEnchant(Enchantment.DURABILITY, 1, shiny);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		
		return item;
	}
	
}
