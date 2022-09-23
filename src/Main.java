import java.util.Scanner;

/**
 * Главный класс приложения
 * @author Max Vasilyev
 * @version 1.0
 */
public class Main {

    /** Главный метод приложения, взаимодейтсвующий с пользователем */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            int command = scanner.nextInt();
            if (command == 1) {
                ServiceBisnesLogic.readCSV(1);
                System.out.println("Данные с месячных отчетов считаны");
            } else if (command == 2) {
                ServiceBisnesLogic.readCSV(2);
                System.out.println("Данные с годового отчета считаны");
            } else if (command == 3) {
                ServiceBisnesLogic.checkMonthAndYear();
            } else if (command == 4) {
                ServiceBisnesLogic.showMonthlyInfo();
            } else if (command == 5) {
                ServiceBisnesLogic.showYearlyInfo();
            } else if (command == 6) {
                break;
            } else {
                System.out.println("Неизвестная комманда, введите другую команду!!!");
            }
        }
    }

    /** Метод реализации печати пунктов меню */
    public static void showMenu() {
        System.out.println("=".repeat(25));
        System.out.println("1 - Считать все месячные рассчеты");
        System.out.println("2 - Считать годовой отчет");
        System.out.println("3 - Сверить отчеты");
        System.out.println("4 - Вывести информацию о месячных отчетах");
        System.out.println("5 - Вывести информацию о годовом отчете");
        System.out.println("6 - Выход");
    }
}

