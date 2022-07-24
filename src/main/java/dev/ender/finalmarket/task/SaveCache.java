package dev.ender.finalmarket.task;

import dev.ender.finalmarket.database.Cache;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveCache extends BukkitRunnable {
    @Override
    public void run() {
        Cache.saveAll();
    }
}
