import java.time.LocalDate;
import java.time.LocalTime;

import models.Service;
import models.Slot;

public class App {
    public static void main(String[] args) throws Exception {
        Slot test = new Slot(LocalTime.parse("20:30"), LocalDate.parse("2023-05-18"));
        test.reserve(null, null);
        AdminApp.services.add(new Service("null", 0, 0));
        AdminApp.services.add(new Service("test", 0, 0));
        System.out.println(test.getId());
        System.out.println(AdminApp.getServiceSlots(LocalDate.parse("2023-05-17"), null));
        System.out.println(AdminApp.getAvailableSlots(LocalDate.parse("2023-05-17"), new Service("test", 0, 0)));
    }
}
