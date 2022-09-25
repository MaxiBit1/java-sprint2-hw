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
            switch (command){
                case 1:
                    ServiceBisnesLogic.readCSV(1);
                    System.out.println("Данные с месячных отчетов считаны");
                    break;
                case 2:
                    ServiceBisnesLogic.readCSV(2);
                    System.out.println("Данные с годового отчета считаны");
                    break;
                case 3:
                    ServiceBisnesLogic.checkMonthAndYear();
                    break;
                case 4:
                    ServiceBisnesLogic.showMonthlyInfo();
                    break;
                case 5:
                    ServiceBisnesLogic.showYearlyInfo();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Неизвестная комманда, введите другую команду!!!");
                    break;
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

