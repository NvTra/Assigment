package vn.funix.fx22252.java.asm04.model;

import vn.funix.fx22252.java.asm04.common.Account;

import java.io.IOException;


public interface ITransfer {
    void transfer(Account receiveAccount, double amount) throws IOException;
}
