package moink.newyearblessing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class MainCommand implements CommandExecutor {

    public static boolean isWithinTheTime() {
        Plugin plugin = NewYearBlessing.getProvidingPlugin(NewYearBlessing.class);
        FileConfiguration config = plugin.getConfig();
        int startYear = config.getInt("start-year");
        int startMonth = config.getInt("start-month");
        int startDay = config.getInt("start-day");
        int endYear = config.getInt("end-year");
        int endMonth = config.getInt("end-month");
        int endDay = config.getInt("end-day");

        Calendar date = Calendar.getInstance(); //当前时间

        Calendar begin = Calendar.getInstance();
        begin.set(startYear, startMonth - 1, startDay, 0, 0, 0);

        Calendar end = Calendar.getInstance();
        end.set(endYear, endMonth - 1, endDay, 23, 59, 59);

        return (date.after(begin) && date.before(end));
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Plugin plugin = NewYearBlessing.getProvidingPlugin(NewYearBlessing.class);
        FileConfiguration config = plugin.getConfig();
        String messageFront = ChatColor.YELLOW + config.getString("message-front");
        if (strings.length == 0) {
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "——————————————插件用法——————————————");
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "/nyb add <内容> - 祈福并获得奖励");
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "/nyb list - 查看所有人的祝福");
            if (commandSender.hasPermission("nyb.commands.reload"))
                commandSender.sendMessage(messageFront + ChatColor.AQUA + "/nyb reload - 重载插件");
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "—————————————————————————————————————");
        } else if (strings.length == 2) {
            String Message0 = strings[0];
            if (Message0.equals("add")) {
                if (commandSender instanceof ConsoleCommandSender) {
                    String messageConsole = config.getString("message-console");
                    commandSender.sendMessage(messageFront + ChatColor.AQUA + messageConsole);
                    return false;
                }
                Player player = (Player) commandSender;
                if (!isWithinTheTime()) {
                    String messageTime = config.getString("message-notime");
                    player.sendMessage(messageFront + ChatColor.RED + messageTime);
                    return false;
                }
                String Message1 = strings[1];
                List<String> blessings = config.getStringList("Blessings");
                List<String> alreadyPlayers = config.getStringList("AlreadyPlayers");
                for (String command_ : alreadyPlayers) {
                    if (command_.equals(player.getName())) {
                        String messageAlready = config.getString("message-already");
                        player.sendMessage(messageFront + ChatColor.RED + messageAlready);
                        return false;
                    }
                }
                blessings.add("§l" + Message1 + " §r§7——" + player.getDisplayName());
                alreadyPlayers.add(player.getName());
                config.set("Blessings", blessings);
                config.set("AlreadyPlayers", alreadyPlayers);
                plugin.saveConfig();
                String messageSuccess = config.getString("message-success");
                player.sendMessage(messageFront + ChatColor.GREEN + messageSuccess);

                List<String> rewards = config.getStringList("rewards");
                int random = new Random().nextInt(rewards.size()); //随机选择
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards.get(random).replace("%player_name%", player.getName()));
            } else {
                String messageError = config.getString("message-error");
                commandSender.sendMessage(messageFront + ChatColor.RED + messageError);
            }
        } else if (strings.length == 1) {
            String Message0 = strings[0];
            if (Message0.equals("list")) {
                if (!isWithinTheTime()) {
                    String messageTime = config.getString("message-notime");
                    commandSender.sendMessage(messageFront + ChatColor.RED + messageTime);
                    return false;
                }
                List<String> blessings = config.getStringList("Blessings");
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    commandSender.sendMessage(messageFront + ChatColor.GREEN + "————————————————祝福列表————————————————————");
                    for (String command_ : blessings) {
                        commandSender.sendMessage(messageFront + ChatColor.WHITE + command_);
                    }
                    commandSender.sendMessage(messageFront + ChatColor.GREEN + "—————————————————————————————————————————————");
                });
            } else if (Message0.equals("reload")) {
                if (commandSender.hasPermission("nyb.commands.reload")) {
                    plugin.reloadConfig();
                    String messageReload = config.getString("message-reload");
                    commandSender.sendMessage(messageFront + ChatColor.GREEN + messageReload);
                } else {
                    String messageNo = config.getString("message-no");
                    commandSender.sendMessage(messageFront + ChatColor.RED + messageNo);
                }
            } else {
                String messageError = config.getString("message-error");
                commandSender.sendMessage(messageFront + ChatColor.RED + messageError);
            }
        } else {
            String messageError = config.getString("message-error");
            commandSender.sendMessage(messageFront + ChatColor.RED + messageError);
        }
        return false;
    }
}
