package moink.newyearblessing;

import org.bukkit.plugin.Plugin;

import java.util.Calendar;

public class Utils {
    public static boolean isWithinTheTime() {
        Plugin plugin = NewYearBlessing.getProvidingPlugin(NewYearBlessing.class);
        int startYear = plugin.getConfig().getInt("start-year");
        int startMonth = plugin.getConfig().getInt("start-month");
        int startDay = plugin.getConfig().getInt("start-day");
        int endYear = plugin.getConfig().getInt("end-year");
        int endMonth = plugin.getConfig().getInt("end-month");
        int endDay = plugin.getConfig().getInt("end-day");

        Calendar date = Calendar.getInstance(); //当前时间

        Calendar begin = Calendar.getInstance();
        begin.set(startYear, startMonth - 1, startDay, 0, 0, 0);

        Calendar end = Calendar.getInstance();
        end.set(endYear, endMonth - 1, endDay, 23, 59, 59);

        if (date.after(begin) && date.before(end))
            return true;
        else
            return false;
    }
}
