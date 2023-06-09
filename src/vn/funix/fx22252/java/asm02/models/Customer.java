package vn.funix.fx22252.java.asm02.models;

import vn.funix.fx22252.java.asm03.models.LoanAccount;
import vn.funix.fx22252.java.asm03.models.SavingsAccount;
import vn.funix.fx22252.java.asm04.dao.AccountDao;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    //khai bao thuoc tinh
    private List<Account> accounts;
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

    //Them TK cho KH
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

    //Ass04
    public List<Account> getAccountsN() {
        List<Account> accountList = AccountDao.list();
        return accountList.stream()
                .filter(account -> account.getCustomerId() == this.getCustomerId())
                .collect(Collectors.toList());
    }

    public void displayInformationN() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        //hien thi thong tin TK
        System.out.printf("%-14s|%20s |%8s | %22s\n", getCustomerId(), getName(), (isPremium() ? "Premium" : "Normal"), df.format(getTotalAccountBalance()));
        List<Account> accounts = AccountDao.list();
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
                System.out.println("Nhap so tien rut: ");
                amout = Double.parseDouble(scanner.nextLine());
            } while (amout <= 0);
            if (account instanceof SavingsAccount) {
                ((SavingsAccount) account).withdraw(amout);
            } else if (account instanceof LoanAccount) {
                ((LoanAccount) account).withdraw(amout);
            }
        }
    }
}
