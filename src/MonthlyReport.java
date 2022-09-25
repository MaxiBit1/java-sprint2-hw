import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс считывания и подсчета месячных данных
 * @author Max Vasilyev
 * @version 1.0
 */

public class MonthlyReport {


    HashMap<Integer, ArrayList<MonthlyLine>> mapMonth = new HashMap<>();

    /**
     * Внутренний класс данных со свойствами <b>nameIsItem</b>,<b>costSpending</b> и <b>costIncome</b>
     */
    class MonthlyLine {

        String nameIsItem;
        int costSpending;
        int costIncome;

        /** Конструктор - создание объектов с определенными значениями
         * @param nameIsItem - название товара
         * @param isExpense - проверка на трату или доход
         * @param quantity - количество товара
         * @param sumOfOne - цена за один товар
         */
        MonthlyLine(String nameIsItem, boolean isExpense, int quantity, int sumOfOne) {
            this.nameIsItem = nameIsItem;
            if (isExpense) {
                costSpending += quantity * sumOfOne;
            } else {
                costIncome += quantity * sumOfOne;
            }
        }
    }

    /** Процедура создания объектов и сохранения их в поле хеш-таблицы */
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

    /** Метод считывания файла с определенным значением
     * @param path - путь до файла
     * @return возвращает считанный файл
     */
    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    /** Процесс проверки заполности хеш-таблицы
     * @return возвращения результата проверки
     */
    boolean checkMonth() {
        if (mapMonth.isEmpty()) {
            System.out.println("Вы не считали данные за месяцы");
            return false;
        }
        return true;
    }

    /** Метод для подсчета всех трад или доходов
     * @param monthNumber - номер месяца
     * @param checkIncomeOrSpending - проверка на трату или доход
     * @return сумму траты или дохода
     */
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

    /** Процесс нахождения товара с максимальным доходом
     * @param month - номер месяца
     */
    void maxThingIncome(int month) {
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

    /** Процесс нахождения товара с максимальнjq тратой
     * @param month - номер месяца
     */
    void maxThingSpending(int month) {
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
