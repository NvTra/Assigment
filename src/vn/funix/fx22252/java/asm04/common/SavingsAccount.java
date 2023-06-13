package vn.funix.fx22252.java.asm04.common;

import vn.funix.fx22252.java.asm03.models.IReportService;
import vn.funix.fx22252.java.asm03.models.IWithdraw;
import vn.funix.fx22252.java.asm04.dao.AccountDao;
import vn.funix.fx22252.java.asm04.model.IReport;
import vn.funix.fx22252.java.asm04.model.ITransfer;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccount extends Account implements IReportService, IWithdraw, ITransfer, IReport, Serializable {
    private final long serialVesionUID = 2L;
    private final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    private final String SAVINGS_ACCOUNT_TYPE = "SAVINGS";

    public SavingsAccount() {

        this.accountType = SAVINGS_ACCOUNT_TYPE;
    }

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
        this.accountType = SAVINGS_ACCOUNT_TYPE;
    }

    public SavingsAccount(String customerId, String accountNumber, double balance) {
        super(customerId, accountNumber, balance);
        this.accountType = SAVINGS_ACCOUNT_TYPE;
    }
    //Override Interface


    @Override
    public boolean withdraw(double amount) throws IOException {
        if (isAccepted(amount)) {
            createTransaction(amount, new Date(), true, Transaction.TransactionType.WITHDRAW);
            AccountDao.update2(getCustomer().getAccountByAccountNumber(AccountDao.list(), getAccountNumber()));
            System.out.println("G/D thanh cong");
            log(amount);
            return true;
        }

        System.out.println("G/D that bai");
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (amount < 50000) {
            System.out.println("Ban can rut it nhat 50,000đ.");
            return false;
        } else if (amount % 10000 != 0) {
            System.out.println("So tien can rut phai la boi so cua 10,000đ.");
            return false;
        } else if (!isPremium() && amount > SAVINGS_ACCOUNT_MAX_WITHDRAW) {
            System.out.println("Ban chi duoc rut toi da 5,000,000đ.");
            return false;
        } else if (getBalance() - amount < 50000) {
            System.out.println("So du tai khoan cua ban khong du de thuc hien giao dich nay.");
            return false;
        }
        return true;
    }

    @Override
    public void log(double amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        System.out.println("+--------------+----------------------+");
        System.out.println("\t  BIEN LAI GIAO DICH SAVINGS ");
        System.out.printf("NGAY G/D: %28s%n", dateFormat.format(new Date()));
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK: %31s%n", getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", decimalFormat.format(amount));
        System.out.printf("SO DU: %31s%n", decimalFormat.format(getBalance()));
        System.out.printf("PHI + VAT: %27s%n", "0đ");
        System.out.println("+--------------+----------------------+");
    }

    @Override
    public void transfer(Account receiveAccount, double amount) throws IOException {
        if (isAccepted(amount)) {
            double newBalance;
            createTransaction(amount, new Date(), true, Transaction.TransactionType.TRANFERS);
            receiveAccount.createTransaction(amount, new Date(), true, Transaction.TransactionType.DEPOSIT);
            System.out.println("Chuyen tien thanh cong, bien lai giao dich: ");
            log(amount, Transaction.TransactionType.TRANFERS, receiveAccount);
        } else {
            System.out.println("G/D khong thanh cong");
        }
    }

    @Override
    public void log(double amount, Transaction.TransactionType type, Account receiveAccount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        System.out.println("+--------------+----------------------+");
        System.out.println("\t  BIEN LAI GIAO DICH SAVINGS ");
        System.out.printf("NGAY G/D: %28s%n", dateFormat.format(new Date()));
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK: %31s%n", getAccountNumber());
        System.out.printf("SO TK NHAN: %26s%n", receiveAccount.getAccountNumber());
        System.out.printf("SO TIEN CHUYEN: %22s%n", decimalFormat.format(amount));
        System.out.printf("SO DU: %31s%n", decimalFormat.format(getBalance()));
        System.out.printf("PHI + VAT: %27s%n", "0đ");
        System.out.println("+--------------+----------------------+");
    }
}
