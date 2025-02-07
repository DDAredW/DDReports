package lunadev.ddreports;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private final List<String> reports = new ArrayList<>();

    // Добавление новой жалобы
    public void addReport(String reporter, String target, String reason) {
        reports.add(reporter + " -> " + target + ": " + reason);
    }

    // Получение списка всех репортов
    public List<String> getReports() {
        return reports;
    }

    // Очистка всех репортов
    public void clearReports() {
        reports.clear();
    }

    // Проверка, есть ли репорты
    public boolean isEmpty() {
        return reports.isEmpty();
    }
}
