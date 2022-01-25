package newyearblessing.newyearblessing.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class maincommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        Plugin plugin = newyearblessing.newyearblessing.NewyearBlessing.getProvidingPlugin(newyearblessing.newyearblessing.NewyearBlessing.class);
        String messagefront = plugin.getConfig().getString("message-front");
        if(strings.length == 0)
        {
            String messageerror = plugin.getConfig().getString("message-error");
            commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.RED+messageerror);
            return false;
        }
        if(strings.length == 2)
        {
            String Message0 = strings[0];
            if (Message0.equals("add"))
            {
                String month = plugin.getConfig().getString("use-month");
                String nowmonth = "%server_time_M%";
                Player p=(Player) commandSender;
                nowmonth = PlaceholderAPI.setPlaceholders(p,nowmonth);
                if(month.equals(nowmonth))
                {
                }
                else
                {
                    if(month.equals("all")) {
                    }
                    else
                    {
                        String messagetime = plugin.getConfig().getString("message-notime");
                        commandSender.sendMessage(ChatColor.YELLOW + messagefront + ChatColor.RED + messagetime);
                        return false;
                    }
                }
                String Message1 = strings[1];
                String player = commandSender.getName();
                List<String> blessing = plugin.getConfig().getStringList("Blessing");
                for (String command_ : blessing) {
                    if(command_.equals(player)){
                        String messagealready = plugin.getConfig().getString("message-already");
                        commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.RED+messagealready);
                        return false;
                    }
                }
                blessing.add(Message1+" §7——"+player);
                blessing.add(player);
                plugin.getConfig().set("Blessing", blessing);
                plugin.saveConfig();
                String messageup = plugin.getConfig().getString("message-upload");
                commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.GREEN+messageup);

                String reward=plugin.getConfig().getString("reward");
                Player players=(Player) commandSender;
                reward = PlaceholderAPI.setPlaceholders(players,reward);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),reward);
                return false;
            }
            else if(Message0.equals("looknumber"))
            {
                String Message1 = strings[1];
                int i = Integer.parseInt( Message1 );
                String messagedi = plugin.getConfig().getString("message-di");
                String messageis = plugin.getConfig().getString("message-is");
                commandSender.sendMessage(ChatColor.YELLOW + messagefront + ChatColor.GREEN + messagedi+Message1+messageis);
                List<String> blessing = plugin.getConfig().getStringList("Blessing");
                commandSender.sendMessage(blessing.get(i*2-2));
            }
            else
            {
                String messageerror = plugin.getConfig().getString("message-error");
                commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.RED+messageerror);
            }
        }
        else if(strings.length == 1)
        {
            String Message0 = strings[0];
            if (Message0.equals("list"))
            {
                String messagelist = plugin.getConfig().getString("message-list");
                commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.GREEN+messagelist);
                List<String> blessing = plugin.getConfig().getStringList("Blessing");
                int i=0;
                for (String command_ : blessing)
                {

                    i++;
                    if(i==1) commandSender.sendMessage(command_);
                    if(i==2) i=0;
                }
            }
            else if(Message0.equals("reload"))
            {
                if (commandSender.hasPermission("newyearblessing.commands.reload"))
                {
                    plugin.reloadConfig();
                    String messagereload = plugin.getConfig().getString("message-reload");
                    commandSender.sendMessage(ChatColor.YELLOW + messagefront + ChatColor.GREEN + messagereload);
                }
                else {
                    String messageno = plugin.getConfig().getString("message-no");
                    commandSender.sendMessage(ChatColor.YELLOW + messagefront + ChatColor.RED + messageno);
                }
            }
            else
            {
                String messageerror = plugin.getConfig().getString("message-error");
                commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.RED+messageerror);
            }
        }
        else
        {
            String messageerror = plugin.getConfig().getString("message-error");
            commandSender.sendMessage(ChatColor.YELLOW+messagefront+ChatColor.RED+messageerror);
        }
        return false;
    }
}
