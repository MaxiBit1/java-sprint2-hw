/**
 * Класс реализующий бизнес-логику
 * @author Max Vasilyev
 * @version 1.0
 */
public class ServiceBisnesLogic {

    static MonthlyReport monthlyReport = new MonthlyReport();
    static YearlyReport yearlyReport = new YearlyReport();

    /** Метод чтения месячных файлов или годового файла */
    public static void readCSV(int command){
        switch (command){
            case 1:
                monthlyReport.readMonthCSV();
                break;
            case 2:
                yearlyReport.readYearCSV();
                break;
        }
    }

    /** Метод сравнения данных месяцев и года */
    public static void checkMonthAndYear() {
        if(!monthlyReport.checkMonth() && !yearlyReport.checkYear()){
            return;
        }else if(!monthlyReport.checkMonth()){
            return;
        }else if(!yearlyReport.checkYear()){
            return;
        }
        boolean isNotError = true;
        for (int i = 1;i<yearlyReport.mapDataYear.size();i++){
            int monthNum = yearlyReport.monthNumber(i);
            boolean isIncomeOrSpending = yearlyReport.isIncome(i);
            int monthCosts = monthlyReport.sumSpendOrIncome(monthNum,isIncomeOrSpending);
            int yearCosts = yearlyReport.sumSpendOrIncome(i,isIncomeOrSpending);
            if(yearCosts!=monthCosts){
                System.out.println("В " + monthNum + " месяце обнаружена ошибка");
                isNotError = false;
            }
        }
        if(isNotError){
            System.out.println("Ошибок не обнаружено");
        }
    }

    /** Метод вывода информации по месяцам */
    public static void showMonthlyInfo(){
        if(!monthlyReport.checkMonth()){
            return;
        }
        for(int i = 1; i < 4;i++){
            System.out.println("=".repeat(25));
            System.out.println("Месяц: " + nameMonth(i));
            monthlyReport.maxThingIncome(i);
            monthlyReport.maxThingSpending(i);
        }
    }

    /** Метод вывода информации по году */
    public static void showYearlyInfo(){
        System.out.println("=".repeat(25));
        if(!yearlyReport.checkYear()){
            return;
        }
        System.out.println(yearlyReport.getNameYear() + " год");
        for (int i = 1; i <= 3;i++){
            System.out.println("Прибыль за " + i + " месяц:" + yearlyReport.profitYearIncome(i));
        }
        System.out.println("Средний расход за все месяцы: " + yearlyReport.meanCostIncome());
        System.out.println("Средний доход за все месяцы: " + yearlyReport.meanCostSpending());
    }

    /** Метод для получения названия месяца
     * @param month - номер месяца
     * @return возвращает название месяца
     */
    public static String nameMonth(int month){
        String result = "";
        switch (month){
            case 1:
                result = "Январь";
                break;
            case 2:
                result = "Февраль";
                break;
            case 3:
                result = "Март";
                break;
        }
        return result;
    }
}
