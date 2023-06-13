package vn.funix.fx22252.java.asm04.test;

import org.junit.Test;
import vn.funix.fx22252.java.asm04.dao.CustomerDao;
import vn.funix.fx22252.java.asm04.service.BinaryFileService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BinaryFileServiceTest {

    @Test
    public void readFile() {
        BinaryFileService.readFile("customer.dat");
    }

    @Test
    public void writeFile() {
        List<String> myArr =new ArrayList<>();
    BinaryFileService.writeFile("customer.dat",myArr);
    }
}