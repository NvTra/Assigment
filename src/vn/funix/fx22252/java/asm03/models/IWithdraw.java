package vn.funix.fx22252.java.asm03.models;

import java.io.IOException;

public interface IWithdraw {
    boolean withdraw(double amount) throws IOException;

    boolean isAccepted(double amount);
}
