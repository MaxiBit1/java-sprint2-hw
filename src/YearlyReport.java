import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class YearlyReport {

    HashMap<Integer, YearData> mapDataYear = new HashMap<>();
    String path;

    class YearData {
        int month;
        int costSpending;
        int costIncome;

        public YearData(int month, int cost, boolean isExpense) {
            this.month = month;
            if (isExpense) {
                costSpending = cost;
            } else {
                costIncome = cost;
            }
        }
    }

    void readYearCSV() {
        path = "resources/y.2021.csv";
        String strYear = readFileContentsOrNull(path);
        String[] lines = strYear.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] linesRecd = lines[i].split(",");
            int month = Integer.parseInt(linesRecd[0]);
            int cost = Integer.parseInt(linesRecd[1]);
            boolean isExpense = Boolean.parseBoolean(linesRecd[2]);
            mapDataYear.put(i, new YearData(month, cost, isExpense));
        }
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    int monthNumber(int indexMap) {
        return mapDataYear.get(indexMap).month;
    }

    boolean isIncome(int monthIndex) {
        YearData yearData = mapDataYear.get(monthIndex);
        return yearData.costIncome != 0;
    }

    boolean checkYear() {
        if (mapDataYear.isEmpty()) {
            System.out.println("Вы не считали данные за год");
            return false;
        }
        return true;
    }

    int sumSpendOrIncome(int monthIndex, boolean checkMonth) {
        YearData yearData = mapDataYear.get(monthIndex);
        if (checkMonth) {
            return yearData.costIncome;
        }
        return yearData.costSpending;

    }

    String getNameYear() {
        return path.substring(12, 16);
    }

    int profitYearIncome(int montNum) {
        boolean checkIsIncome;
        int a = 0;
        int b = 0;
        for (int i = 1; i < mapDataYear.size(); i++) {
            checkIsIncome = isIncome(i);
            if (mapDataYear.get(i).month == montNum && a == 0) {
                if (checkIsIncome) {
                    a = mapDataYear.get(i).costIncome;
                } else {
                    a = mapDataYear.get(i).costSpending;
                }
            } else if (mapDataYear.get(i).month == montNum) {
                if (checkIsIncome) {
                    b = mapDataYear.get(i).costIncome;
                } else {
                    b = mapDataYear.get(i).costSpending;
                }
            }

        }
        return a - b;
    }


    double meanCostIncome() {
        double sum = 0;
        for (int i = 1; i < mapDataYear.size(); i++) {
            sum += mapDataYear.get(i).costIncome;
        }
        sum /= ((double) mapDataYear.size() / 2);
        return sum;
    }

    double meanCostSpending() {
        double sum = 0;
        for (int i = 1; i < mapDataYear.size(); i++) {
            sum += mapDataYear.get(i).costSpending;
        }
        sum /= ((double) mapDataYear.size() / 2);
        return sum;
    }
}
