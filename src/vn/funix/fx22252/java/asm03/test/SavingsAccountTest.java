package vn.funix.fx22252.java.asm03.test;

import vn.funix.fx22252.java.asm03.models.SavingsAccount;

import java.io.IOException;
import java.io.Serializable;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    private SavingsAccount normalAccount;
    private SavingsAccount premiumAccount;

    @org.junit.Before
    public void setUp() {
        normalAccount = new SavingsAccount("123456", 8000000);
        premiumAccount = new SavingsAccount("123456", 10000000);
    }


    @org.junit.Test
    public void withdraw() throws IOException {
        assertTrue(normalAccount.withdraw(50000.00));//rut tien TK Normal
        assertTrue(premiumAccount.withdraw(6000000)); // rut tien TK Premium
        assertFalse(normalAccount.withdraw(5000));// rut tien ko du so tien toi thieu
        assertFalse(normalAccount.withdraw(2532.23));// rut tien sai boi so
        assertFalse(premiumAccount.withdraw(10000000));// rut qua so du
    }

    @org.junit.Test
    public void isAccepted() {
        assertTrue(normalAccount.isAccepted(5000000.00)); // rut tien dung
        assertTrue(premiumAccount.isAccepted(2000000)); // rut tien dung
        assertTrue(premiumAccount.isAccepted(50000));// rut tien toi thieu 50000
        assertTrue(premiumAccount.isAccepted(6000000));//rut tien TK premium
        assertFalse(normalAccount.isAccepted(5000));// rut tien duoi 50000
        assertFalse(normalAccount.isAccepted(521422.25));// rut tien khong phai boi so cua 10000
        assertFalse(normalAccount.isAccepted(6000000));// rut vuot dinh muc TK Normal
        assertFalse(premiumAccount.isAccepted(20000000));// rut tien qua so du
    }

    @org.junit.Test
    public void log() throws IOException {
        premiumAccount.withdraw(400000);
        premiumAccount.log(400000);
        normalAccount.withdraw(2000000);
        normalAccount.log(2000000);
    }
}