import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Balance implements IfaceBalance {
    private BigDecimal currentValue = BigDecimal.ZERO;
    private final ArrayList<BigDecimal> receivedBigDecimalList = new ArrayList<>();
    private final ArrayList<BigDecimal> spendingBigDecimalList = new ArrayList<>();
    private final ArrayList<Date> datesReceivedList = new ArrayList<>();
    private final ArrayList<Date> dateSpendingList = new ArrayList<>();
    private final String path = "//Users/user/Documents//wallet.csv";

    public void parseDataFile() {
        try (FileReader fileReader = new FileReader(path); BufferedReader bufferedReader = new BufferedReader(fileReader))
        {
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] arrayString = line.split(";");

                if (arrayString[0].equalsIgnoreCase("received")) {
                    for (int i = 1; i < arrayString.length; i++) {
                        BigDecimal x = new BigDecimal(arrayString[i]).setScale(2, RoundingMode.HALF_UP);
                        receivedBigDecimalList.add(x);
                    }
                }
                else if (arrayString[0].equalsIgnoreCase("spent")){
                    for (int i = 1; i < arrayString.length; i++) {
                        BigDecimal y = new BigDecimal(arrayString[i]).setScale(2, RoundingMode.HALF_UP);
                        spendingBigDecimalList.add(y);
                    }
                }
                else if (arrayString[0].equalsIgnoreCase("dateOfReceiving")) {
                    for (int i = 1; i < arrayString.length; i++) {
                        try {
                            long lll = Long.parseLong(arrayString[i]);
                            datesReceivedList.add(new Date(lll));
                        } catch (NumberFormatException e) {
                            System.err.println("Невозможно преобразовать строку в long: " + e.getMessage());
                        }

                    }
                }
                else if (arrayString[0].equalsIgnoreCase("dateOfSpending")) {

                    for (int i = 1; i < arrayString.length; i++) {
                        try {
                            long lll = Long.parseLong(arrayString[i]);
                            dateSpendingList.add(new Date(lll));
                        } catch (NumberFormatException e) {
                            System.err.println("Невозможно преобразовать строку в long: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            currentValue();
        }
    }
    public BigDecimal currentValue(){
        BigDecimal sumReceived = BigDecimal.ZERO;
        BigDecimal sumSpending = BigDecimal.ZERO;
        for (BigDecimal number : receivedBigDecimalList) {
            sumReceived = sumReceived.add(number);
        }
        for (BigDecimal number : spendingBigDecimalList) {
            sumSpending = sumSpending.add(number);
        }
        this.currentValue = sumReceived.subtract(sumSpending);
        return this.currentValue;
    }
    public void toSendMoney(double quantity) {
        BigDecimal quantityB = new BigDecimal(quantity).setScale(2, RoundingMode.HALF_UP);
        if(quantityB.doubleValue() <= currentValue.doubleValue()){
            spendingBigDecimalList.add(quantityB);
            Date date = new Date();
            dateSpendingList.add(date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String formattedDate = sdf.format(date);
            System.out.println(formattedDate + " отправлено " + quantityB + " монет.");
            currentValue();
            fileUpdate();
            System.out.println("Остаток " + currentValue);
            System.out.println();
        }
        else {
            System.out.println("недостаточно средств!!! \n");
        }
    }
    public void toGetMoney(double quantity){
        BigDecimal quantityB = new BigDecimal(quantity).setScale(2, RoundingMode.HALF_UP);
        if(quantityB.doubleValue() > 0) {
            receivedBigDecimalList.add(quantityB);
            Date date = new Date();
            datesReceivedList.add(date);

            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String formattedDate = sdf.format(date);

            System.out.println(formattedDate + " получено " + quantityB + " монет.");
            currentValue();
            fileUpdate();
            System.out.println("Остаток " + currentValue);
            System.out.println();
        }
        else {
            System.out.println("Должно быть положительное значение !!! \n");
        }
    }
    public void fileUpdate() {
        List<String> textLines = new ArrayList<>();
        textLines.add(stringLineReceivedFromIntegerList());
        textLines.add(stringLineDatesReceivedList());
        textLines.add(stringLineSpendingFromIntegerList());
        textLines.add(stringLineDateSpendingList());



       // String pathTEST = "//Users/user/Documents//walletTEST.csv";
        try (FileWriter fw = new FileWriter(path, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(fw))
        {
            for (String line : textLines) {
                writer.append(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String stringLineReceivedFromIntegerList(){
        StringBuilder sb1 = new StringBuilder();
        sb1.append("received;");
        for (int i = 0; i < receivedBigDecimalList.size()  ; i++) {
            sb1.append(receivedBigDecimalList.get(i).doubleValue());
            if(i != receivedBigDecimalList.size() -1) {
                sb1.append(";");
            }
        }
       return sb1.toString();
    }
    public String stringLineSpendingFromIntegerList(){
        StringBuilder sb2 = new StringBuilder();
        sb2.append("spent;");
        for (int i = 0; i < spendingBigDecimalList.size(); i++) {
            sb2.append(spendingBigDecimalList.get(i).doubleValue());
            if(i != spendingBigDecimalList.size() -1) {
                sb2.append(";");
            }
        }
        return sb2.toString();
    }
    public String stringLineDatesReceivedList(){
        StringBuilder sb3 = new StringBuilder();
        sb3.append("dateOfReceiving;");
        for (int i = 0; i < datesReceivedList.size(); i++) {
            sb3.append(datesReceivedList.get(i).getTime());
            if(i != datesReceivedList.size() -1) {
                sb3.append(";");
            }
        }
        return sb3.toString();
    }
    public String stringLineDateSpendingList(){
        StringBuilder sb4 = new StringBuilder();
        sb4.append("dateOfSpending;");
        for (int i = 0; i < dateSpendingList.size(); i++) {
            sb4.append(dateSpendingList.get(i).getTime());
            if(i != dateSpendingList.size() -1) {
                sb4.append(";");
            }
        }
        return sb4.toString();
    }
}
