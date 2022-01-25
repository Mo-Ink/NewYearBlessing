package newyearblessing.newyearblessing.commands;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class papi extends PlaceholderExpansion {
    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public String getAuthor(){
        return "someauthor";
    }
    @Override
    public String getIdentifier(){
        return "nyb";
    }
    @Override
    public String getVersion(){
        return "1.0.0";
    }
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        Plugin plugin = newyearblessing.newyearblessing.NewyearBlessing.getProvidingPlugin(newyearblessing.newyearblessing.NewyearBlessing.class);
        for (int i = identifier.length();--i>=0;)
        {
            if (!Character.isDigit(identifier.charAt(i))){
                return "null";
            }
        }
        try {
            int num = Integer.parseInt(identifier);
            List<String> blessing = plugin.getConfig().getStringList("Blessing");
            String str = blessing.get(num * 2 - 2);
            return str;
        }
        catch (Exception e)
        {
            return "null";
        }
    }
}