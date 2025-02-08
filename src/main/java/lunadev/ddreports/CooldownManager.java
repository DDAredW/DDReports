package lunadev.ddreports;

import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final DDReports plugin;

    public CooldownManager(DDReports plugin) {
        this.plugin = plugin;
    }

    public boolean hasCooldown(UUID uuid) {
        if (!cooldowns.containsKey(uuid)) return false;

        long timeLeft = getRemainingTime(uuid);
        if (timeLeft <= 0) {
            cooldowns.remove(uuid);
            return false;
        }
        return true;
    }

    public long getRemainingTime(UUID uuid) {
        if (!cooldowns.containsKey(uuid)) return 0;

        return cooldowns.get(uuid) + (plugin.getConfig().getLong("cooldown.time") * 1000)
                - System.currentTimeMillis();
    }

    public void setCooldown(UUID uuid) {
        cooldowns.put(uuid, System.currentTimeMillis());
    }
}