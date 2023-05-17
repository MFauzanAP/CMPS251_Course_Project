package main;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Service;
import models.Slot;

public class AdminApp {
    public static void main(String[] args) throws Exception {
        AdminRepository.initializeData();
        Slot test = new Slot(LocalTime.parse("20:30"), LocalDate.parse("2023-05-18"));
        test.reserve(null, null);
        AdminRepository.addServices(new Service("null1", 0, 0));
        AdminRepository.addServices(new Service("test", 0, 0));
        AdminRepository.addServices(new Service("test", 10, 0));
        System.out.println(test.getId());
        System.out.println(AdminRepository.getServiceSlots(LocalDate.parse("2023-05-17"), null));
        System.out.println(AdminRepository.getAvailableSlots(LocalDate.parse("2023-05-18"), new Service("test", 0, 0)));
        AdminRepository.saveData();
    }
}
