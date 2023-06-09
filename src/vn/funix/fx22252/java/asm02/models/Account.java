package vn.funix.fx22252.java.asm02.models;

import vn.funix.fx22252.java.asm03.models.DigitalBank;
import vn.funix.fx22252.java.asm03.models.Transaction;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static vn.funix.fx22252.java.asm03.models.Transaction.*;

public class Account implements Serializable {

    //khai bao thuoc tinh
    private long serialVersionUID = 2L;
    private String customerId;
    private String accountNumber;
    private double balance;
    public String accountType;
    private final List<Transaction> transactions = new ArrayList<>();
    private final ArrayList<Transaction> accountTransactions = new ArrayList<>();

    //khoi tao constructor
    public Account() {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = null;
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

    //----------------end setter and getter -----------------
    public boolean isPremium() {
        return balance >= 10000000;
    } // tai khoan la premium neu so du toi thieu la 10.000.000

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        return String.format("%-10s|%20s | %32s", accountNumber, getAccountType(), decimalFormat.format(getBalance()));
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
//    public Customer getCustomer() {
//        // Khi thông tin khách hàng được lưu vào file,
//        // đối tượng Account chỉ lưu customerId,
//        // do đó phải truy vấn khách hàng từ đối tượng CustomerList
//        List<Customer> customers = CustomerDao.list();
//
//    }

    public ArrayList<Transaction> getTransactions(ArrayList<Transaction> transactionsList, String AccountNumber) {

        for (Transaction transaction : transactionsList) {
            if (transaction.getAccoundNumber().equals(accountNumber)) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }

    public void displayTransactionsList() {
        if (accountTransactions.isEmpty()) {
            System.out.println("Khong tim thay giao dich");
        } else {
            {
                for (Transaction t : accountTransactions) {
                    t.toString();
                }
            }
        }
    }

    public void createTransaction(double amount, Date time, boolean status, TransactionType type) {
        if (type == TransactionType.DEPOSIT) {
            balance += amount;
        } else if (type == TransactionType.WITHDRAW) {
            balance -= amount;
        } else if (type == TransactionType.TRANFERS) {
            balance -= amount;
        }
        Transaction transaction = new Transaction(getAccountNumber(), amount, new Date(), true, type);
        System.out.println("Giao dịch thanh cong");
    }

    public static Account input(Scanner scanner) {
        System.out.println("Nhap so tai khoan: ");
        String accountNumber = scanner.nextLine();
        System.out.println("Nhap so du ban dau");
        double balance = scanner.nextDouble();
        if (balance <= 50000) {
            System.out.println("So tien khong hop le");
            return null;
        }
        Transaction transaction = new Transaction(accountNumber, balance, new Date(), true, TransactionType.DEPOSIT);
        Account account = new Account(accountNumber, balance);
        account.createTransaction(balance, new Date(), true, TransactionType.DEPOSIT);
        System.out.println("them tai khoan thanh cong");
        return account;
    }
}
