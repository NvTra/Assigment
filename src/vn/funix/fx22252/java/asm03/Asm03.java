package vn.funix.fx22252.java.asm03;


import vn.funix.fx22252.java.asm02.models.Customer;
import vn.funix.fx22252.java.asm03.models.*;

import java.io.IOException;
import java.util.Date;
import java.util.Scanner;


public class Asm03 {
    private static final int EXIT_COMMAND_CODE = 0;
    private static final int EXIT_ERROR_CODE = -1;
    private static final Scanner sc = new Scanner(System.in);
    private static final DigitalBank activeBank = new DigitalBank();
    private static final String CUSTOMER_ID = "040095012040";
    private static final String CUSTOMER_NAME = "Nguyen Tra";

    public static void main(String[] args) {
        programSelection();
    }

    public static void showMenu() {
        System.out.println("+----------+---------------------------------------+-------+");
        System.out.println("| NGAN HANG SO | FX22252@3.0.0                             |");
        System.out.println("+----------+---------------------------------------+-------+");
        System.out.println(" 1. Thong tin khach hang                ");
        System.out.println(" 2. Them tai khoan ATM                  ");
        System.out.println(" 3. Them tai khoan tin dung             ");
        System.out.println(" 4. Rut tien                            ");
        System.out.println(" 5. Lich su giao dich                   ");
        System.out.println(" 0. Thoat                               ");
        System.out.println("+----------+---------------------------------------+-------+");
        System.out.print("Chuc nang: ");
    }

    public static void programSelection() {

        activeBank.addCustomer(new Customer(CUSTOMER_NAME, CUSTOMER_ID));
        while (true) {
            try {
                showMenu();
                int select = sc.nextInt();
                switch (select) {
                    case 0:
                        System.exit(EXIT_COMMAND_CODE);
                        break;
                    case 1:
                        showCustomer();
                        break;
                    case 2:
                        addSavingsAccount();
                        break;
                    case 3:
                        addLoanAccount();
                        break;
                    case 4:
                        withdraw();
                        break;
                    case 5:
                        listOfTransactions();
                        break;
                    default:
                        System.out.println("Chuc nang khong hop le. Vui long chon lai");
                }
            } catch (Exception e) {
                System.out.println("Chuc nang khong hop le. Vui long chon lai");
                sc.nextLine();
            }
        }
    }

    private static void showCustomer() {
        System.out.println("+----------+---------------------------------------+-------+");
        try {
            Customer customer = activeBank.getCustomerById(CUSTOMER_ID);
            if (customer != null) {
                customer.displayInformation();
            }
        } catch (Exception e) {
            System.out.println("Khach hang khong ton tai");
        }
    }

    public static void addSavingsAccount() {
        System.out.println("+----------+---------------------------------------+-------+");
        String accountNumber;
        do {
            System.out.print("Nhap STK gom 6 chu so: ");
            accountNumber = sc.next();
        } while (!activeBank.validateAccount(accountNumber));
        System.out.print("Nhap so du: ");
        double balance = sc.nextDouble();

        while (balance < 50000) {
            System.out.println("so du toi thieu la 50,000d");
            balance = sc.nextDouble();
            System.out.println("Nhap so du: ");
        }
        activeBank.addAccount(CUSTOMER_ID, new SavingsAccount(accountNumber, balance));
        activeBank.getCustomerById(CUSTOMER_ID).getAccountsByAccountNumber(accountNumber).addTransaction(new Transaction(accountNumber, balance, new Date(), true));
        System.out.println("them tai khoan thanh cong");
    }

    public static void addLoanAccount() {
        System.out.println("+----------+---------------------------------------+-------+");
        String accountNumber;
        do {
            System.out.print("Nhap STK gom 6 chu so: ");
            accountNumber = sc.next();
        } while (!activeBank.validateAccount(accountNumber));
        System.out.print("Nhap so tien vay: ");
        double balance = sc.nextDouble();
        while (balance > 100000000) {
            System.out.println("so tien vay vuot qua han muc");
            balance = sc.nextDouble();
            System.out.println("Nhap so tien vay: ");
        }
        activeBank.addAccount(CUSTOMER_ID, new LoanAccount(accountNumber, balance));
        activeBank.getCustomerById(CUSTOMER_ID).getAccountsByAccountNumber(accountNumber).addTransaction(new Transaction(accountNumber, balance, new Date(), true));
        System.out.println("them tai khoan tin dung thanh cong");
    }

    public static void withdraw() throws IOException {
        System.out.println("+----------+---------------------------------------+-------+");
        String accountNumber;
        do {
            System.out.print("nhap STK muon rut: ");
            accountNumber = sc.next();
        } while (!activeBank.nullAccount(accountNumber));

        System.out.print("Nhap so tien can rut: ");
        double amount = sc.nextDouble();
        activeBank.withdraw(CUSTOMER_ID, accountNumber, amount);
    }

    public static void listOfTransactions() {
        showCustomer();
        activeBank.getCustomerById(CUSTOMER_ID).displayAllTransaction();
    }
}
