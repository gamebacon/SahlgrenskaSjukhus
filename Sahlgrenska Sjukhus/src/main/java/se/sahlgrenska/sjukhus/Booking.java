package se.sahlgrenska.sjukhus;

import se.sahlgrenska.sjukhus.person.employee.Employee;
import se.sahlgrenska.sjukhus.person.patient.Patient;
import se.sahlgrenska.sjukhus.item.Item;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Booking {
    private LocalDateTime time;
    private List<Patient> patients;
    private List<Employee> employees;
    private Ward ward;
    private Room room;


    public Booking() {

    }


    public Room getRoom() {
        return room;

    }






}
