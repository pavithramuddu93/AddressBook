package com.address.book;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookMain {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Person> personMap = new HashMap();
    private static Map<String, Map<String, Person>> addressBookMap = new HashMap();

    /**
     * This is the main method, Here is the starting point of the program.
     *
     * @param args
     */
    public static void main(String[] args) {

        boolean isExit = false;

        do {
            System.out.println("\n\t\tAddress Book System");
            System.out.println("\n\t\tEnter A to Add Person ");
            System.out.println("\t\tEnter E to Edit Person");
            System.out.println("\t\tEnter D to Delete Person");
            System.out.println("\t\tEnter S to Search Person");
            System.out.println("\t\tEnter C to Count Persons In City");
            System.out.println("\t\tEnter P to Print Address Book");
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
                    System.out.println("\n\t\t" + addressBookMap.toString());
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


    /**
     * Method For editing the existing person details on the basis of first name.
     */
    private static void editContact(String firstName, String cityName) {
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
    }

    /**
     * Method for taking person details in Person-Model format.
     *
     * @return : Person Object
     */
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

    /**
     * Method for deleting the person from existing address book
     */
    private static void deletePerson(String firstName, String cityName) {
        Person newPerson = addressBookMap.get(cityName).get(firstName);
        System.out.println(newPerson.toString());
        if (addressBookMap.get(cityName).get(firstName) != null) {
            addressBookMap.get(cityName).remove(firstName);
            System.out.println("Deleted Successfully");
        } else {
            System.out.println("Record not exist");
        }

    }

    /**
     * Method for taking person details and store them into storage with
     * their city name reference.
     * In this program used hashmap. Multiple person can add.
     */
    private static void addBook() {
        Map<String, Person> newPersonMap = new HashMap();
        Person newPerson;
        newPerson = contactFields();

        if (addressBookMap.get(newPerson.getCity()) != null)
            newPersonMap = addressBookMap.get(newPerson.getCity());

        newPersonMap.put(newPerson.getFirstName(), newPerson);
        addressBookMap.put(newPerson.getCity(), newPersonMap);

        System.out.println("\n\t\t" + addressBookMap.toString());
    }

    /**
     * Method for search person in a city across address book by using stream.
     * @param city : name of city to be search.
     * @return : Hashmap containing personList of city.
     */
    private static Map<String, Map<String, Person>> searchPerson(String city) {
        Map<String, Map<String, Person>> personsByCity = new HashMap();
        addressBookMap.entrySet().stream()
                .filter(e ->e.getKey().equalsIgnoreCase(city))
                .forEach(entry -> personsByCity.put(entry.getKey(), entry.getValue()));
        return personsByCity;
    }

    /**
     * Method for counting Number of personList in particular city.
     * @param city : name of city to be search
     * @return : count
     */
    private static int personsCountByCity(String city){
        Map<String, Map<String, Person>> personCount = searchPerson(city);
        return personCount.get(city).size();
    }
}