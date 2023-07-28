import java.util.Scanner;

public  class Wallet {

    public static void main(String[] args) {
        Balance balance = new Balance();
        balance.parseDataFile();

        System.out.println("Привет");

        Scanner scanner = new Scanner(System.in);
        String s ;
        String console = """
                Выберите операцию:\s
                1 узнать баланс\s
                2 отправить\s
                3 получить\s
                Введите слово "exit" для выхода.""";
        System.out.println(console);
        do
        {
            s = scanner.nextLine();
            if(s.equalsIgnoreCase("1")){
                System.out.println("Ваш баланс: " + balance.currentValue() + "\n");
                System.out.println( console);
            }
            else if (s.equalsIgnoreCase("2")) {
                System.out.println("Операция отправить");
                System.out.println("Введите сумму: ");
                try {
                balance.toSendMoney(scanner.nextDouble());}
                catch (Exception e){
                    System.out.println("Ошибка. Не корректное значение.");
                }
                System.out.println( console );
            }
            else if (s.equalsIgnoreCase("3")) {
                System.out.println("Операция получить");
                System.out.println("Введите сумму: ");
                try {
                    balance.toGetMoney(scanner.nextDouble());
                }
                catch (Exception e){
                    System.out.println("Ошибка. Не корректное значение.");
                }
                System.out.println(console);
            }
        }
        while (!s.equals("exit"));
        System.out.println("Программа завершена");
    }
 }