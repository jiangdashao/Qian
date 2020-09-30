package me.rerere.qian.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import me.rerere.qian.Qian;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class PlayerEcoData {
    private final String uuid;
    private final String name;
    private final double balance;

    public static PlayerEcoData newData(Player player) {
        return new PlayerEcoData(player.getUniqueId().toString(), player.getName(), Qian.getInstance().getConfigLoader().getMainConfig().getInitialBalance());
    }
}
