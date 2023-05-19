package cmps251.main;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import cmps251.models.Patient;
import cmps251.models.Service;
import cmps251.models.Slot;
import cmps251.models.Patient.ResidencyType;
import cmps251.repos.AdminRepository;
import cmps251.repos.PatientRepository;
import cmps251.repos.ServiceRepository;
import cmps251.repos.SlotRepository;

public class AdminApp {
    public static void main(String[] args) throws Exception {
        // AdminRepository.initializeData();

        Patient patient = new Patient("12345678901", "Muhammad Putra", ResidencyType.RESIDENT);
        PatientRepository.addPatient(patient);
        PatientRepository.updatePatientId("12345678901", "68475684579");
        System.out.println("Patients List: " + PatientRepository.getPatientsAsList());

        ServiceRepository.addService(new Service("Procedure", 15, 50));
        ServiceRepository.addService(new Service("Generic", 20, 100));
        ServiceRepository.addService(new Service("Specialized", 10, 150));
        ServiceRepository.addService(new Service("Operation", 5, 1000));
        System.out.println("Services List: " + ServiceRepository.getServicesAsList());
        
        ArrayList<Slot> availableSlots = SlotRepository.getAvailableSlotsByDateService(LocalDate.parse("2023-05-19"), ServiceRepository.getServicesByTitle("Operation").get(0).getId());
        System.out.println("Available Slots for Operation Service: " + availableSlots);
        SlotRepository.bookSlot(availableSlots.get(0), patient);
        System.out.println("Booked slot for " + availableSlots.get(0).getTime());

        PatientRepository.updatePatientName(patient, "New name");
        LocalTime oldTime = availableSlots.get(0).getTime();
        SlotRepository.updateSlotTime(availableSlots.get(0), LocalTime.parse("20:30"));
        System.out.println("Updated slots after changing time and patient name: " + SlotRepository.getSlotsAsList());
        System.out.println("Slots at old time: " + SlotRepository.getSlotsByTime(oldTime));
        System.out.println("Slots at new time: " + SlotRepository.getSlotsByTime(LocalTime.parse("20:30")));

        AdminRepository.saveData();
    }
}
