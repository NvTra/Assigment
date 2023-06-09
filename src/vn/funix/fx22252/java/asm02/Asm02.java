package vn.funix.fx22252.java.asm02;

import vn.funix.fx22252.java.asm02.models.Account;
import vn.funix.fx22252.java.asm02.models.Bank;
import vn.funix.fx22252.java.asm02.models.Customer;


import java.util.Scanner;
import java.util.regex.Pattern;

public class Asm02 {
    private static final Bank bank = new Bank();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ProgramSelection();

    }

    public static void ProgramSelection() {
        while (true) {
            System.out.println("+----------+-------------------+-------+");
            System.out.println("| NGAN HANG SO | FX22252@2.0.0         |");
            System.out.println("+----------+-------------------+-------+");
            System.out.println(" 1. Them khach hang                     ");
            System.out.println(" 2. Them tai khoan cho khach hang       ");
            System.out.println(" 3. Hien thi danh sach khach hang       ");
            System.out.println(" 4. Tim theo CCCD                       ");
            System.out.println(" 5. Tim theo ten khach hang             ");
            System.out.println(" 0. Thoat                               ");
            System.out.println("+----------+-------------------+-------+");
            System.out.print("Chuc nang: ");
            try {
                int select = sc.nextInt();
                if (select == 0) {
                    System.exit(0);
                } else if (select == 1) {
                    addCustomer();
                } else if (select == 2) {
                    addAccount();
                } else if (select == 3) {
                    for (int i = 0; i < bank.getCustomers().size(); i++) {
                        bank.getCustomers().get(i).displayInformation();
                    }
                } else if (select == 4) {
                    System.out.print("Nhap so CCCD khach hang: ");
                    String soCCCD = sc.next();
                    bank.searchCustomerByCCCD(soCCCD);
                } else if (select == 5) {
                    System.out.println("nhap ten khach hang");
                    String name = sc.next();
                    bank.searchCustomerByName(name);
                } else {
                    System.out.println("Khong hop le. Vui long chon lai:");
                }
            } catch (Exception e) {
                System.out.println("Khong hop le. Vui long chon lai:");
                sc.nextLine();
            }
        }
    }

    //Option select
    public static void addCustomer() {
        String soCCCD;
        System.out.println("+----------+-------------------+-------+");
        System.out.print("Nhap ten khach hang: ");
        String name = sc.next();
        sc.nextLine();
        while (true) {
            System.out.print("Nhap so CCCD: ");
            soCCCD = sc.next();
            if (soCCCD.equalsIgnoreCase("no")) {
                System.exit(0);
            } else if (!bank.validateCustomerId(soCCCD)) {
                System.out.println("So CCCD khong hop le");
                System.out.println("Vui long nhap lai hoac nhap 'No' de thoat:");
            } else if (bank.isCustomerExisted(soCCCD)) {
                System.out.println("So CCCD da duoc su dung.");
                System.out.println("Vui long nhap lai hoac nhap 'No' de thoat:");
            } else {
                break;
            }
        }
        System.out.println("Da them khach hang " + soCCCD + " vao danh sach");
        bank.addCustomer(new Customer(name, soCCCD));


    }

    public static void addAccount() {
        String soCCCD;
        String maSTK;
        double balance;
        while (true) {
            System.out.println("+----------+-------------------+-------+");
            System.out.print("Nhap CCCD khach hang: ");
            soCCCD = sc.next();
            if (!bank.validateCustomerId(soCCCD)) {
                System.out.println("So CCCD khong hop le");
                System.out.println("Vui long nhap lai hoac nhap 'No' de thoat:");
            } else if (soCCCD.equalsIgnoreCase("no")) {
                System.exit(0);
            } else if (bank.findCustomer(soCCCD) == null) {
                System.out.println("Khong tim thay khach hang voi so CCCD nay");
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("Nhap ma so TK gom 6 chu so:");
            maSTK = sc.next();
            Pattern pattern = Pattern.compile("^\\d{6}$");
            if (!pattern.matcher(maSTK).find()) {
                System.out.println("Ma tai khoan khong hop le");
            } else if (bank.isValidAccount(maSTK)) {
                System.out.println("Tai khoan da duoc su dung");
            } else {
                break;
            }
        }
        while (true) {
            System.out.println("Nhap so du:");
            balance = sc.nextDouble();
            if (balance < 50000) {
                System.out.println("so du toi thieu la 50,000đ");
            } else {
                break;
            }
        }
        bank.addAccount(soCCCD, new Account(maSTK, balance));
        System.out.println("Them tai khoan thanh cong");
    }


}
