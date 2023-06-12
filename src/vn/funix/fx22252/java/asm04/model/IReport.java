package vn.funix.fx22252.java.asm04.model;

import vn.funix.fx22252.java.asm04.common.Account;
import vn.funix.fx22252.java.asm04.common.Transaction;


public interface IReport {
    void log(double amount, Transaction.TransactionType type, Account receiveAccount);
}
