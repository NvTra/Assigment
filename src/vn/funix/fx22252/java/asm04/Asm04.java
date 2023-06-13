package vn.funix.fx22252.java.asm04;


import vn.funix.fx22252.java.asm04.common.Customer;
import vn.funix.fx22252.java.asm04.common.DigitalBank;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;

import java.io.IOException;
import java.util.Scanner;

public class Asm04 {
    private final static Scanner sc = new Scanner(System.in);

    private final static DigitalBank activeBank = new DigitalBank();

    public static void main(String[] args) {
        programSelection();
    }

    public static void showMenu() {
        System.out.print(
                """
                        +----------+---------------------------------------+-------+
                        | NGAN HANG SO | FX22252@4.0.0                             |
                        +----------+---------------------------------------+-------+
                        | 1. Xem danh sach khach hang                              |
                        | 2. Nhap danh sach khach hang                             |
                        | 3. Them tai khoan ATM                                    |
                        | 4. Chuyen tien                                           |
                        | 5. Rut tien                                              |
                        | 6. Tra cuu lich su giao dich                             |
                        | 0. Thoat                                                 |
                        +----------+---------------------------------------+-------+
                        """);
        System.out.print("Chuc nang: ");
    }

    public static void programSelection() {
        activeBank.startUp();
        int select = 0;

        do {
            showMenu();
            try {
                select = sc.nextInt();
                if (select == 1) {
                    listCustomer();
                } else if (select == 2) {
                    inputCustomers();
                } else if (select == 3) {
                    addATMAccount();
                } else if (select == 4) {
                    transfer();
                } else if (select == 5) {
                    withdraw();
                } else if (select == 6) {
                    displayTransactionInformation();
                } else {
                    System.out.println("Chuc nang khong hop le. Vui long chon lai");
                }
            } catch (Exception e) {
                System.out.println("Chuc nang khong hop le. Vui long chon lai");
                sc.nextLine();
            }
        } while (select != 0);
    }

    private static void listCustomer() {
        activeBank.showCustomers();
    }

    public static void inputCustomers() {
        System.out.println("+----------------------------------------------------------+");
        System.out.println("Nhap duong dan den tep: ");
        String path = sc.next();
        sc.nextLine();
        activeBank.addCustomers(path);
    }

    private static void addATMAccount() throws IOException {
        System.out.println("+----------------------------------------------------------+");
        String customerId;
        sc.nextLine();
        while (true) {
            System.out.println("Nhap ma so cua khach hang: ");
            customerId = sc.nextLine();
            if (!activeBank.validateCustomerId(customerId)) {
                System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            } else if (!activeBank.isCustomerExisted(CustomerDao.list(), new Customer(null, customerId))) {
                System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            } else {
                break;
            }
        }
        activeBank.addSavingAccount(new Scanner(System.in), customerId);


    }

    public static void transfer() throws IOException {
        System.out.println("+----------------------------------------------------------+");
        sc.nextLine();
        String customerId;
        while (true) {
            System.out.println("Nhap ma so cua khach hang: ");
            customerId = sc.nextLine();
            if (!activeBank.validateCustomerId(customerId)) {
                System.out.println("Ma so khong dung");
            } else if (!activeBank.isCustomerExisted(CustomerDao.list(), new Customer("", customerId))) {
                System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            } else {
                break;
            }
        }
        activeBank.tranfers(new Scanner(System.in), customerId);
        CustomerDao.save(activeBank.getCustomers());
    }

    public static void withdraw() throws IOException {
        System.out.println("+----------------------------------------------------------+");
        sc.nextLine();
        String customerId;
        while (true) {
            System.out.println("Nhap ma so cua khach hang: ");
            customerId = sc.nextLine();
            if (!activeBank.validateCustomerId(customerId)) {
                System.out.println("Ma so khong dung");
            } else if (!activeBank.isCustomerExisted(CustomerDao.list(), new Customer("", customerId))) {
                System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            } else {
                break;
            }
        }
        activeBank.withdraw(new Scanner(System.in), customerId);
        CustomerDao.save(activeBank.getCustomers());
    }

    private static void displayTransactionInformation() {
        System.out.println("+----------------------------------------------------------+");
        sc.nextLine();
        String customerId;
        while (true) {
            System.out.println("Nhap ma so cua khach hang: ");
            customerId = sc.nextLine();
            if (!activeBank.validateCustomerId(customerId)) {
                System.out.println("Ma so khong dung");
            } else if (!activeBank.isCustomerExisted(CustomerDao.list(), new Customer("", customerId))) {
                System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            } else {
                break;
            }
        }
        activeBank.getCustomerById(customerId).displayTransactionInformation();
    }
}
