package lunadev.ddreports;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReportsCommand implements CommandExecutor {
    private final ReportManager reportManager;
    private final DDReports plugin;

    public ReportsCommand(ReportManager reportManager, DDReports plugin) {
        this.reportManager = reportManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("clear")) {
            if (!sender.hasPermission("ddreports.manage")) {
                sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.no-permission")));
                return true;
            }

            reportManager.clearReports();
            sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.report-cleared")));
            return true;
        }

        if (!sender.hasPermission("ddreports.view")) {
            sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.no-permission")));
            return true;
        }

        if (reportManager.isEmpty()) {
            sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.no-reports")));
            return true;
        }

        sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.report-list")));
        reportManager.getReports().forEach(report ->
                sender.sendMessage(plugin.format(plugin.getConfig().getString("messages.report-entry")
                        .replace("%report%", report))));

        return true;
    }
}