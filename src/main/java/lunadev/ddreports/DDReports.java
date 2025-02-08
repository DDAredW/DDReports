package lunadev.ddreports;

import org.bukkit.plugin.java.JavaPlugin;

public class DDReports extends JavaPlugin {
    private static DDReports instance;
    private ReportManager reportManager;

    @Override
    public void onEnable() {
        instance = this;
        reportManager = new ReportManager();
        saveDefaultConfig();
        getCommand("report").setExecutor(new ReportCommand(reportManager, this));
        getCommand("reports").setExecutor(new ReportsCommand(reportManager, this));
        getLogger().info("DDReports успешно загружен!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DDReports выключен.");
    }

    public static DDReports getInstance() {
        return instance;
    }

    public String format(String message) {
        return message.replace("&", "§");
    }
}