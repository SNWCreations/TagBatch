package snw.tagbatch;

import org.bukkit.plugin.java.JavaPlugin;

import static java.util.Objects.requireNonNull;

public final class TagBatch extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        requireNonNull(getCommand("tagbatch")).setExecutor(new TagBatchCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
