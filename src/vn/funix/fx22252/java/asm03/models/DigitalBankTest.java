package vn.funix.fx22252.java.asm03.models;

import org.junit.Before;
import org.junit.Test;

import vn.funix.fx22252.java.asm02.models.Customer;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;
import vn.funix.fx22252.java.asm04.exception.CustomerIdNotValidException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


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
    public void isCustomerExisted (){
        assertTrue(activeBank.isCustomerExisted(CustomerDao.list(),new Customer("hoang","040095012040")));
        assertFalse(activeBank.isCustomerExisted(CustomerDao.list(),new Customer("hoang","040095012043")));

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
    @Test
    public void goicustomer(){
        System.out.println(activeBank.getCustomerbyAccountNumber("123564"));
        System.out.println(String.format("%.1f đ",100000.00));
    }

}