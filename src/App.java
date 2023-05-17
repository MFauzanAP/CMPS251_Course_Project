import java.time.LocalDate;
import java.time.LocalTime;

import models.Slot;

public class App {
    public static void main(String[] args) throws Exception {
        Slot test = new Slot(LocalTime.parse("20:30"), LocalDate.parse("2023-05-18"));
        test.reserve(null, null);
        System.out.println(test.getId());
        System.out.println(AdminApp.getAvailableSlots(LocalDate.parse("2023-05-18")));
    }
}
