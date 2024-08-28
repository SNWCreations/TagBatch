package snw.tagbatch;

import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import snw.srs.gui.AbstractPlayersSelectorGUI;

import java.util.OptionalInt;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TagBatchGUI extends AbstractPlayersSelectorGUI {
    private final String tag;
    private final Set<UUID> original;

    public TagBatchGUI(String tag) {
        super(
                JavaPlugin.getPlugin(TagBatch.class),
                "选择将要拥有 " + tag + " 标签的玩家"
        );
        this.tag = tag;
        Set<UUID> matching = Bukkit.getOnlinePlayers()
                .stream()
                .filter(it -> it.getScoreboardTags().contains(tag))
                .map(Player::getUniqueId)
                .collect(Collectors.toSet());
        this.selectedPlayers.addAll(matching);
        this.original = ImmutableSet.copyOf(this.selectedPlayers);
    }

    @Override
    protected OptionalInt getMaxSelectablePlayers() {
        return OptionalInt.empty();
    }

    @Override
    protected boolean onSubmit(Player clicker, Set<UUID> set) {
        CollectionDiff<UUID> diff = CollectionDiff.create(original, set);
        diff.added().forEach(uuid -> {
            OfflinePlayer offlineHandle = Bukkit.getOfflinePlayer(uuid);
            Player handle = offlineHandle.getPlayer();
            if (handle != null) {
                handle.addScoreboardTag(tag);
                clicker.sendMessage(
                        "%s成功向 %s 添加了 %s 标签"
                                .formatted(ChatColor.GREEN, handle.getName(), tag)
                );
            } else {
                clicker.sendMessage(
                        "%s由于 %s 不在线，无法向其添加 %s 标签"
                                .formatted(ChatColor.RED, offlineHandle.getName(), tag)
                );
            }
        });
        diff.removed().forEach(uuid -> {
            OfflinePlayer offlineHandle = Bukkit.getOfflinePlayer(uuid);
            Player handle = offlineHandle.getPlayer();
            if (handle != null) {
                handle.removeScoreboardTag(tag);
                clicker.sendMessage(
                        "%s成功从 %s 移除了 %s 标签"
                                .formatted(ChatColor.GREEN, handle.getName(), tag)
                );
            } else {
                clicker.sendMessage(
                        "%s由于 %s 不在线，无法从其移除 %s 标签"
                                .formatted(ChatColor.RED, offlineHandle.getName(), tag)
                );
            }
        });
        return true;
    }

    @Override
    protected void afterCancel(Player player) {
    }

    @Override
    protected boolean requireNonEmptySelectedPlayersSet() {
        return true;
    }
}
