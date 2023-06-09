package vn.funix.fx22252.java.asm03.test;

import org.junit.Before;
import org.junit.Test;
import vn.funix.fx22252.java.asm02.models.Account;
import vn.funix.fx22252.java.asm02.models.Customer;
import vn.funix.fx22252.java.asm03.models.*;

import java.util.Date;

import static org.junit.Assert.*;

public class DigitalBankTest {
    private DigitalBank activeBank;
    private static final String CUSTOMER_ID = "040095012040";
    private static final String CUSTOMER_NAME = "Nguyen Tra";

    @Before
    public void setUp() {
        activeBank = new DigitalBank();
        activeBank.addCustomer(new DigitalCustomer(CUSTOMER_NAME, CUSTOMER_ID));
        LoanAccount loanAccount = new LoanAccount("111111",10000000);
        SavingsAccount savingsAccount = new SavingsAccount("222222", 5000000);
        activeBank.addAccount(CUSTOMER_ID, loanAccount);
        activeBank.addAccount(CUSTOMER_ID, savingsAccount);
    }

    @Test
    public void getCustomerById() {
        assertEquals("Nguyen Tra", activeBank.getCustomerById("040095012040").getName());
        assertNotEquals("Hoang", activeBank.getCustomerById("040095012040").getName());

    }

    @Test
    public void addAccount() {
        activeBank.addAccount(CUSTOMER_ID, new LoanAccount("142352",1000000));
        activeBank.addAccount(CUSTOMER_ID, new SavingsAccount("123569", 1000000));
        activeBank.getCustomerById(CUSTOMER_ID).displayInformation();
    }

    @Test
    public void addCustomer() {
        Customer customer = new Customer("Hoang", "040095012060");
        activeBank.addCustomer(customer);
        activeBank.getCustomerById("040095012060").displayInformation();
    }

    @Test
    public void validateAccount() {
        assertTrue(activeBank.validateAccount("123456"));
        assertFalse(activeBank.validateAccount("111111"));
        assertFalse(activeBank.validateAccount("1111ee"));
    }

    @Test
    public void withdraw() {
        assertFalse(activeBank.withdraw("040095012060", "123568", 500000));
        assertTrue(activeBank.withdraw(CUSTOMER_ID, "222222", 200000));
        assertTrue(activeBank.withdraw(CUSTOMER_ID, "111111", 200000));
        activeBank.getCustomerById(CUSTOMER_ID).displayInformation();
    }

    @Test
    public void display() {
        activeBank.getCustomerById(CUSTOMER_ID).getAccountsByAccountNumber("222222").addTransaction(new Transaction("222222", 5000000, new Date(), true));
        assertTrue(activeBank.withdraw(CUSTOMER_ID, "111111", 2000000));
        activeBank.withdraw(CUSTOMER_ID, "222222", 200000);
        activeBank.getCustomerById(CUSTOMER_ID).displayAllTransaction();

    }

    @Test
    public void isAccountPremium() {
        Account account = new Account("123456", 500000);
        assertFalse(account.isPremium());
        assertTrue(new Account("123456", 10000000).isPremium());
    }

    @Test
    public void isCustomerExisted() {
      assertTrue( activeBank.isCustomerExisted("040095012040"));
      assertFalse(activeBank.isCustomerExisted("040095012050"));
    }
    @Test
    public void isAccountExisted(){
        assertTrue(activeBank.getCustomerById("040095012040").isAccountExisted("111111"));
        assertFalse(activeBank.getCustomerById("040095012040").isAccountExisted("111122"));
    }
    @Test
    public void isCustomerPremium(){
       assertTrue(activeBank.getCustomerById("040095012040").isPremium());
       activeBank.addCustomer(new Customer("Hoang","040095012060"));
       assertFalse(activeBank.getCustomerById("040095012060").isPremium());
    }
    @Test
    public void getAccountsByAccountNumber(){
        assertNull(activeBank.getCustomerById(CUSTOMER_ID).getAccountsByAccountNumber("222232"));
        assertEquals("111111",activeBank.getCustomerById(CUSTOMER_ID).getAccountsByAccountNumber("111111").getAccountNumber());
        assertNotEquals("111122",activeBank.getCustomerById(CUSTOMER_ID).getAccountsByAccountNumber("111111").getAccountNumber());
    }
    @Test
    public void validateCustomerId(){
        assertTrue(activeBank.validateCustomerId("040095012040"));
        assertFalse(activeBank.validateCustomerId("0400950120"));
    }
    @Test
    public void getTotalAccountBalance(){
        assertEquals(105000000,activeBank.getCustomerById("040095012040").getTotalAccountBalance(),0);
        assertNotEquals(10500,activeBank.getCustomerById("040095012040").getTotalAccountBalance(),0);
    }
}