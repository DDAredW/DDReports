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
    private final CooldownManager cooldownManager;

    public ReportCommand(ReportManager reportManager, DDReports plugin) {
        this.reportManager = reportManager;
        this.plugin = plugin;
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игроки могут использовать эту команду!");
            return true;
        }

        Player reporter = (Player) sender;

        // Проверка cooldown
        if (cooldownManager.hasCooldown(reporter.getUniqueId())) {
            long remainingTime = cooldownManager.getRemainingTime(reporter.getUniqueId()) / 1000;
            reporter.sendMessage(plugin.format(plugin.getConfig().getString("messages.cooldown")
                    .replace("%time%", String.valueOf(remainingTime))));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.incorrect-usage")
                    .replace("%usage%", "/report <ник> <причина>")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            reporter.sendMessage(plugin.format(plugin.getConfig().getString("messages.player-not-found")));
            return true;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        reportManager.addReport(reporter.getName(), target.getName(), reason);

        // Установка cooldown
        cooldownManager.setCooldown(reporter.getUniqueId());

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