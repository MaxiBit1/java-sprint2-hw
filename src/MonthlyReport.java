import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {

    HashMap<Integer, ArrayList<MonthlyLine>> mapMonth = new HashMap<>();

    class MonthlyLine {

        String nameIsItem;
        int costSpending;
        int costIncome;

        MonthlyLine(String nameIsItem, boolean isExpense, int quantity, int sumOfOne) {
            this.nameIsItem = nameIsItem;
            if (isExpense) {
                costSpending += quantity * sumOfOne;
            } else {
                costIncome += quantity * sumOfOne;
            }
        }
    }

    void readMonthCSV() {
        ArrayList<MonthlyLine> arrayOfObjects;
        for (int i = 1; i < 4; i++) {
            arrayOfObjects = new ArrayList<>();
            String monthStr = readFileContentsOrNull("resources/m.20210" + i + ".csv");
            String[] linesStr = monthStr.split("\n");
            for (int l = 1; l < linesStr.length; l++) {
                String[] lineElement = linesStr[l].split(",");
                String nameIsItem = lineElement[0];
                boolean isExpense = Boolean.parseBoolean(lineElement[1]);
                int quantity = Integer.parseInt(lineElement[2]);
                int sumOfOne = Integer.parseInt(lineElement[3]);
                arrayOfObjects.add(new MonthlyLine(nameIsItem, isExpense, quantity, sumOfOne));
            }
            mapMonth.put(i, arrayOfObjects);
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

    boolean checkMonth() {
        if (mapMonth.isEmpty()) {
            System.out.println("Вы не считали данные за месяцы");
            return false;
        }
        return true;
    }

    int sumSpendOrIncome(int monthNumber, boolean checkIncomeOrSpending) {

        ArrayList<MonthlyLine> arrayMonthly = mapMonth.get(monthNumber);
        int sum = 0;
        for (MonthlyLine monthlyLine : arrayMonthly) {
            if (!checkIncomeOrSpending) {
                sum += monthlyLine.costSpending;
            } else {
                sum += monthlyLine.costIncome;
            }
        }
        return sum;
    }


    void maxIncomeThingIncome(int month) {
        int resultMax = 0;
        for (int i = 0; i < mapMonth.get(month).size(); i++) {
            resultMax = Integer.max(resultMax, mapMonth.get(month).get(i).costIncome);
        }
        for (int i = 0; i < mapMonth.get(month).size(); i++) {
            if (resultMax == mapMonth.get(month).get(i).costIncome) {
                System.out.println("Товар:\t" + mapMonth.get(month).get(i).nameIsItem);
                System.out.println("Сумма товара:\t" + mapMonth.get(month).get(i).costIncome);
                break;
            }
        }
    }

    void maxIncomeThingSpending(int month) {
        int resultMax = 0;
        for (int i = 0; i < mapMonth.get(month).size(); i++) {
            resultMax = Integer.max(resultMax, mapMonth.get(month).get(i).costSpending);
        }
        for (int i = 0; i < mapMonth.get(month).size(); i++) {
            if (resultMax == mapMonth.get(month).get(i).costSpending) {
                System.out.println("Товар:\t" + mapMonth.get(month).get(i).nameIsItem);
                System.out.println("Сумма товара:\t" + mapMonth.get(month).get(i).costSpending);
                break;
            }
        }
    }
}
