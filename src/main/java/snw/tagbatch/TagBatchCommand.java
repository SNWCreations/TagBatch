package snw.tagbatch;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class TagBatchCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "未提供一个标签");
                return false;
            }
            String tag = args[0];
            new TagBatchGUI(tag, player.getUniqueId()).show();
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "不受支持的执行者");
        }
        return true;
    }
}
