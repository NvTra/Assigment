package vn.funix.fx22252.java.asm03.models;

import vn.funix.fx22252.java.asm02.models.Account;
import vn.funix.fx22252.java.asm02.models.Bank;
import vn.funix.fx22252.java.asm02.models.Customer;
import vn.funix.fx22252.java.asm04.dao.AccountDao;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;


import java.io.*;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DigitalBank extends Bank {


    public Customer getCustomerById(String customerId) {
        for (int i = 0; i < getCustomers().size(); i++) {
            if (getCustomers().get(i).getCustomerId().equals(customerId)) {
                return getCustomers().get(i);
            }
        }
        return null;
    }

    @Override
    public void addAccount(String customerId, Account newAccount) {
        super.addAccount(customerId, newAccount);
    }


    @Override
    public void addCustomer(Customer newCustomer) {
        super.addCustomer(newCustomer);
    }

    public boolean validateAccount(String accountNumber) {
        Pattern pattern = Pattern.compile("^\\d{6}$");
        if (!pattern.matcher(accountNumber).find()) {
            System.out.println("Tai khoan ban nhap khong hop le. Vui long nhap lai");
            return false;
        }
        if (super.isValidAccount(accountNumber)) {
            System.out.println("so TK da duoc su dung");
            return false;
        }
        return true;
    }

    public boolean nullAccount(String accountNumber) {
        Pattern pattern = Pattern.compile("^\\d{6}$");
        if (!pattern.matcher(accountNumber).find()) {
            return false;
        }
        if (super.isValidAccount(accountNumber)) {
            return false;
        }
        return true;
    }

    public boolean withdraw(String customerId, String accountNumber, double amount) {
        if (getCustomerById(customerId) == null) {
            System.out.println("Khach hang khong ton tai.");
            return false;
        }
        Account account = getCustomerById(customerId).getAccountsByAccountNumber(accountNumber);
        if (account == null) {
            System.out.println("Tai khoan khong ton tai");
            return false;
        }
        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).withdraw(amount);
            return true;
        } else if (account instanceof LoanAccount) {
            ((LoanAccount) account).withdraw(amount);
            return true;
        }
        System.out.println("Khong tim thay khach hang!");
        return false;
    }

    //ASS 04
    public void addCustomers(String fileName) {
        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] tokens = line.split(",");
                if (tokens.length != 2) {
                    System.out.println("du lieu khong hop le: " + line);
                    continue;
                }
                String customerID = tokens[0];
                String name = tokens[1];
                if (!validateCustomerId(customerID)) {
                    System.out.println("So CCCD " + customerID + " Khong hop le.");
                } else if (isCustomerExisted(CustomerDao.list(), new Customer(name, customerID))) {
                    System.out.println("Khach hang " + customerID + " da ton tai, them khach hang khong thanh cong");
                } else {
                    System.out.println("Da them khach hang " + customerID + " vao danh sach khach hang");
                    getCustomers().add(new Customer(name, customerID));
                }
            }
            if (CustomerDao.list().isEmpty()) {
                CustomerDao.save(getCustomers());
            } else {
                for (Object object : CustomerDao.list()) {
                    getCustomers().add((Customer) object);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File khong ton tai");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCustomers() {
        List<Customer> customersList = CustomerDao.list();
        if (customersList.isEmpty()) {
            System.out.println("Chua có khach hang nao trong danh sach");
        } else {
            for (Customer customer : customersList) {
                customer.displayInformationN();
            }
        }
    }


    public void addSavingAccount(Scanner scanner, String customerId) throws IOException {
        for (Customer customer : getCustomers()) {
            if (customer.getCustomerId().equals(customerId)) {
                String maTK;
                do {
                    System.out.print("Nhap so tai khoan gom 6 chu so: ");
                    maTK = scanner.nextLine();
                } while (!nullAccount(maTK));
//                ||isAccountExisted(AccountDao.list(),new Account(maTK,0))
                double balance;
                do {
                    System.out.print("Nhap so du tai khoan >= 50000đ: ");
                    balance = scanner.nextDouble();
                } while (balance < 50000);
                customer.addAccount(new SavingsAccount(customerId,maTK,balance));
                AccountDao.save(customer.getAccounts());
                System.out.println("Tao tai khoan thanh cong");
            }
        }

    }

    public void withdraw(Scanner scanner, String customerId) {
        for (Customer customer : getCustomers()) {
            if ((customer.getCustomerId().equals(customerId))) {
                customer.displayInformation();
                customer.withdraw(scanner);
            }
        }

    }

    public void tranfers(Scanner scanner, String customerId) {
        for (Customer customer : getCustomers()) {
            if ((customer.getCustomerId().equals(customerId))) {
                searchCustomerByCCCD(customerId);
                withdraw(scanner, customerId);
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

    public boolean isCustomerExisted(List<Customer> customers, Customer newCustomer) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(newCustomer.getCustomerId())) {
                return true;
            }
        }
        return false;
    }

    public String getCustomerById(List<Customer> customerList, String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                return customerId;
            }
        }
        return null;
    }
}
