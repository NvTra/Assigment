package vn.funix.fx22252.java.asm02.models;


import vn.funix.fx22252.java.asm03.models.DigitalBank;
import vn.funix.fx22252.java.asm03.models.Transaction;
import vn.funix.fx22252.java.asm04.dao.TransactionDao;


import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static vn.funix.fx22252.java.asm03.models.Transaction.*;

public class Account implements Serializable {

    //khai bao thuoc tinh
    private final long serialVersionUID = 2L;
    private String customerId;
    private String accountNumber;
    private double balance;
    public String accountType;
    private final List<Transaction> transactions = new ArrayList<>();


    //khoi tao constructor
    public Account() {

    }

    public Account(String customerId, String accountNumber, double balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = null;
    }
    //----------------begin setter and getter ---------------

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Customer getCustomer() {
        return DigitalBank.getCustomerById(customerId);
    }

    //----------------end setter and getter -----------------
    public boolean isPremium() {
        return balance >= 10000000;
    } // tai khoan la premium neu so du toi thieu la 10.000.000

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        return String.format("%11s | %-32s | %32s", accountNumber, getAccountType(), decimalFormat.format(getBalance()));
    }

    //lich su giao dich


    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }

    public void diplayTransaction() {
        if (transactions.size() > 0) {
            for (Transaction transaction : transactions) {
                System.out.println(transaction.toString());
            }
        }
    }

    //Asm04


    public ArrayList<Transaction> getTransactions(ArrayList<Transaction> transactionsList, String accountNumber) {

        for (Transaction transaction : transactions) {
            if (transaction.getAccoundNumber().equals(accountNumber)) {
                transactionsList.add(transaction);
            }
        }
        return transactionsList;
    }

    public void displayTransactionsList() {
        transactions.forEach(t-> System.out.println(t.toString()));

    }

    public void createTransaction(double amount, Date time, boolean status, TransactionType type) throws IOException {
        if (type == TransactionType.DEPOSIT) {
            balance += amount;
        } else if (type == TransactionType.WITHDRAW) {
            balance -= amount;
        } else if (type == TransactionType.TRANFERS) {
            balance -= amount;
        }
        Transaction transaction = new Transaction(getAccountNumber(), amount, time, true, type);
        addTransaction(transaction);
        TransactionDao.save(getTransactions());
    }


}
