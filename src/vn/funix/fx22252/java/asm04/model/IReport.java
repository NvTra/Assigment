package vn.funix.fx22252.java.asm04.model;

import vn.funix.fx22252.java.asm03.models.Transaction;

public interface IReport {
    void log(double amount, Transaction type, String receiveAccount);
}
