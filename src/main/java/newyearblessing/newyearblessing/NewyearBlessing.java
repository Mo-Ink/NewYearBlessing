package newyearblessing.newyearblessing;

import newyearblessing.newyearblessing.commands.papi;
import newyearblessing.newyearblessing.commands.maincommand;
import org.bukkit.plugin.java.JavaPlugin;
import newyearblessing.newyearblessing.metrics.Metrics;
import newyearblessing.newyearblessing.commands.PlaceholderHook;

public final class NewyearBlessing extends JavaPlugin {
    private PlaceholderHook placeholderHook;
    @Override
    public void onEnable()
    {
        // All you have to do is adding the following two lines in your onEnable method.
        // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
        int pluginId = 10347; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        // Optional: Add custom charts
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
        placeholderHook = new PlaceholderHook();
        new papi().register();
        // Plugin startup logic
        this.saveDefaultConfig();
        getConfig().options().copyDefaults();
        getCommand("nyb").setExecutor(new maincommand());
    }
    @Override
    public void onDisable() {
        saveDefaultConfig();
        // Plugin shutdown logic
    }
}
