package me.rerere.qian.listener;

import me.rerere.qian.Qian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Qian.getInstance().getBalanceManager().asyncLoad(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        
    }
}
