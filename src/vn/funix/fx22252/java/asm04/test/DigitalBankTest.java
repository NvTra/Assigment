package vn.funix.fx22252.java.asm04.test;

import org.junit.Before;
import org.junit.Test;
import vn.funix.fx22252.java.asm03.models.DigitalCustomer;
import vn.funix.fx22252.java.asm03.models.LoanAccount;
import vn.funix.fx22252.java.asm04.common.Account;
import vn.funix.fx22252.java.asm04.common.Customer;
import vn.funix.fx22252.java.asm04.common.DigitalBank;
import vn.funix.fx22252.java.asm04.common.SavingsAccount;
import vn.funix.fx22252.java.asm04.dao.AccountDao;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;

import java.util.Comparator;

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
    public void showCustomers() {
        activeBank.showCustomers();
    }


    @Test
    public void isAccountExisted() {
       assertTrue(activeBank.isAccountExisted(AccountDao.list(),new Account("111111",0)));
        assertFalse(activeBank.isAccountExisted(AccountDao.list(),new Account("111983",0)));
    }

    @Test
    public void isCustomerExisted() {
        assertTrue(activeBank.isCustomerExisted(CustomerDao.list(),new Customer("Hoang","040095012040")));
        assertFalse(activeBank.isCustomerExisted(CustomerDao.list(),new Customer("Hoang","040095044510")));
    }

    @Test
    public void getCustomerById() {
        assertEquals("Nguyen Van Long", activeBank.getCustomerById("040095012040").getName());
        assertNotEquals("Hoang", activeBank.getCustomerById("040095012040").getName());
    }
    @Test
    public void addCustomers(){
        activeBank.addCustomers("store/customers.tx");
    }

}