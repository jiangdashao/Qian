package me.rerere.qian.listener;

import me.rerere.qian.Qian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Qian.getInstance().getBalanceManager().asyncLoad(player);
    }
}
