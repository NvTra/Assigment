package vn.funix.fx22252.java.asm04.dao;

import vn.funix.fx22252.java.asm02.models.Customer;
import vn.funix.fx22252.java.asm04.service.BinaryFileService;


import java.io.*;
import java.util.List;

public class CustomerDao {
    private final static String FILE_PATH = "store/customers.dat";

    public static void save(List<Customer> customers) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, customers);
    }

    public static List<Customer> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }


}
