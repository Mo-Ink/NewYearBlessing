package moink.newyearblessing;

import moink.newyearblessing.commands.MainCommand;
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
        saveDefaultConfig();
    }
}
