package vn.funix.fx22252.java.asm03.models;


import vn.funix.fx22252.java.asm04.common.Customer;

import java.text.DecimalFormat;


public class DigitalCustomer extends Customer {
    public DigitalCustomer(String name, String customerId) {
        super(name, customerId);
    }


    @Override
    public void displayInformation() {
        DecimalFormat df = new DecimalFormat("#,###đ");
        //hien thi thong tin TK
        System.out.printf("%-14s|%10s |%8s | %22s\n", getCustomerId(), getName(), (isPremium() ? "Premium" : "Normal"), df.format(getTotalAccountBalance()));
        //hien thi cac tai khoan ma KH so huu
        if (getAccounts().size() > 0) {
            for (int j = 0; j < getAccounts().size(); j++) {
                System.out.printf("%-3s %-10s|%10s | %32s\n", (j + 1), this.getAccounts().get(j).getAccountNumber(), this.getAccounts().get(j).getAccountType(), df.format(this.getAccounts().get(j).getBalance()));
            }
        }
    }

    @Override
    public void displayAllTransaction() {
        super.displayAllTransaction();
    }
}
