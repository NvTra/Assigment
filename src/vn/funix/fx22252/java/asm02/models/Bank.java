package vn.funix.fx22252.java.asm02.models;



import vn.funix.fx22252.java.asm04.dao.CustomerDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    //khai bao thuoc tinh
    private final String id;
    private List<Customer> customers;
    private List<Customer> customerList= CustomerDao.list();;

    //khoi tao constructor
    public Bank() {
        this.id = String.valueOf(UUID.randomUUID());
        this.customers = new ArrayList<>();
    }

    //----------------begin setter and getter ---------------

    public String getId() {
        return id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    //----------------end setter and getter -----------------

    //them khach hang
    public void addCustomer(Customer newCustomer) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(newCustomer.getCustomerId())) {
                System.out.println("Khach hang da ton tai");
                return;
            }
        }
        customers.add(newCustomer);
    }

    // them tai khoan cho KH
    public void addAccount(String customerId, Account newAccount) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                customer.addAccount(newAccount);
            }
        }

    }

    //kiem tra xem Matk da ton tai hay chua
    public boolean isValidAccount(String maTK) {
        for (Customer customer : customers) {
            if (customer.isAccountExisted(maTK))
                return true;
        }
        return false;
    }

    //kiem tra CCCD co hop le
    public boolean validateCustomerId(String soCCCD) {
        return User.isValidCustomerId(soCCCD);

    }

    //kiem tra khach hang da ton tai tren he thong chua?
    public boolean isCustomerExisted(String soCCCD) {
        for (Customer c : customers) {
            if (c.getCustomerId().equals(soCCCD)) {
                return true;
            }
        }
        return false;
    }

    // tim kiem khach hang
    public Customer findCustomer(String soCCCD) {
        for (Customer c : customers) {
            if (isCustomerExisted(soCCCD)) {
                return c;
            }
        }
        return null;
    }

    // tim kiem khach hang bang so CCCD
    public void searchCustomerByCCCD(String soCCCD) {
        boolean found = false;
        for (int i = 0; i < getCustomers().size(); i++) {
            if (getCustomers().get(i).getCustomerId().equals(soCCCD)) {//kiem tra CustomerID i trong chuoi customer
                found = true;   //tra ve true neu tim thay
                getCustomers().get(i).displayInformation(); //hien thi thong tin phan tu i
            }
        }
        if (!found) {
            System.out.println("khong tim thay khach hang voi so CCCD la " + soCCCD);
        }
    }

    // tim kiem khach hang qua ten
    public void searchCustomerByName(String name) {
        boolean found = false;
        for (int i = 0; i < getCustomers().size(); i++) {
            if (getCustomers().get(i).getName().toLowerCase().contains(name.toLowerCase())) {//kiem tra CustomerID i trong chuoi customer
                found = true;   //tra ve true neu tim thay
                getCustomers().get(i).displayInformation();//hien thi thong tin phan tu i
            }
        }
        if (!found) {
            System.out.println("Khong tim thay khach hang: " + name);
        }
    }


}
