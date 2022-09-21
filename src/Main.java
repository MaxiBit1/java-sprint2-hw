import java.util.Scanner;

public class Main {
    static MonthlyReport monthlyReport = new MonthlyReport();
    static YearlyReport yearlyReport = new YearlyReport();

    public static void main(String[] args) {
        // Поехали!
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                monthlyReport.readMonthCSV();
                System.out.println("Данные с месячных отчетов считаны");
            } else if (command == 2) {
                yearlyReport.readYearCSV();
                System.out.println("Данные с годового отчета считаны");
            } else if (command == 3) {
                checkMonthAndYear();
            } else if (command == 4) {
                showMonthlyInfo();
            } else if (command == 5) {
                showYearlyInfo();
            } else if (command == 6) {
                break;
            } else {
                System.out.println("Неизвестная комманда, введите другую команду!!!");
            }
        }
    }

    public static void showMenu() {
        System.out.println("=".repeat(25));
        System.out.println("1 - Считать все месячные рассчеты");
        System.out.println("2 - Считать годовой отчет");
        System.out.println("3 - Сверить отчеты");
        System.out.println("4 - Вывести информацию о месячных отчетах");
        System.out.println("5 - Вывести информацию о годовом отчете");
        System.out.println("6 - Выход");
    }

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

    public static void showMonthlyInfo(){
        if(!monthlyReport.checkMonth()){
            return;
        }
        for(int i = 1; i < 4;i++){
            System.out.println("=".repeat(25));
            System.out.println("Месяц: " + nameMonth(i));
            monthlyReport.maxIncomeThingIncome(i);
            monthlyReport.maxIncomeThingSpending(i);
        }
    }

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

    public static String nameMonth(int month){
        if(month == 1){
            return "Январь";
        }else if(month == 2){
            return "Февраль";
        }else {
            return "Март";
        }
    }
}

