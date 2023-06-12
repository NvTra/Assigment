package vn.funix.fx22252.java.asm03.models;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.UUID;

public class Transaction implements Serializable {
    private final long serialVersionUID = 2L;
    private final String Id;
    private final String accoundNumber;
    private final double amount;
    private final String time;
    private final boolean status;
    private TransactionType type;


    public Transaction(String accoundNumber, double amount, Date time, boolean status) {
        this.Id = String.valueOf(UUID.randomUUID());
        this.accoundNumber = accoundNumber;
        this.amount = amount;
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.time = formater.format(time);
        this.status = status;

    }

    public Transaction(String accoundNumber, double amount, Date time, boolean status, TransactionType type) {
        this.Id = String.valueOf(UUID.randomUUID());
        this.accoundNumber = accoundNumber;
        this.amount = amount;
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.time = formater.format(time);
        this.status = status;
        this.type = type;
    }

    //getter
    public String getAccoundNumber() {
        return accoundNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        String format = String.format("[GD]%8s | %-8s |%22s |%20s", getAccoundNumber(), type, (type.equals(TransactionType.DEPOSIT) ? df.format(getAmount()) : df.format(-getAmount())), getTime());
        return format;
    }


    public enum TransactionType {
        DEPOSIT, WITHDRAW, TRANFERS
    }
}
