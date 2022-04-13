package com.address.book;
import com.address.book.Person;
import com.address.book.ValidationException;
import com.address.book.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public class AddressBookMain {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Person> personMap = new HashMap();
    private static Map<String, Map<String, Person>> addressBookMap = new HashMap();

    public static void main(String[] args) throws ValidationException {

        boolean isExit = false;

        do {
            System.out.println("\n\t\tAddress Book System");
            System.out.println("\n\t\tEnter A to Add Person ");
            System.out.println("\t\tEnter E to Edit Person");
            System.out.println("\t\tEnter D to Delete Person");
            System.out.println("\t\tEnter S to Search Person");
            System.out.println("\t\tEnter C to Count Persons In City");
            System.out.println("\t\tEnter P to Print Address Book With and Without Sorting");
            System.out.println("\t\tEnter Q to Quit ");
            System.out.print("\t\tPlease Select One Option : ");
            char userInput = scanner.nextLine().toUpperCase().charAt(0);
            switch (userInput) {
                case 'A':
                    //add
                    addBook();
                    break;
                case 'E':
                    //edit
                    System.out.print("\nEnter the first name of the person to edit : ");
                    String firstName = scanner.nextLine();
                    System.out.print("\nEnter the city name of the person to edit : ");
                    String cityName = scanner.nextLine();
                    editContact(firstName,cityName);
                    break;
                case 'D':
                    //delete
                    System.out.print("\nEnter the first name of the person to delete : ");
                    String personName = scanner.nextLine();
                    System.out.print("\nEnter the city name of the person to delete : ");
                    String city = scanner.nextLine();
                    deletePerson(personName,city);
                    break;
                case 'S':
                    //Search
                    System.out.print("\nEnter the city name of the person to search : ");
                    String pCity = scanner.nextLine();
                    System.out.println("\nPersons in city " + pCity + " is : " + searchPerson(pCity));
                    break;
                case 'C':
                    System.out.print("\nEnter the city name to count persons : ");
                    String countCity = scanner.nextLine();
                    System.out.println("\nNumber of Persons in city " + countCity + " is " + personsCountByCity(countCity));
                    break;
                case 'P':
                    //print
                    writeIntoFile();
                    System.out.print("\nEnter the city name to sort : ");
                    String sortCity = scanner.nextLine();
                    System.out.println("\n\t\t Without sorting : " + addressBookMap.toString());
                    System.out.println("\n\t\t Sorted By Person Name : " + sortByPersonName(sortCity));
                    System.out.println("\n\t\t sorted By City : " + sortByCity());

                    break;
                case 'Q':
                    //quit
                    isExit = true;
                    break;
                default:
                    System.out.println("Please select correct option");
            }
        } while (!isExit);

    }


    private static void editContact(String firstName, String cityName) throws ValidationException {
        try {
            personMap = addressBookMap.get(cityName);
            System.out.println(personMap.toString());
            if (addressBookMap.get(cityName).get(firstName) != null) {
                Person editedPerson = contactFields();
                personMap.put(editedPerson.getFirstName(), editedPerson);
                addressBookMap.put(editedPerson.getCity(), personMap);
            } else {
                System.out.println("Record Not Found");
            }
            System.out.println("\n\t\t" + addressBookMap.toString());
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private static Person contactFields() {
        Person person = new Person();
        System.out.print("Enter First Name : ");
        person.setFirstName(scanner.nextLine());

        System.out.print("Enter Last Name : ");
        person.setLastName(scanner.nextLine());

        System.out.print("Enter Address : ");
        person.setAddress(scanner.nextLine());

        System.out.print("Enter State : ");
        person.setState(scanner.nextLine());

        System.out.print("Enter City : ");
        person.setCity(scanner.nextLine());

        System.out.print("Enter ZipCode : ");
        person.setZip(scanner.nextLine());

        System.out.print("Enter Phone Number : ");
        person.setPhone(scanner.nextLine());
        return person;
    }

    private static void deletePerson(String firstName, String cityName) throws ValidationException {

        try {
            Person newPerson = addressBookMap.get(cityName).get(firstName);
            System.out.println(newPerson.toString());
            if (addressBookMap.get(cityName).get(firstName) != null) {
                addressBookMap.get(cityName).remove(firstName);
                System.out.println("Deleted Successfully");
            } else {
                System.out.println("Record not exist");
            }
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }

    }

    private static void addBook() throws ValidationException {
        try {
            Map<String, Person> newPersonMap = new HashMap();
            Person newPerson;
            newPerson = contactFields();

            if (addressBookMap.get(newPerson.getCity()) != null)
                newPersonMap = addressBookMap.get(newPerson.getCity());

            newPersonMap.put(newPerson.getFirstName(), newPerson);
            addressBookMap.put(newPerson.getCity(), newPersonMap);

            System.out.println("\n\t\t" + addressBookMap.toString());
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private static Map<String, Map<String, Person>> searchPerson(String city) throws ValidationException {
        Map<String, Map<String, Person>> personsByCity = new HashMap();
        try {
            addressBookMap.entrySet().stream()
                    .filter(e -> e.getKey().equalsIgnoreCase(city))
                    .forEach(entry -> personsByCity.put(entry.getKey(), entry.getValue()));
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
        return personsByCity;
    }

    private static int personsCountByCity(String city) throws ValidationException {
        Map<String, Map<String, Person>> personCount = searchPerson(city);
        return personCount.get(city).size();
    }

    private static Map<String, Person> sortByPersonName(String city) throws ValidationException {
        try {
            Map<String, Person> temp = addressBookMap.get(city);
            Map<String, Person> sorted = temp.entrySet().stream()
                    .sorted(comparingByKey())
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
            return sorted;
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private static Map<String, Map<String, Person>> sortByCity() throws ValidationException {
        try {
            Map<String, Map<String, Person>> sorted = addressBookMap.entrySet().stream()
                    .sorted(comparingByKey())
                    .collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
            return sorted;
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * Method for writing the address book data into a json file using jackson
     * library.
     * @throws ValidationException
     */
    private static void writeIntoFile() throws ValidationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("outputData.json"),addressBookMap);
            System.out.println("successfully");
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }
}