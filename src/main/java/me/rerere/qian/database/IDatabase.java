package me.rerere.qian.database;

import me.rerere.qian.data.PlayerEcoData;

import java.util.Optional;
import java.util.UUID;

public interface IDatabase {
    boolean init();

    void shutdown();

    Optional<PlayerEcoData> query(UUID uuid);

    void update(UUID uuid, PlayerEcoData data);
}
