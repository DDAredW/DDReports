package lunadev.ddreports;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {
    private final List<String> reports;

    public ReportManager() {
        this.reports = new ArrayList<>();
    }

    public void addReport(String reporter, String target, String reason) {
        String report = String.format("§7[§c!§7] §f%s §7-> §f%s§7: §f%s", reporter, target, reason);
        reports.add(report);
    }

    public List<String> getReports() {
        return new ArrayList<>(reports);
    }

    public void clearReports() {
        reports.clear();
    }

    public boolean isEmpty() {
        return reports.isEmpty();
    }
}