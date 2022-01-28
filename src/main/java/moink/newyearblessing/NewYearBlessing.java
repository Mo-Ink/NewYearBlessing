package moink.newyearblessing;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class NewYearBlessing extends JavaPlugin {
    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getConfig().options().copyDefaults();
        getCommand("newyearblessing").setExecutor(new MainCommand());
    }

    @Override
    public void onDisable() {
        saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }
}
