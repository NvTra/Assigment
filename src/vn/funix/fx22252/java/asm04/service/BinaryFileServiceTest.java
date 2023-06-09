package vn.funix.fx22252.java.asm04.service;

import org.junit.Test;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class BinaryFileServiceTest {

    @Test
    public void readFile() {

        BinaryFileService bt = new BinaryFileService();
        bt.readFile("store/customers.dat");
        bt.readFile("store/accounts.dat");
    }
}