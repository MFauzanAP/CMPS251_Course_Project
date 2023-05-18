package main;

import java.time.LocalDate;
import java.time.LocalTime;

import models.Patient;
import models.Service;
import models.Slot;
import models.Patient.ResidencyType;
import repos.AdminRepository;
import repos.PatientRepository;
import repos.ServiceRepository;
import repos.SlotRepository;

public class AdminApp {
    public static void main(String[] args) throws Exception {
        // AdminRepository.initializeData();
        Slot test = new Slot(LocalTime.parse("20:30"), LocalDate.parse("2023-05-18"));
        test.reserve(null, null);
        PatientRepository.addPatients(new Patient("12345678901", "Muhammad Putra", ResidencyType.RESIDENT));
        ServiceRepository.addServices(new Service("null1", 0, 0));
        ServiceRepository.addServices(new Service("test", 0, 0));
        ServiceRepository.addServices(new Service("test", 10, 0));
        System.out.println(test.getId());
        System.out.println(SlotRepository.getServiceSlots(LocalDate.parse("2023-05-17"), null));
        System.out.println(SlotRepository.getAvailableSlots(LocalDate.parse("2023-05-18"), new Service("test", 0, 0)));
        AdminRepository.saveData();
    }
}
