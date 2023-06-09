package vn.funix.fx22252.java.asm04.dao;

import vn.funix.fx22252.java.asm02.models.Account;
import vn.funix.fx22252.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final static String FILE_PATH = "store/accounts.dat";

    public static void save(List<Account> accounts) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }

    public static List<Account> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Account editAccount) throws IOException {
        List<Account> accounts = list();
        boolean hasExits = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
        List<Account> updateAccounts;
        if (!hasExits) {
            updateAccounts = new ArrayList<>();
            updateAccounts.add(editAccount);
        } else {
            updateAccounts = new ArrayList<>();
            for (Account account : accounts) {
                if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
                    updateAccounts.add(editAccount);
                } else {
                    updateAccounts.add(account);
                }
            }
        }
        save(updateAccounts);
    }
}
