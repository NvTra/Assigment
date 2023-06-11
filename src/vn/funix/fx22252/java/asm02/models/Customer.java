package vn.funix.fx22252.java.asm02.models;

import vn.funix.fx22252.java.asm03.models.DigitalBank;
import vn.funix.fx22252.java.asm03.models.LoanAccount;
import vn.funix.fx22252.java.asm03.models.SavingsAccount;
import vn.funix.fx22252.java.asm03.models.Transaction;
import vn.funix.fx22252.java.asm04.dao.AccountDao;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;


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
        for (Account account : getAccounts()) {
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

    public static Account getAccountbyAccountNumberN(String accountNumber) {
        for (Customer customer : CustomerDao.list()) {
            for (Account account : customer.getAccounts()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return account;
                }

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
        } else return !isAccountExisted(getAccounts(), new Account(accountNumber, 0));
    }

    //Ass04
    public List<Account> getAccountsN() {
        List<Account> accountList = getAccounts();
        return accountList
                .stream()
                .filter(account -> account.getCustomerId().equals(this.getCustomerId()))
                .collect(Collectors.toList());
    }

    public void displayInformationN() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        //hien thi thong tin TK
        System.out.printf("%-13s| %-32s |%8s | %22s\n", getCustomerId(), getName(), (isPremium() ? "Premium" : "Normal"), df.format(getTotalAccountBalance()));
        List<Account> accounts = getAccountsN();
        if (accounts.size() > 0) {
            for (int j = 0; j < accounts.size(); j++) {
                System.out.println((j + 1) + accounts.get(j).toString());
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

    public void input(Scanner scanner) throws IOException {
        String accountNumber;
        do {
            System.out.print("Nhap so tai khoan gom 6 chu so: ");
            accountNumber = String.valueOf(Integer.parseInt(scanner.nextLine()));
        } while (!isValidAcounttNumber(accountNumber) || DigitalBank.isAccoutexist(accountNumber));
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
        Account account = new Account(accountNumber, balance);
        account.createTransaction(0, new Date(), true, Transaction.TransactionType.DEPOSIT);
    }

    public void withdraw(Scanner scanner) throws IOException {
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
        String depositAccountNumber;
        do {
            System.out.print("Nhap so tai khoan:");
            depositAccountNumber = scanner.nextLine();
        } while (!isAccountExisted(depositAccountNumber));
        String receiveNumber;
        do {
            System.out.print("Nhap so tai khoan nhan:");
            receiveNumber = scanner.nextLine();
        } while (!DigitalBank.isAccoutexist(receiveNumber));
        System.out.println("Gui tien den tai khoan: " + receiveNumber + "   |  " + DigitalBank.getCustomerbyAccountNumber(receiveNumber).getName());
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
            System.out.print("Xac nhan thuc hien chuyen: " +String.format("%.1f đ",amount)  + " tu tai khoan [" + depositAccountNumber + "] den tai khoan [" + receiveNumber + "] (Y/N): ");
            confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("n")) {
                break;
            }
            for (Customer customer : CustomerDao.list()) {
                for (Account account : customer.getAccounts()) {
                    if (account.getAccountNumber().equals(depositAccountNumber)) {
                        SavingsAccount acc = (SavingsAccount) getAccountsByAccountNumber(depositAccountNumber);
                        SavingsAccount recceiveAccount = (SavingsAccount) getAccountbyAccountNumberN(receiveNumber);
                        acc.transfer(recceiveAccount, amount);
                        AccountDao.update2(acc);
                        AccountDao.update2(recceiveAccount);
                        recceiveAccount.addTransaction(new Transaction(receiveNumber, amount, new Date(), true, Transaction.TransactionType.DEPOSIT));
                    }
                }
            }
        }
        while (!confirm.equalsIgnoreCase("n"));


//            AccountDao.save(getAccounts());
//            AccountDao.save(DigitalBank.getCustomerbyAccountNumber(receiveNumber).getAccounts());


    }

    public void displayTransactionInformation() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        //hien thi thong tin khach hang
        System.out.printf("%-13s| %-32s |%8s | %22s\n", getCustomerId(), getName(), (isPremium() ? "Premium" : "Normal"), df.format(getTotalAccountBalance()));
        // hien thi tai khoan khach hang
        List<Account> accounts = getAccountsN();
        if (accounts.size() > 0) {
            for (int j = 0; j < accounts.size(); j++) {
                System.out.println((j + 1) + accounts.get(j).toString());
            }
        }
        getAccounts().forEach(account -> account.displayTransactionsList());

    }
}
