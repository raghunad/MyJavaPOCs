import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        LocalDateTime dateTime1 = LocalDateTime.of(2024,12,28,0,0);
        LocalDateTime dateTime2 = LocalDateTime.of(2024,12,29,0,0);
        LocalDateTime dateTime3 = LocalDateTime.of(2024,12,31,0,0);

        DateTimeFormatter formatteryyyy = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter formatterYYYY = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

        System.out.println("Date1 in yyyy: " + dateTime1.format(formatteryyyy));
        System.out.println("Date1 in YYYY: " + dateTime1.format(formatterYYYY));

        System.out.println("Date2 in yyyy: " + dateTime2.format(formatteryyyy));
        System.out.println("Date2 in YYYY: " + dateTime2.format(formatterYYYY));

        System.out.println("Date3 in yyyy: " + dateTime3.format(formatteryyyy));
        System.out.println("Date3 in YYYY: " + dateTime3.format(formatterYYYY));
    }
}
