package me.rerere.qian.data;

import me.rerere.qian.Qian;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class BalanceManager {
    private final Map<UUID, PlayerEcoData> cache = new HashMap<>();

    public void asyncLoad(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(Qian.getInstance(), () -> {
            UUID uuid = player.getUniqueId();
            Optional<PlayerEcoData> optionalPlayerEcoData =
                    Qian.getInstance().getDatabaseManager().getDatabase().query(uuid);
            if (optionalPlayerEcoData.isPresent()) {
                cache.put(uuid, optionalPlayerEcoData.get());
            } else {
                PlayerEcoData initData = PlayerEcoData.newData(player);
                Qian.getInstance().getDatabaseManager().getDatabase().insert(initData);
            }
        });
    }

    public void asyncSave(UUID uuid) {
        if (!this.cache.containsKey(uuid)) {
            return;
        }
        PlayerEcoData data = this.cache.get(uuid);
        Bukkit.getScheduler().runTaskAsynchronously(Qian.getInstance(), () -> Qian.getInstance().getDatabaseManager().getDatabase().update(data));
    }
}
