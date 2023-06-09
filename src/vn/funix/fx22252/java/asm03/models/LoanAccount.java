package vn.funix.fx22252.java.asm03.models;

import vn.funix.fx22252.java.asm02.models.Account;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanAccount extends Account implements IWithdraw, IReportService {
    private final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    private static final double LOAN_ACCOUNT_MAX_BALANCE = 100000000;
    private final String LOAN_ACCOUNT_TYPE = "LOAN";


    public LoanAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
        this.accountType = LOAN_ACCOUNT_TYPE;
    }


    public double getFee(double amount) {
        double fee;
        if (isPremium()) {
            fee = amount * LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE;
        } else {
            fee = amount * LOAN_ACCOUNT_WITHDRAW_FEE;
        }
        return fee;
    }

    //Override tu Interface
    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            double newBalance = getBalance() - amount - getFee(amount);
            double fee=getFee(amount);
            log(amount);
            setBalance(newBalance);
            System.out.println("G/D thanh cong");
            addTransaction(new Transaction(getAccountNumber(), -(amount + fee), new Date(), true));
            return true;
        }

        System.out.println("G/D that bai");
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (amount < 0) {
            System.out.println("So tien rut phai lon hon 0");
            return false;
        } else if (amount > LOAN_ACCOUNT_MAX_BALANCE) {
            System.out.println("So tien rut vuot qua han muc");
            return false;
        } else if (getBalance() - amount < 50000) {
            System.out.println("Ban can duy tri so du tai khoan toi thieu 50,000đ");
            return false;
        }
        return true;
    }

    @Override
    public void log(double amount) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DecimalFormat decimalFormat = new DecimalFormat("#,###đ");
        System.out.println("+--------------+----------------------+");
        System.out.println("\t  BIEN LAI GIAO DICH LOAN ");
        System.out.printf("NGAY G/D: %28s%n", dateFormat.format(new Date()));
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK: %31s%n", getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", decimalFormat.format(amount));
        System.out.printf("SO DU: %31s%n", decimalFormat.format(getBalance()-amount-getFee(amount)));
        System.out.printf("PHI + VAT: %27s%n", decimalFormat.format(getFee(amount)));
        System.out.println("+--------------+----------------------+");
    }


}
