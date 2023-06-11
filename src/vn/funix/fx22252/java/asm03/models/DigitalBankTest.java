package vn.funix.fx22252.java.asm03.models;

import org.junit.Before;
import org.junit.Test;

import vn.funix.fx22252.java.asm04.exception.CustomerIdNotValidException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class DigitalBankTest {
    private DigitalBank activeBank;
    Scanner scanner = new Scanner(System.in);

    @Before
    public void setUp() {
        activeBank = new DigitalBank();
    }

    @Test
    public void addCustomers() throws CustomerIdNotValidException {
        Path path = Paths.get("store", "customers.txt");
        activeBank.addCustomers(String.valueOf(path));

        activeBank.showCustomers();


    }

    @Test
    public void addAccount() throws IOException {
        activeBank.addSavingAccount(new Scanner(System.in), "040095012040");
    }



    @Test
    public void showCustomers() {
        Path path = Paths.get("store", "customers.txt");
        activeBank.addCustomers(String.valueOf(path));
        activeBank.getCustomerById("040095012040").addAccount(new SavingsAccount("040095012040","123456",100000));
        activeBank.showCustomers();
    }
    @Test
    public void testlinhtinh() throws IOException {
        Path path = Paths.get("store", "customers.txt");
        activeBank.addCustomers(String.valueOf(path));
        activeBank.addAccount("040095012040", new SavingsAccount("123456", 10000000));
        activeBank.showCustomers();
        activeBank.withdraw("040095012040","123456",500000);
    }


}