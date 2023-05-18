package main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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
        Slot test = new Slot(LocalTime.parse("20:30"), LocalDate.parse("2023-05-19"));
        test.reserve(null, null);
        PatientRepository.addPatient(new Patient("12345678901", "Muhammad Putra", ResidencyType.RESIDENT));
        PatientRepository.updatePatientId("12345678901", "68475684579");
        ServiceRepository.addService(new Service("null1", 0, 0));
        // ServiceRepository.addService(new Service("test", 0, 0));
        // ServiceRepository.addService(new Service("test", 10, 0));
        System.out.println(test.getId());
        System.out.println(PatientRepository.getPatientsAsList());
        System.out.println(SlotRepository.getAvailableSlotsByDate(LocalDate.parse("2023-05-19")));
        // System.out.println(SlotRepository.getAvailableSlotsByDateService(LocalDate.parse("2023-05-19"), ServiceRepository.getServicesByTitle("test").get(0).getId()));
        ArrayList<Slot> availableSlots = SlotRepository.getAvailableSlotsByDate(LocalDate.parse("2023-05-19"));
        Patient patient = PatientRepository.getPatientById("68475684579");
        SlotRepository.bookSlot(availableSlots.get(0), patient);
        PatientRepository.updatePatientName(patient, "New name");
        SlotRepository.updateSlotTime(availableSlots.get(0), LocalTime.parse("20:30"));
        // availableSlots.get(0).setTime(LocalTime.parse("20:30"));
        System.out.println(SlotRepository.getSlotsAsList());
        System.out.println(SlotRepository.getSlotsByTime(LocalTime.parse("07:00")));
        System.out.println(SlotRepository.getSlotsByTime(LocalTime.parse("20:30")));
        AdminRepository.saveData();
    }
}
