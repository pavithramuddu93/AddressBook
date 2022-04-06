package com.address.book;
import java.util.Map;

public class AddressBookModel {
    private String name;
    private Map<String,Person> personMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Person> getPersonMap() {
        return personMap;
    }

    public void setPersonMap(Map<String, Person> personMap) {
        this.personMap = personMap;
    }


    public AddressBookModel(String name, Map<String, Person> personMap) {
        this.name = name;
        this.personMap = personMap;
    }

    @Override
    public String toString() {
        return "AddressBookModel{" +
                "name='" + name + '\'' +
                ", personMap=" + personMap +
                '}';
    }
}