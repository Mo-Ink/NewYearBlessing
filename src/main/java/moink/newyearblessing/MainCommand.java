package moink.newyearblessing;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Plugin plugin = NewYearBlessing.getProvidingPlugin(NewYearBlessing.class);
        String messageFront = ChatColor.YELLOW + plugin.getConfig().getString("message-front");
        if (commandSender instanceof ConsoleCommandSender) {
            String messageConsole = plugin.getConfig().getString("message-console");
            commandSender.sendMessage(ChatColor.YELLOW + messageFront + ChatColor.AQUA + messageConsole);
            return false;
        }
        Player player = (Player) commandSender;
        if (strings.length == 0) {
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "插件用法如下：");
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "/nyb add <内容> - 许愿并获得奖励");
            commandSender.sendMessage(messageFront + ChatColor.AQUA + "/nyb list - 查看所有人的许愿");
            if (commandSender.isOp())
                commandSender.sendMessage(messageFront + ChatColor.AQUA + "/nyb reload - 重载插件");
            return false;
        }
        if (strings.length == 2) {
            String Message0 = strings[0];
            if (Message0.equals("add")) {
                if (!Utils.isWithinTheTime()) {
                    String messageTime = plugin.getConfig().getString("message-notime");
                    commandSender.sendMessage(messageFront + ChatColor.RED + messageTime);
                    return false;
                }
                String Message1 = strings[1];
                List<String> blessing = plugin.getConfig().getStringList("Blessing");
                for (String command_ : blessing) {
                    if (command_.equals(player.getName())) {
                        String messageAlready = plugin.getConfig().getString("message-already");
                        commandSender.sendMessage(messageFront + ChatColor.RED + messageAlready);
                        return false;
                    }
                }
                blessing.add(Message1 + " §7——" + player.getName());
                blessing.add(player.getName());
                plugin.getConfig().set("Blessing", blessing);
                plugin.saveConfig();
                String messageUp = plugin.getConfig().getString("message-upload");
                commandSender.sendMessage(messageFront + ChatColor.GREEN + messageUp);

                String reward = plugin.getConfig().getString("reward");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), reward.replace("%player_name%", player.getName()));
                return false;
            } else {
                String messageError = plugin.getConfig().getString("message-error");
                commandSender.sendMessage(messageFront + ChatColor.RED + messageError);
            }
        } else if (strings.length == 1) {
            String Message0 = strings[0];
            if (Message0.equals("list")) {
                if (!Utils.isWithinTheTime()) {
                    String messageTime = plugin.getConfig().getString("message-notime");
                    commandSender.sendMessage(messageFront + ChatColor.RED + messageTime);
                    return false;
                }
                String messageList = plugin.getConfig().getString("message-list");
                commandSender.sendMessage(messageFront + ChatColor.GREEN + messageList);
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
                    commandSender.sendMessage(messageFront + ChatColor.GREEN + messageReload);
                } else {
                    String messageNo = plugin.getConfig().getString("message-no");
                    commandSender.sendMessage(messageFront + ChatColor.RED + messageNo);
                }
            } else {
                String messageError = plugin.getConfig().getString("message-error");
                commandSender.sendMessage(messageFront + ChatColor.RED + messageError);
            }
        } else {
            String messageError = plugin.getConfig().getString("message-error");
            commandSender.sendMessage(messageFront + ChatColor.RED + messageError);
        }
        return false;
    }
}
