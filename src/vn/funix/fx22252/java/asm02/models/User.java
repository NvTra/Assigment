package vn.funix.fx22252.java.asm02.models;

import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable {
    //khai bao thuoc tinh
    private final long serialVersionUID = 2L;
    private String name;
    private String customerId;

    //khoi tao constructor
    public User() {
        this.name = name;
        this.customerId = customerId;
    }

    public User(String name, String customerId) {
        this.name = name;
        setCustomerId(customerId);
    }
    //----------------begin setter and getter ---------------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        try {
            if (isValidCustomerId(customerId)) {
                this.customerId = customerId;
            }
        } catch (Exception e) {
            System.out.println("So CCCD khong hop le");
        }
    }

    //----------------end setter and getter -----------------

    // phuong thuc kiem tra tinh hop le cua so CCCD
    public static boolean isValidCustomerId(String customerId) {
        return (isNumberic(customerId) && traSoatMaTinh(customerId));
    }

    public static boolean traSoatMaTinh(String customerId) {
        //kiem tra xem so CCCD co nam trong danh sach ma tinh
        int[] maTinh = {1, 2, 4, 6, 8, 10, 11, 12, 14, 15, 17, 19, 20, 22, 24, 25, 26, 27, 30, 31, 33, 34, 35, 36, 37, 38, 40, 42, 44, 45, 46, 48, 49, 51, 52, 54, 56, 58, 60, 62, 64, 66, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 83, 84, 86, 87, 89, 91, 92, 93, 94, 95, 96
        };
        for (int n : maTinh) {
            if (Integer.parseInt(customerId.substring(0, 3)) == n) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumberic(String customerId) {
        //kiem tra CCCD co la so co 12 chu so
        Pattern pt = Pattern.compile("^\\d{12}$");
        return pt.matcher(customerId).find();
    }

}
