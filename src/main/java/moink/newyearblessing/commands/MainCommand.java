package moink.newyearblessing.commands;

import moink.newyearblessing.NewYearBlessing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Plugin plugin = NewYearBlessing.getProvidingPlugin(NewYearBlessing.class);
        Player player = (Player) commandSender;
        String messageFront = plugin.getConfig().getString("message-front");
        if (strings.length == 0) {
            String messageError = plugin.getConfig().getString("message-error");
            commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageError);
            return false;
        }
        if (strings.length == 2) {
            String Message0 = strings[0];
            if (Message0.equals("add")) {
                int startYear = plugin.getConfig().getInt("start-year");
                int startMonth = plugin.getConfig().getInt("start-month");
                int startDay = plugin.getConfig().getInt("start-day");
                int endYear = plugin.getConfig().getInt("end-year");
                int endMonth = plugin.getConfig().getInt("end-month");
                int endDay = plugin.getConfig().getInt("end-day");

                Calendar date = Calendar.getInstance();
                date.setTime(new Date()); //当前时间

                Calendar begin = Calendar.getInstance();
                begin.set(startYear, startMonth, startDay, 0, 0, 0);

                Calendar end = Calendar.getInstance();
                end.set(endYear, endMonth, endDay, 23, 59, 59);

                if (!(date.after(begin) && date.before(end))) {
                    String messageTime = plugin.getConfig().getString("message-notime");
                    commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageTime);
                    return false;
                }
                String Message1 = strings[1];
                List<String> blessing = plugin.getConfig().getStringList("Blessing");
                for (String command_ : blessing) {
                    if (command_.equals(player.getName())) {
                        String messageAlready = plugin.getConfig().getString("message-already");
                        commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageAlready);
                        return false;
                    }
                }
                blessing.add(Message1 + " §7——" + player);
                blessing.add(player.getName());
                plugin.getConfig().set("Blessing", blessing);
                plugin.saveConfig();
                String messageUp = plugin.getConfig().getString("message-upload");
                commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.GREEN + messageUp);

                String reward = plugin.getConfig().getString("reward");
                reward.replace("%player_name%", player.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward);
                return false;
            } else {
                String messageError = plugin.getConfig().getString("message-error");
                commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageError);
            }
        } else if (strings.length == 1) {
            String Message0 = strings[0];
            if (Message0.equals("list")) {
                String messageList = plugin.getConfig().getString("message-list");
                commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.GREEN + messageList);
                List<String> blessing = plugin.getConfig().getStringList("Blessing");
                int i = 0;
                for (String command_ : blessing) {
                    i++;
                    if (i == 1) commandSender.sendMessage(command_);
                    if (i == 2) i = 0;
                }
            } else if (Message0.equals("reload")) {
                if (commandSender.hasPermission("nyb.commands.reload")) {
                    plugin.reloadConfig();
                    String messageReload = plugin.getConfig().getString("message-reload");
                    commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.GREEN + messageReload);
                } else {
                    String messageNo = plugin.getConfig().getString("message-no");
                    commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageNo);
                }
            } else {
                String messageError = plugin.getConfig().getString("message-error");
                commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageError);
            }
        } else {
            String messageError = plugin.getConfig().getString("message-error");
            commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.RED + messageError);
        }
        return false;
    }
}
