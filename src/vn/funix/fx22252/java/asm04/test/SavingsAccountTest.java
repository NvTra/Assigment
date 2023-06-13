package vn.funix.fx22252.java.asm04.test;

import org.junit.Test;
import vn.funix.fx22252.java.asm04.common.Account;
import vn.funix.fx22252.java.asm04.common.Customer;
import vn.funix.fx22252.java.asm04.common.SavingsAccount;

import java.io.IOException;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    private SavingsAccount normalAccount;
    private SavingsAccount premiumAccount;

    @org.junit.Before
    public void setUp() {

        normalAccount = new SavingsAccount("123456", 8000000);
        premiumAccount = new SavingsAccount("111111", 10000000);
    }


    @Test
    public void isAccepted() {
        assertTrue(normalAccount.isAccepted(5000000.00)); // rut tien dung
        assertTrue(premiumAccount.isAccepted(2000000)); // rut tien dung
        assertTrue(premiumAccount.isAccepted(50000));// rut tien toi thieu 50000
        assertTrue(premiumAccount.isAccepted(6000000));//rut tien TK premium
        assertFalse(normalAccount.isAccepted(5000));// rut tien duoi 50000
        assertFalse(normalAccount.isAccepted(521422.25));// rut tien khong phai boi so cua 10000
        assertFalse(normalAccount.isAccepted(6000000));// rut vuot dinh muc TK Normal
        assertFalse(premiumAccount.isAccepted(20000000));//
    }

    @Test
    public void log() throws IOException {
        premiumAccount.log(400000);
        normalAccount.log(2000000);
    }

    @Test
    public void transfer() throws IOException {
        premiumAccount.transfer(normalAccount, 100000);
        normalAccount.transfer(premiumAccount,500000);
    }

}