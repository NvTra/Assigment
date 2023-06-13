package vn.funix.fx22252.java.asm04.dao;

import vn.funix.fx22252.java.asm04.common.Account;
import vn.funix.fx22252.java.asm04.service.BinaryFileService;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountDao {
    private final static String FILE_PATH = "store/accounts.dat";
    private static final int MAX_THREAD = 4; // số lượng thread tối đa để sử dụng

    public static void save(List<Account> accounts) {
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }

    public static List<Account> list() {
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Account editAccount) {
        List<Account> accounts = list();
        boolean hasExits = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
        List<Account> updateAccounts;
        updateAccounts = new ArrayList<>();
        if (!hasExits) {
            updateAccounts.add(editAccount);
        } else {
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

    public static void update2(Account editAccount) {
        // Tạo ExecutorService để sử dụng multi-thread
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);
        List<Account> accounts = list();
        boolean hasExits = accounts.stream()
                .anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
        List<Account> updateAccounts;
        updateAccounts = new ArrayList<>();
        if (!hasExits) {
            updateAccounts.add(editAccount);
        } else {
            executorService.execute(() -> {
                    for (Account account : AccountDao.list()) {
                        if (account.getAccountNumber().equals(editAccount.getAccountNumber())) {
                            updateAccounts.add(editAccount);
                        } else {
                            updateAccounts.add(account);
                        }
                    }

            });
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            Thread.yield();
        }
        save(updateAccounts);
    }


}
