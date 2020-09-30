package me.rerere.qian.database;

import me.rerere.qian.data.PlayerEcoData;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface IDatabase {
    String getName();

    boolean init();

    void shutdown();

    boolean containsData(UUID uuid);

    Optional<PlayerEcoData> query(UUID uuid);

    void insert(PlayerEcoData data);

    void update(PlayerEcoData data);

    void batchUpdate(Set<PlayerEcoData> dataSet);
}
