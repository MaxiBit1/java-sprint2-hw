import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

/** Класс для считывания и работы с данными с годового года
 * @author Max Vasilyev
 * @version 1.0
 */
public class YearlyReport {

    /** Поле хранения данных с годового файла в виде хеш-таблицы */
    HashMap<Integer, YearData> mapDataYear = new HashMap<>();
    /** Поле пути до годового файла */
    String path;

    /**
     * Внутренний класс данных со свойствами <b>month</b>,<b>costSpending</b> и <b>costIncome</b>
     */
    class YearData {
        /** Поле числа месяца */
        int month;
        /** Поле количество трат */
        int costSpending;
        /** Поле количетсва дорхода */
        int costIncome;

        /** Конструктор - для создания объектов со свойствами
         * @param month - число месяца
         * @param cost - сумма
         * @param isExpense - трата или доход
         */
        public YearData(int month, int cost, boolean isExpense) {
            this.month = month;
            if (isExpense) {
                costSpending = cost;
            } else {
                costIncome = cost;
            }
        }
    }

    /** Метод для получения данных и сохранение их в хеш-таблице */
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

    /** Процесс счтения файла с определенным параметром
     * @param path - название пути до файла
     * @return возвращает считыванный файл
     */
    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории.");
            return null;
        }
    }

    /** Метод получения номера месяца с определенным параметром
     * @param indexMap - индекс хэш-таблицы
     * @return возвращает номер месяца
     */
    int monthNumber(int indexMap) {
        return mapDataYear.get(indexMap).month;
    }

    /** Метод проверки данных на трату с определенным параметром
     * @param mapIndex - идекс хеш-таблицы
     * @return возвращает true если сумма дохода не равна 0
     */
    boolean isIncome(int mapIndex) {
        YearData yearData = mapDataYear.get(mapIndex);
        return yearData.costIncome != 0;
    }

    /** Метод проверки считки годового файла
     * @return возвращает результат проверки
     */
    boolean checkYear() {
        if (mapDataYear.isEmpty()) {
            System.out.println("Вы не считали данные за год");
            return false;
        }
        return true;
    }

    /** Метод получение суммы дохода или трат
     * @param mapIndex - индекс хэш-таблицы
     * @param checkMonth - трата или доход
     * @return возвращение суммы
     */
    int sumSpendOrIncome(int mapIndex, boolean checkMonth) {
        YearData yearData = mapDataYear.get(mapIndex);
        if (checkMonth) {
            return yearData.costIncome;
        }
        return yearData.costSpending;
    }

    /** Метод получения года
     * @return возвращает номер года
     */
    String getNameYear() {
        return path.substring(12, 16);
    }

    /** Метод получения прибыли за месяца
     * @param montNum - номер месяца
     * @return возвращает прибыль
     */
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

    /** Метод получения среднего дохода за год
     * @return возвращает средний доход
     */
    double meanCostIncome() {
        double sum = 0;
        for (int i = 1; i < mapDataYear.size(); i++) {
            sum += mapDataYear.get(i).costIncome;
        }
        sum /= ((double) mapDataYear.size() / 2);
        return sum;
    }

    /** Метод получения средней траты за год
     * @return возвращает среднюю трату
     */
    double meanCostSpending() {
        double sum = 0;
        for (int i = 1; i < mapDataYear.size(); i++) {
            sum += mapDataYear.get(i).costSpending;
        }
        sum /= ((double) mapDataYear.size() / 2);
        return sum;
    }
}
