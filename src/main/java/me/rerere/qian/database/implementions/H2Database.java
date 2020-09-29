package me.rerere.qian.database.implementions;

import me.rerere.qian.data.PlayerEcoData;
import me.rerere.qian.database.IDatabase;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class H2Database implements IDatabase {
    @Override
    public String getName() {
        return "H2";
    }

    @Override
    public boolean init() {
        return false;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public boolean containsData(UUID uuid) {
        return false;
    }

    @Override
    public Optional<PlayerEcoData> query(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public void update(PlayerEcoData data) {

    }

    @Override
    public void batchUpdate(Set<PlayerEcoData> dataSet) {

    }
}
