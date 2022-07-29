package me.hybridplague.stafflist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import me.hybridplague.stafflist.inventories.AdminList;
import me.hybridplague.stafflist.inventories.EditUser;
import me.hybridplague.stafflist.inventories.HelperList;
import me.hybridplague.stafflist.inventories.ModList;
import me.hybridplague.stafflist.inventories.OwnerList;
import me.hybridplague.stafflist.inventories.SAList;
import me.hybridplague.stafflist.inventories.TModList;
import net.md_5.bungee.api.ChatColor;

public class StaffList extends JavaPlugin {

	private static StaffList instance;
	public InventoryHandler ih;
	
	public OwnerList ol;
	public SAList sal;
	public AdminList al;
	public ModList ml;
	public TModList tml;
	public HelperList hl;
	
	public EditUser eu;
	
	public List<Inventory> gui = new ArrayList<Inventory>();
	public Map<Player, UUID> editing = new HashMap<Player, UUID>();
	public Map<Player, String> previous = new HashMap<Player, String>();
	
	private final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");
	
	public String colorize(String message) {
		Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', message));
		StringBuffer buffer = new StringBuffer();
		
		while(matcher.find()) {
			matcher.appendReplacement(buffer, ChatColor.of(matcher.group(1)).toString());
		}
		return matcher.appendTail(buffer).toString();
	}
	
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		ih = new InventoryHandler();
		instance = this;
		
		ol = new OwnerList();
		sal = new SAList();
		al = new AdminList();
		ml = new ModList();
		tml = new TModList();
		hl = new HelperList();
		
		eu = new EditUser();
		
		this.getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("stafflist") || label.equalsIgnoreCase("staff")) {
			if (!(sender instanceof Player)) return false;
			
			Player p = (Player) sender;
			
			switch(args.length) {
			case 0:
				ol.menu(p);
				break;
			case 1:
				if (args[0].equalsIgnoreCase("add")) {
					if (p.hasPermission("stafflist.admin")) {
						p.sendMessage(ChatColor.RED + "/staff add <owner | senioradmin | admin | moderator | trialmod | helper> <player> ");
						break;
					} else {
						ol.menu(p);
						break;
					}
				}
				if (args[0].equalsIgnoreCase("remove")) {
					if (p.hasPermission("stafflist.admin")) {
						p.sendMessage(ChatColor.RED + "/staff remove <player>");
						break;
					} else {
						ol.menu(p);
						break;
					}
				}
				ol.menu(p);
				break;
			case 2:
				if (args[0].equalsIgnoreCase("add")) {
					if (p.hasPermission("stafflist.admin")) {
						p.sendMessage(ChatColor.RED + "/staff add <owner | senioradmin | admin | moderator | trialmod | helper> <player> ");
						break;
					} else {
						ol.menu(p);
						break;
					}
				}
				if (args[0].equalsIgnoreCase("remove")) {
					if (p.hasPermission("stafflist.admin")) {
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
						UUID id = op.getUniqueId();
						
						remove(p, id);
						
					} else {
						ol.menu(p);
						break;
					}
				}
				ol.menu(p);
				break;
			case 3:
				if (args[0].equalsIgnoreCase("add")) {
					if (p.hasPermission("stafflist.admin")) {
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[2]);
						UUID id = op.getUniqueId();
						String a = args[1].toLowerCase();
						
						add(p, id, a);
						
						break;
					} else {
						ol.menu(p);
						break;
					}
				}
				if (args[0].equalsIgnoreCase("remove")) {
					if (p.hasPermission("stafflist.admin")) {
						OfflinePlayer op = Bukkit.getOfflinePlayer(args[1]);
						UUID id = op.getUniqueId();
						
						remove(p, id);
					} else {
						ol.menu(p);
						break;
					}
				}
				ol.menu(p);
				break;
			default:
				if (p.hasPermission("stafflist.admin")) {
					p.sendMessage(ChatColor.RED + "/staff add <position> <player>");
					break;
				} else {
					ol.menu(p);
					break;
				}
			}
			saveConfig();
			return true;
		}
		
		return false;
	}
	
	public void add(Player p, UUID id, String arg) {
		
		OfflinePlayer op = Bukkit.getOfflinePlayer(id);
		
		if (!op.hasPlayedBefore() && !op.isOnline()) {
			p.sendMessage(ChatColor.RED + "Player not found.");
			return;
		}
		
		switch(arg) {
		case "owner":
			if (!getConfig().getStringList("Owner").contains(op.toString())) {
				List<String> list = getConfig().getStringList("Owner");
				list.add(id.toString());
				getConfig().set("Owner", list);
				p.sendMessage(colorize("&aSuccessfully added &e" + op.getName() + " &ato the &#a447ffOwner &alist."));
				break;
			} else {
				p.sendMessage(colorize("&e" + op.getName() + " &cis already on the  &clist."));
				break;
			}
		case "senioradmin", "sa":
			if (!getConfig().getStringList("SeniorAdmin").contains(op.toString())) {
				List<String> list = getConfig().getStringList("SeniorAdmin");
				list.add(id.toString());
				getConfig().set("SeniorAdmin", list);
				p.sendMessage(colorize("&aSuccessfully added &e" + op.getName() + " &ato the &#cd0812Senior Administrator &alist."));
				break;
			} else {
				p.sendMessage(colorize("&e" + op.getName() + " &cis already on the &#cd0812Senior Administrator &clist."));
				break;
			}
		case "admin":
			if (!getConfig().getStringList("Admin").contains(op.toString())) {
				List<String> list = getConfig().getStringList("Admin");
				list.add(id.toString());
				getConfig().set("Admin", list);
				p.sendMessage(colorize("&aSuccessfully added &e" + op.getName() + " &ato the &#ff3c01Administrator &alist."));
				break;
			} else {
				p.sendMessage(colorize("&e" + op.getName() + " &cis already on the &#ff3c01Administrator &clist."));
				break;
			}
		case "moderator", "mod":
			if (!getConfig().getStringList("Moderator").contains(op.toString())) {
				List<String> list = getConfig().getStringList("Moderator");
				list.add(id.toString());
				getConfig().set("Moderator", list);
				p.sendMessage(colorize("&aSuccessfully added &e" + op.getName() + " &ato the &#fe7201Moderator &alist."));
				break;
			} else {
				p.sendMessage(colorize("&e" + op.getName() + " &cis already on the &#fe7201Moderator &clist."));
				break;
			}
		case "trialmod", "tmod":
			if (!getConfig().getStringList("TrialMod").contains(op.toString())) {
				List<String> list = getConfig().getStringList("TrialMod");
				list.add(id.toString());
				getConfig().set("TrialMod", list);
				p.sendMessage(colorize("&aSuccessfully added &e" + op.getName() + " &ato the &#fe9a01Trial Moderator &alist."));
				break;
			} else {
				p.sendMessage(colorize("&e" + op.getName() + " &cis already on the &#fe9a01Trial Moderator &clist."));
				break;
			}
		case "helper":
			if (!getConfig().getStringList("Helper").contains(op.toString())) {
				List<String> list = getConfig().getStringList("Helper");
				list.add(id.toString());
				getConfig().set("Helper", list);
				p.sendMessage(colorize("&aSuccessfully added &e" + op.getName() + " &ato the &#ffd000Helper &alist."));
				break;
			} else {
				p.sendMessage(colorize("&e" + op.getName() + " &cis already on the &#ffd000Helper &clist."));
				break;
			}
		default:
			p.sendMessage(ChatColor.RED + "/staff add <owner | senioradmin | admin | moderator | trialmod | helper> <player> ");
			break;
		}
	}
	
	public void remove(Player p, UUID id) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(id);
		if (!op.hasPlayedBefore() && !op.isOnline()) {
			p.sendMessage(ChatColor.RED + "Player not found.");
			return;
		}
		
		if (getConfig().getStringList("Owner").contains(id.toString())) {
			List<String> list = getConfig().getStringList("Owner");
			list.remove(id.toString());
			getConfig().set("Owner", list);
			p.sendMessage(colorize("&cSuccessfully removed &e" + op.getName() + " &cfrom the &#a447ffOwner &clist."));
			
		}
		if (getConfig().getStringList("SeniorAdmin").contains(id.toString())) {
			List<String> list = getConfig().getStringList("SeniorAdmin");
			list.remove(id.toString());
			getConfig().set("SeniorAdmin", list);
			p.sendMessage(colorize("&cSuccessfully removed &e" + op.getName() + " &cfrom the &#cd0812Senior Administrator &clist."));
			
		}
		if (getConfig().getStringList("Admin").contains(id.toString())) {
			List<String> list = getConfig().getStringList("Admin");
			list.remove(id.toString());
			getConfig().set("Admin", list);
			p.sendMessage(colorize("&cSuccessfully removed &e" + op.getName() + " &cfrom the &#ff3c01Administrator &clist."));
			
		}
		if (getConfig().getStringList("Moderator").contains(id.toString())) {
			List<String> list = getConfig().getStringList("Moderator");
			list.remove(id.toString());
			getConfig().set("Moderator", list);
			p.sendMessage(colorize("&cSuccessfully removed &e" + op.getName() + " &cfrom the &#fe7201Moderator &clist."));
			
		}
		if (getConfig().getStringList("TrialMod").contains(id.toString())) {
			List<String> list = getConfig().getStringList("TrialMod");
			list.remove(id.toString());
			getConfig().set("TrialMod", list);
			p.sendMessage(colorize("&cSuccessfully removed &e" + op.getName() + " &cfrom the &#fe9a01Trial Moderator &clist."));
			
		}
		if (getConfig().getStringList("Helper").contains(id.toString())) {
			List<String> list = getConfig().getStringList("Helper");
			list.remove(id.toString());
			getConfig().set("Helper", list);
			p.sendMessage(colorize("&cSuccessfully removed &e" + op.getName() + " &cfrom the &#ffd000Helper &clist."));
		}
		else {
			return;
		}
	}
	
	
	public static StaffList getInstance() {
		return instance;
	}
	
}
