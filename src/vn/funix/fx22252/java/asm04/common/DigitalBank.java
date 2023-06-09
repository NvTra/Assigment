package vn.funix.fx22252.java.asm04.common;

import vn.funix.fx22252.java.asm03.models.LoanAccount;
import vn.funix.fx22252.java.asm04.dao.AccountDao;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;
import vn.funix.fx22252.java.asm04.dao.TransactionDao;
import vn.funix.fx22252.java.asm04.service.TextFileService;


import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DigitalBank extends Bank {


    public static Customer getCustomerById(String customerId) {
        for (int i = 0; i < CustomerDao.list().size(); i++) {
            if (CustomerDao.list().get(i).getCustomerId().equals(customerId)) {
                return CustomerDao.list().get(i);
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
        return !super.isValidAccount(accountNumber);
    }


    public boolean withdraw(String customerId, String accountNumber, double amount) throws IOException {
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
    public static boolean isAccoutexist(String accountNumber) {

            for (Account account : AccountDao.list()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }

        return false;
    }

    public void addCustomers(String fileName) {
        try {
            List<List<String>> customerListFile = TextFileService.readFile(fileName);
            List<Customer> customers = super.getCustomers();
            if (customerListFile.isEmpty()) {
                System.out.println("File khong hop le");
            } else {
                for (List<String> customer : customerListFile) {
                    Customer newCustomer = new Customer(customer);
                    if (!validateCustomerId(newCustomer.getCustomerId())) {
                        System.out.println("So CCCD " + newCustomer.getCustomerId() + " Khong hop le.");
                    } else if (isCustomerExisted(CustomerDao.list(), newCustomer)) {
                        System.out.println("Khach hang " + newCustomer.getCustomerId() + " da ton tai, them khach hang khong thanh cong");
                    } else {
                        System.out.println("Da them khach hang " + newCustomer.getCustomerId() + " vao danh sach khach hang");
                        customers.add(newCustomer);
                    }
                }
            }
            CustomerDao.save(customers);
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
        List<Account> accounts = new ArrayList<>();
        List<Customer> customers = CustomerDao.list();//getCustomers()
        for (Customer customer : customers) {
            accounts.addAll(customer.getAccountsN());
            if (customer.getCustomerId().equals(customerId)) {
                accounts.addAll(customer.input(scanner));
            }
        }
        AccountDao.save(accounts);
        saveTransaction();
    }

    public void withdraw(Scanner scanner, String customerId) throws IOException {
        for (Customer customer : CustomerDao.list()) {
            if ((customer.getCustomerId().equals(customerId))) {
                customer.displayInformationN();
                customer.withdraw(scanner);
            }
        }
//        CustomerDao.save(getCustomers());
        saveTransaction();
    }

    public void tranfers(Scanner scanner, String customerId) throws IOException {
        for (Customer customer : CustomerDao.list()) {
            if ((customer.getCustomerId().equals(customerId))) {
                customer.displayInformationN();
                customer.transfers(scanner);
            }
        }
//        CustomerDao.save(getCustomers());
        saveTransaction();
    }

    public boolean isAccountExisted(List<Account> accountsList, Account newAccount) {
        return accountsList.stream()
                .anyMatch(account -> account.getAccountNumber().equals(newAccount.getAccountNumber()));
    }

    public boolean isCustomerExisted(List<Customer> customers, Customer newCustomer) {
        return customers.stream()
                .anyMatch(customer -> customer.getCustomerId().equals(newCustomer.getCustomerId()));
    }

    public String getCustomerById(List<Customer> customerList, String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                return customerId;
            }
        }
        return null;
    }


    public static Customer getCustomerbyAccountNumber(String accountNumber) {
        for (Customer customer : CustomerDao.list()) {
            for (Account account : customer.getAccountsN()) {
                if (account.getAccountNumber().equals(accountNumber)) {
                    account.getCustomer();
                    return customer;
                }

            }
        }
        return null;
    }

    public void saveTransaction() throws IOException {
        List<Transaction> transactionList = new ArrayList<>();
        for (Account account : AccountDao.list()) {
            transactionList.addAll(account.getTransactions());
        }
        TransactionDao.save(transactionList);
    }
}
