import java.util.Scanner;

public class PasswordManager {
    private static final String CORRECT_PASSWORD = "dima"; // Замените "your_password" на ваш реальный пароль

    public static boolean authenticate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите пароль: ");
        String enteredPassword = scanner.nextLine();

        if (enteredPassword.equals(CORRECT_PASSWORD)) {
            System.out.println("Пароль верный. Доступ разрешен.");
            return true;
        } else {
            System.out.println("Неверный пароль. Доступ запрещен.");
            return false;
        }
    }
}
