package vn.funix.fx22252.java.asm04.model;

import vn.funix.fx22252.java.asm02.models.Account;


public interface ITransfer {
    void transfer(Account receiveAccount, double amount);
}
