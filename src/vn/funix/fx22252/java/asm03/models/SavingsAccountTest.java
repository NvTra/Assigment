package vn.funix.fx22252.java.asm03.models;

import org.junit.Before;
import org.junit.Test;
import vn.funix.fx22252.java.asm04.common.SavingsAccount;

import java.io.IOException;

public class SavingsAccountTest {
    private SavingsAccount sa;
    private SavingsAccount be;

    @Before
    public void setUP() {
        sa = new SavingsAccount("123456", 1000000);
        be = new SavingsAccount("222222", 10000000);
    }

    @Test
    public void withdraw() {
    }

    @Test
    public void isAccepted() {
    }

    @Test
    public void log() {
    }

    @Test
    public void transfer() throws IOException {
        sa.transfer(be, 1000000);
    }

    @Test
    public void testLog() {
    }
}