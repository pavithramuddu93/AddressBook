package com.address.book;

public class AddressBookMain {
    public static void main(String[] args) {
        System.out.println("Welcome to the Address Book Program");
        AddressBook contactinfo = new AddressBook();
        contactinfo.setUpInfo();
        contactinfo.displayListItems();

}
}

