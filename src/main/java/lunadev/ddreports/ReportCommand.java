package lunadev.ddreports;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ReportCommand implements CommandExecutor {
    private final ReportManager reportManager;
    private final DDReports plugin;

    public ReportCommand(ReportManager reportManager, DDReports plugin) {
        this.reportManager = reportManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.incorrect-usage").replace("%usage%", "/report <ник> <причина>")));
            return true;
        }

        Player reporter = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            reporter.sendMessage(plugin.format(plugin.getConfig().getString("messages.player-not-found")));
            return true;
        }

        // Собираем причину жалобы, начиная с индекса 1 до конца массива аргументов
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        reportManager.addReport(reporter.getName(), target.getName(), reason);

        reporter.sendMessage(plugin.format(plugin.getConfig().getString("messages.report-submitted")
                .replace("%target%", target.getName())
                .replace("%reason%", reason)));

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("ddreports.view"))
                .forEach(p -> p.sendMessage(plugin.format(plugin.getConfig().getString("messages.report-received")
                        .replace("%player%", reporter.getName())
                        .replace("%target%", target.getName())
                        .replace("%reason%", reason))));

        return true;
    }
}
