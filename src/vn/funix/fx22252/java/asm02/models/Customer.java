package vn.funix.fx22252.java.asm02.models;

import vn.funix.fx22252.java.asm03.models.LoanAccount;
import vn.funix.fx22252.java.asm03.models.SavingsAccount;
import vn.funix.fx22252.java.asm03.models.Transaction;
import vn.funix.fx22252.java.asm04.dao.AccountDao;


import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    //khai bao thuoc tinh
    private final List<Account> accounts;
    private final long serialVersionUID = 2L;

    //khoi tao constructor
    public Customer() {
        this.accounts = new ArrayList<>();
    }

    public Customer(String name, String customerId) {
        super(name, customerId);
        this.accounts = new ArrayList<>();
    }


    //----------------begin setter and getter ---------------
    public List<Account> getAccounts() {
        return accounts;
    }
    //----------------end setter and getter -----------------

    // kiem tra tai khoan premium neu Kh la premium
    public boolean isPremium() {
        for (Account acount : accounts) {
            if (acount.isPremium()) {
                return true;
            }
        }
        return false;
    }

    // Add TK cho KH
    public void addAccount(Account newAccount) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(newAccount.getAccountNumber())) {
                System.out.println("Tai khoan da duoc su dung");
                return;
            }
        }
        accounts.add(newAccount);
    }

    //kiem tra su ton tai cua maKH
    public boolean isAccountExisted(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    // phuong thuc tinh tong so du
    public double getTotalAccountBalance() {
        double totalBalance = 0;
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    //phuong thuc hien thi
    public void displayInformation() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        //hien thi thong tin TK
        System.out.printf("%-14s|%10s |%8s | %22s\n", getCustomerId(), getName(), (isPremium() ? "Premium" : "Normal"), df.format(getTotalAccountBalance()));
        //hien thi cac tai khoan ma KH so huu
        if (accounts.size() > 0) {
            for (int j = 0; j < accounts.size(); j++) {
                System.out.println((j + 1) + "   " + this.accounts.get(j).toString());
            }
        }
    }

    //lay account neu TK ton tai
    public Account getAccountsByAccountNumber(String accountNumber) {
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public void displayAllTransaction() {
        if (getAccounts().size() > 0) {
            for (Account account : getAccounts()) {
                account.diplayTransaction();
            }
        }
    }
    public boolean isAccountExisted(List<Account> accountsList, Account newAccount) {
        for (Account account : accountsList) {
            if (account.getAccountNumber().equals(newAccount.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }
    public boolean isValidAcounttNumber(String accountNumber) {
        Pattern pt = Pattern.compile("^\\d{6}$");
        if (!pt.matcher(accountNumber).find()) {
            return false;
        } else if (isAccountExisted(AccountDao.list(),new Account(accountNumber,0))) {
            return false;
        }
        return true;
    }

    //Ass04
    public List<Account> getAccountsN() {
        List<Account> accountList = AccountDao.list();
        return accountList
                .stream()
                .filter(account -> account.getCustomerId().equals(this.getCustomerId()))
                .collect(Collectors.toList());
    }

    public void displayInformationN() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        //hien thi thong tin TK
        System.out.printf("%-14s|%20s |%8s | %22s\n", getCustomerId(), getName(), (isPremium() ? "Premium" : "Normal"), df.format(getTotalAccountBalance()));
        List<Account> accounts = getAccountsN();
        if (accounts.size() > 0) {
            for (int j = 0; j < accounts.size(); j++) {
                System.out.println((j + 1) + "   " + accounts.get(j).toString());
            }
        }
    }

    public Account getAccountByAccountNumber(List<Account> accounts, String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public Account input(Scanner scanner) throws IOException {
        String accountNumber;
        do {
            System.out.println("Nhap so tai khoan gom 6 chu so: ");
            accountNumber = scanner.nextLine();
        } while (!isValidAcounttNumber(accountNumber));
        double balance = 0;
        do {
            try {

                System.out.print("Nhap so du tai khoan >= 50000đ: ");
                balance = scanner.nextDouble();
            } catch (Exception e) {
                scanner.nextLine();
            }
        } while (balance < 50000);
        addAccount(new SavingsAccount(getCustomerId(), accountNumber, balance));
        AccountDao.save(getAccounts());
        Transaction transaction = new Transaction(accountNumber, balance, new Date(), true, Transaction.TransactionType.DEPOSIT);
        Account account = new Account(accountNumber, balance);
        account.createTransaction(balance, new Date(), true, Transaction.TransactionType.DEPOSIT);
        return account;
    }

    public void withdraw(Scanner scanner) {
        List<Account> accounts = getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Khach hang khong co tai khoan nao, thao tac khong thanh cong");
        } else {
            Account account;
            double amout;
            do {
                System.out.print("Nhap so tai khoan: ");
                account = getAccountByAccountNumber(accounts, scanner.nextLine());
            } while (accounts == null);
            do {
                System.out.print("Nhap so tien rut: ");
                amout = Double.parseDouble(scanner.nextLine());
            } while (amout <= 0);
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).withdraw(amout);
            } else if (account instanceof LoanAccount) {
                ((LoanAccount) account).withdraw(amout);
            }
        }
    }

    public void transfers(Scanner scanner) throws IOException {
        String accNumber;
        do {
            System.out.print("Nhap so tai khoan:");
            accNumber = scanner.nextLine();
        } while (!isAccountExisted(accNumber));
        String receiveNumber;
        do {
            System.out.print("Nhap so tai khoan nhan:");
            receiveNumber = scanner.nextLine();
        } while (!isAccountExisted(receiveNumber));

//        System.out.println("Gui tien den tai khoan: " + receiveNumber + "   |  ");

        System.out.print("Nhap so tien chuyen: ");
        double amount = 0;
        while (amount < 50000) {
            amount = scanner.nextDouble();
            if (amount < 50000) {
                System.out.println("So tien khong hop le. ");
                scanner.nextLine();
            }
        }
        scanner.nextLine();
        String confirm;
        do {
            System.out.print("Xac nhan thuc hien chuyen: " + amount + " tu tai khoan [" + accNumber + "] den tai khoan [" + receiveNumber + "] (Y/N): ");
            confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("n")) {
                break;
            }
        }
        while (!confirm.equalsIgnoreCase("y"));
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accNumber)) {
                SavingsAccount acc = (SavingsAccount) getAccountsByAccountNumber(accNumber);
                SavingsAccount recceiveAccount = (SavingsAccount) getAccountsByAccountNumber(receiveNumber);
                acc.transfer(recceiveAccount, amount);
            }
        }
        AccountDao.save(getAccounts());
    }
}
