package vn.funix.fx22252.java.asm03.test;


import vn.funix.fx22252.java.asm03.models.LoanAccount;

import static org.junit.Assert.*;

public class LoanAccountTest {
    private LoanAccount normalAccount;
    private LoanAccount premiumAccount;

    @org.junit.Before
    public void setUp() {
        normalAccount = new LoanAccount("123456",6000000);

        premiumAccount = new LoanAccount("123456",15000000);
    }

    @org.junit.Test
    public void getFee() {

        assertEquals(150000, normalAccount.getFee(3000000.00), 0);
        assertEquals(30000, premiumAccount.getFee(3000000.00), 0);
    }

    @org.junit.Test
    public void withdraw() {
        assertTrue(normalAccount.withdraw(50000.00));
        assertTrue(normalAccount.withdraw(4000000));

    }

    @org.junit.Test
    public void isAccepted() {
        assertTrue(normalAccount.isAccepted(200000));
        assertTrue(normalAccount.isAccepted(4000000));
        assertFalse(normalAccount.isAccepted(150000000)); //rut qua han muc
        assertFalse(premiumAccount.isAccepted(-500000));// rut so <0
    }

    @org.junit.Test
    public void log() {

        premiumAccount.withdraw(6000000);

    }
}