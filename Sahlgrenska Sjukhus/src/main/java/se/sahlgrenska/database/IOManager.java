package se.sahlgrenska.database;

import se.sahlgrenska.gui.util.UtilGUI;
import se.sahlgrenska.sjukhus.Address;
import se.sahlgrenska.sjukhus.Archive;
import se.sahlgrenska.sjukhus.Booking;
import se.sahlgrenska.sjukhus.Hospital;
import se.sahlgrenska.sjukhus.item.Item;
import se.sahlgrenska.sjukhus.person.Gender;
import se.sahlgrenska.sjukhus.person.Person;
import se.sahlgrenska.sjukhus.person.employee.*;
import se.sahlgrenska.sjukhus.person.patient.BloodType;
import se.sahlgrenska.sjukhus.person.patient.Disease;
import se.sahlgrenska.sjukhus.person.patient.Patient;

import javax.management.Notification;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.*;

public class IOManager {


    private final Database database = new Database("gamebacon.net", "guest4life", "Lp3jRWB4FQrjC4z", "sahlgrenska");

    public Set<Employee> getOnline() {
        if(database.isConnected()) {

            ResultSet resultSet = callProcedure("getOnline");
            return getEmployees(resultSet);
        }

        return null;
    }


    public void query(String query) {
        if(database.isConnected()) {
            try {
                Statement statement = database.getConnection().createStatement();
                boolean resultSet = statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //test3

    public int getEmployeeID(String personNumber) {
        if(database.isConnected()) {
            try {
                Statement statement = database.getConnection().createStatement();
                ResultSet resultSet = callProcedure("getEmployeeID", personNumber);

                while(resultSet.next())
                    return resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return -1;
    }

    public Set<Employee> getAllEmployees(LoginDetails loginDetails) {

        if(database.isConnected()) {
            ResultSet resultSet = callProcedure("getAllEmployees", loginDetails.getUsername(), loginDetails.getPassword());
            return getEmployees(resultSet);
        }

        return null;
    }

    private Set<Employee> getEmployees(ResultSet resultSet) {
        Set<Employee> employees = new HashSet<>();

        try {
            while (resultSet.next()) {
                String id = String.valueOf(resultSet.getInt(1));
                float salary = resultSet.getInt(2);
                float workingHours = resultSet.getFloat(3);
                Accessibility accessibility = Accessibility.valueOf(resultSet.getString(4));

                String personNum = resultSet.getString(5);
                String fistName = resultSet.getString(6);
                String lastName = resultSet.getString(7);
                String phone = resultSet.getString(8);
                Gender gender = Gender.valueOf(resultSet.getString(9));


                String country = resultSet.getString(10);
                String city = resultSet.getString(11);
                String street = resultSet.getString(12);
                String zip = resultSet.getString(13);

                Address address = new Address(country, city, street, zip);

                Person person = new Person(fistName, lastName, personNum, gender, phone, address);

                String username = resultSet.getString(14);
                String password = resultSet.getString(15);

                LoginDetails loginDetails = new LoginDetails(username, password);

                Employee employee = new Employee(person, id, salary, workingHours, accessibility, loginDetails);
                employees.add(employee);

            }
        } catch (Exception e) {

            }
        return employees;
    }



    public Employee getEmployee(LoginDetails loginDetails) {
        Employee employee = null;

        if(database.isConnected()) {
            try {
                ResultSet resultSet = callProcedure("getEmployee", loginDetails.getUsername(), loginDetails.getPassword());

                while (resultSet.next()) {

                    String id = String.valueOf(resultSet.getInt(1));
                    float salary = resultSet.getInt(2);
                    float workingHours = resultSet.getFloat(3);
                    Accessibility accessibility = Accessibility.valueOf(resultSet.getString(4));

                    //todo get person
                    String personNumm = resultSet.getString(5);
                    String firstName = resultSet.getString(6);
                    String lastName = resultSet.getString(7);
                    String phoneNum= resultSet.getString(8);
                    Gender gender = Gender.valueOf(resultSet.getString(9));

                    //int addressID = resultSet.getInt(10);
                    String sweden = resultSet.getString(10);
                    String city = resultSet.getString(11);
                    String street = resultSet.getString(12);
                    String zip = resultSet.getString(13);

                    Address address = new Address(sweden, city, street, zip);

                    Person person = new Person(firstName, lastName, personNumm, gender, phoneNum, address);

                    employee = new Employee(person, id, salary, workingHours, accessibility, loginDetails);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employee;
    }

    private Person getPersonPre(ResultSet resultSet) {
        Person person = null;
        try {
        String personNumm = resultSet.getString(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String phoneNum= resultSet.getString(4);
        Gender gender = Gender.valueOf(resultSet.getString(5));

        String country = resultSet.getString(6);
        String city = resultSet.getString(7);
        String street = resultSet.getString(8);
        String zip = resultSet.getString(9);

        Address address = new Address(country, city, street, zip);
        person = new Person(firstName, lastName, personNumm, gender, phoneNum, address);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return person;
    }


    public Set<Person> getAllPersons() {
        Set<Person> persons = new HashSet<>();

        if(database.isConnected()) {
            try {
                Statement statement = database.getConnection().createStatement();
                String query = String.format("SELECT * FROM person p INNER JOIN address a ON p.address_id = a.id");
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()) {

                    String personNum = resultSet.getString(1);
                    String fistName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    String phone = resultSet.getString(4);
                    Gender gender = Gender.valueOf(resultSet.getString(5));

                    int addressID = resultSet.getInt(6);
                    int addressID2 = resultSet.getInt(7);

                    String country = resultSet.getString(8);
                    String city = resultSet.getString(9);
                    String street = resultSet.getString(10);
                    String zip = resultSet.getString(11);

                    Address address = new Address(country, city, street, zip);


                    Person person = new Person(fistName, lastName, personNum, gender, phone, address);
                    persons.add(person);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return persons;
    }

    public Person getPerson(String personNumber) {
        Person person = null;

        if(database.isConnected()) {
            try {
                Statement statement = database.getConnection().createStatement();
                String query = String.format("SELECT * FROM person WHERE person.person_number = \"%s\"", personNumber);
                ResultSet resultSet = statement.executeQuery(query);

                while(resultSet.next()) {

                    String personNum = resultSet.getString(1);
                    String fistName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    String phone = resultSet.getString(4);
                    Gender gender = Gender.valueOf(resultSet.getString(5));


                    /*
                    String country = resultSet.getString(10);
                    String city = resultSet.getString(11);
                    String street = resultSet.getString(12);
                    String zip = resultSet.getString(13);

                    Address address = new Address(country, city, street, zip);
                     */

                    person = new Person(fistName, lastName, personNum, gender, phone, new Address());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    /*
        metod för att köra mysql procedures
        oviktigt att veta hur den fungerar
     */
    private ResultSet callProcedure(String name, Object... args) {

        try {

            String query = String.format("{ CALL %s(", name);


            if(args.length == 0)
                query += ")";
            else
                for(int i = 0; i < args.length; i++)
                    query += "?,";


            query = query.substring(0, query.length() - 1) + ") } ";

            CallableStatement callableStatement = database.getConnection().prepareCall(query);

            for(int i = 1; i <= args.length; i++)
                callableStatement.setObject(i, args[i - 1]);

            return callableStatement.executeQuery();

        } catch (SQLNonTransientConnectionException e) {
            //tillfällig lösning för connection timeout problem
            UtilGUI.error("Något gick fel. (Starta om programmet)");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void remember(LoginDetails loginDetails) {
        try {
            File file = new File(getClass().getResource("/save.txt").toURI());
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            if(loginDetails != null)
                writer.write(loginDetails.getUsername() + " " + loginDetails.getPassword());
            else
                writer.write("");

            writer.close();

        } catch (IOException | URISyntaxException e) {
            UtilGUI.error("Fel med save.txt, kolla den är kvar eller lägg till den.");
            e.printStackTrace();
        }
    }


    public LoginDetails getRemember() {
        LoginDetails loginDetails = null;

        try {
            File file = new File(getClass().getResource("/save.txt").toURI());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();

            if(line != null) {
                String[] stuff = line.split(" ");

                loginDetails = new LoginDetails(stuff[0], stuff[1]);
            }

        } catch (URISyntaxException | IOException e) {
            UtilGUI.error("Fel med save.txt, kolla den är kvar eller lägg till den.");
            e.printStackTrace();
        }

        return loginDetails;
    }

    public void closeDB() {
        database.close();
    }

    public void deleteUser(String id) {
        query(String.format("DELETE FROM employee WHERE id = %s", id));
        query(String.format("DELETE FROM login_details WHERE employee_id = %s", id));
    }

    public Set<Patient> getPatients() {
        Set<Patient> patients = new HashSet<>();

        if (database.isConnected()) {
            ResultSet resultSet = callProcedure("getPatients");

            try {
                while(resultSet.next()) {
                    Person person = getPersonPre(resultSet);
                    int patient_id = resultSet.getInt("id");
                    String conditionDescription = resultSet.getString("condition_description");
                    boolean criticalCondition = resultSet.getBoolean("critical_condition");
                    BloodType bloodType = BloodType.valueOf(resultSet.getString("blood_type"));

                    Patient patient = new Patient(person, patient_id, new ArrayList<Disease>(), new ArrayList<Notification>(), conditionDescription, criticalCondition, bloodType);
                    patients.add(patient);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return patients;
    }


    public Hospital loadHospitalData() {

        Set<Person> persons = getAllPersons();
        Set<Patient> patients = getPatients();

        Map<Patient, List<Booking>> bookings = new HashMap<>();

        Archive archive = new Archive();


        Hospital hospital = new Hospital("Sahlgrenska sjukhuset", 200, new HashMap<Item, Integer>(), persons, new Archive(), 500000, new Address("Göteborg", "Blå stråket 5", "413 45", "Åmål"));

        return hospital;
    }
}
