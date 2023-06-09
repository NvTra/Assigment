package vn.funix.fx22252.java.asm01;

import java.util.Random;
import java.util.Scanner;

public class Asm01 {
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rd = new Random();

    public static void main(String[] args) {

        System.out.println("+----------+-------------------+-------+");
        System.out.println("| NGAN HANG SO | FX22252@1.0.0         |");
        System.out.println("+----------+-------------------+-------+");
        System.out.println("| 1. Nhap CCCD                         |");
        System.out.println("| 0. Thoat                             |");
        System.out.println("+----------+-------------------+-------+");
        System.out.print("Chuc nang: ");

        luaChonSuDungChuongTrinh();
        sc.nextLine();
        //che do bao mat
        int luachon = 0;
        do {
            try {
                System.out.println("Chon kieu bao mat:");
                System.out.println("\t 1. EASY (Bao mat bang so)");
                System.out.println("\t 2. HARD (Bao mat bang so + chu)");
                System.out.println("\t 0. Thoat");
                System.out.print("Chuc nang:");
                luachon = sc.nextInt();
                if (luachon == 0) {
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    System.exit(0);
                } else {
                    if (luachon == 1) {
                        baoMatSo();
                        break;
                    } else if (luachon == 2) {
                        baoMatchu();
                        break;
                    } else {
                        System.out.println("Khong hop le. Vui long chon lai!");
                    }
                }

            } catch (Exception e) {
                System.out.println("Khong hop le. Vui long chon lai!");
                sc.nextLine();
            }
        } while (luachon != -1);
        // nhap va su dung so cccd
        String soCCCD = kiemTraCCCD();
        //tach so CCCD de kiem tra
        int soTraNoisinh = Integer.parseInt(soCCCD.substring(0, 3));
        int soTraGioiTinh = Integer.parseInt(soCCCD.substring(3, 4));
        int soTraNamSinh = Integer.parseInt(soCCCD.substring(4, 6));
        String soNgauNhien = soCCCD.substring(6, 12);

        traCuu(soTraNoisinh, soTraGioiTinh, soTraNamSinh, soNgauNhien);
    }


    public static void luaChonSuDungChuongTrinh() {

        int luachon = 0;

        do {
            try {
                luachon = sc.nextInt();
                if (luachon == 0) {
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    System.exit(0);
                    break;
                } else if (luachon == 1) {
                    break;
                } else {
                    System.out.println("Khong hop le. Vui long chon lai!");
                    sc.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Khong hop le. Vui long chon lai!");
                sc.nextLine();
            }

        } while (luachon != -1);
    }

    //tao ma bi mat bang so
    public static void baoMatSo() {
        int soBaoMatNgauNhien = rd.nextInt(900) + 100;
        System.out.println("Nhap ma xac thuc: " + soBaoMatNgauNhien);
        int nhapSoBaoMat;
        do {
            try {
                nhapSoBaoMat = sc.nextInt();
                if (soBaoMatNgauNhien == nhapSoBaoMat) {
                    System.out.print("Vui long nhap so CCCD: ");
                    sc.nextLine();
                    break;
                } else {
                    System.out.println("Ma bao mat khong dung. Vui Long thu lai");
                    sc.nextLine();
                }
            } catch (Exception e) {
                System.out.println("Ma bao mat khong dung. Vui Long thu lai");
                sc.nextLine();
            }
        } while (true);
    }

    //tao so bi mat bao gom ca so va chu
    public static void baoMatchu() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        String maBaoMat = "";
        for (int sbm = 0; sbm < 6; sbm++) {
            sb.append(CHARACTERS.charAt(rd.nextInt(CHARACTERS.length())));
            maBaoMat = sb.toString();
        }
        System.out.println("Nhap ma xac thuc: " + maBaoMat);
        sc.nextLine();
        while (true) {

            String nhapMaBaoMat = sc.nextLine();
            if (nhapMaBaoMat.equals(maBaoMat)) {
                System.out.print("Vui long nhap so CCCD: ");
                break;
            } else {
                System.out.println("Ma bao mat khong dung. Vui Long thu lai");
            }
        }

    }

    //kiem tra 3 so dau CCCD co nam trong danh sach ma tinh
    public static boolean traSoatMaTinh(String soCCCD) {
        int[] maTinh = {1, 2, 4, 6, 8, 10, 11, 12, 14, 15, 17, 19, 20, 22, 24, 25, 26, 27, 30, 31, 33, 34, 35, 36, 37, 38, 40, 42, 44, 45, 46, 48, 49, 51, 52, 54, 56, 58, 60, 62, 64, 66, 67, 68, 70, 72, 74, 75, 77, 79, 80, 82, 83, 84, 86, 87, 89, 91, 92, 93, 94, 95, 96
        };
        for (int n : maTinh) {
            if (Integer.parseInt(soCCCD.substring(0, 3)) == n) {
                return true;
            }
        }
        return false;
    }

    // kiem tra chuoi CCCD có phai la dang so
    public static boolean isNotNumeric(String soCCCD) {
        return !soCCCD.chars().allMatch(Character::isDigit);
    }
    //phuong thuc kiem tra tinh hop le cua so CCCD va tra ve so CCCD
    public static String kiemTraCCCD() {
        String soCCCD;
        do {
            soCCCD = sc.nextLine();
            if (soCCCD.equals("No")) {
                System.exit(0);
            }
            if (soCCCD.length() != 12 || isNotNumeric(soCCCD) || !traSoatMaTinh(soCCCD)) {
                System.out.println("So CCCD khong hop le.");
                System.out.println("Vui long nhap lai hoac 'No' de thoat:");
            }
        } while (soCCCD.length() != 12 || isNotNumeric(soCCCD) || !traSoatMaTinh(soCCCD));
        return soCCCD;

    }
    //phuong thuc lua chon de hien thi thong tin tu so CCCD
    public static void traCuu(int soTraNoiSinh, int soTraGioiTinh, int soTraNamSinh, String soTraNgauNhien) {
        int luachon = 0;
        do {
            try {
                System.out.println("\t| 1. Kiem tra noi sinh");
                System.out.println("\t| 2. Kiem tra tuoi, gioi tinh");
                System.out.println("\t| 3. Kiem tra so ngau nhien");
                System.out.println("\t| 0. Thoat");
                System.out.print("Chuc nang: ");
                luachon = sc.nextInt();
                if (luachon == 0) {
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    System.exit(0);
                    break;
                } else {
                    if (luachon == 1) {
                        noiSinh(soTraNoiSinh);
                    } else if (luachon == 2) {
                        gioiTinhNamSinh(soTraGioiTinh, soTraNamSinh);
                    } else if (luachon == 3) {
                        System.out.println("So ngau nhien: " + soTraNgauNhien);
                    } else {
                        System.out.println("Khong hop le. Vui long chon lai");
                    }
                }
            } catch (Exception e) {
                System.out.println("Khong hop le. Vui long chon lai");
                sc.nextLine();
            }
        } while (luachon != -1);
    }

    // tra cuu gioi tinh nam sinh
    public static void gioiTinhNamSinh(int soGioiTinh, int soNamSinh) {
        String gioiTinh;
        if (soGioiTinh % 2 == 0) {
            gioiTinh = "Nam";
        } else {
            gioiTinh = "Nu";
        }
        switch (soGioiTinh) {
            case 0:
            case 1:
                System.out.println("Gioi tinh: " + gioiTinh + " | 19" + soNamSinh);
                break;
            case 2:
            case 3:
                System.out.println("Gioi tinh: " + gioiTinh + " | 20" + soNamSinh);
                break;
            case 4:
            case 5:
                System.out.println("Gioi tinh: " + gioiTinh + " | 21" + soNamSinh);
                break;
            case 6:
            case 7:
                System.out.println("Gioi tinh: " + gioiTinh + " | 22" + soNamSinh);
                break;
            case 8:
            case 9:
                System.out.println("Gioi tinh: " + gioiTinh + " | 23" + soNamSinh);
                break;
            default:
                System.out.println(" ");
                break;
        }
    }

    // tra cuu noi sinh
    public static void noiSinh(int soNoiSinh) {
        switch (soNoiSinh) {
            case 1:
                System.out.println("Noi Sinh: Ha Noi");
                break;
            case 2:
                System.out.println("Noi Sinh: Ha Giang");
                break;
            case 4:
                System.out.println("Noi Sinh: Cao Bang");
                break;
            case 6:
                System.out.println("Noi Sinh: Bac Kan");
                break;
            case 8:
                System.out.println("Noi Sinh: Tuyen Quang");
                break;
            case 10:
                System.out.println("Noi Sinh: Lao Cai");
                break;
            case 11:
                System.out.println("Noi Sinh: Dien Bien");
                break;
            case 12:
                System.out.println("Noi Sinh: Lai Chau");
                break;
            case 14:
                System.out.println("Noi Sinh: Son La");
                break;
            case 15:
                System.out.println("Noi Sinh: Yen Bai");
                break;
            case 17:
                System.out.println("Noi Sinh: Hoa Binh");
                break;
            case 19:
                System.out.println("Noi Sinh: Thai Nguyen");
                break;
            case 20:
                System.out.println("Noi Sinh: Lang Son");
                break;
            case 22:
                System.out.println("Noi Sinh: Quang Ninh");
                break;
            case 24:
                System.out.println("Noi Sinh: Bac Giang");
                break;
            case 25:
                System.out.println("Noi Sinh: Phu Tho");
                break;
            case 26:
                System.out.println("Noi Sinh: Vinh Phuc");
                break;
            case 27:
                System.out.println("Noi Sinh: Bac Ninh");
                break;
            case 30:
                System.out.println("Noi Sinh: Hai Duong");
                break;
            case 31:
                System.out.println("Noi Sinh: Hai Phong");
                break;
            case 33:
                System.out.println("Noi Sinh: Hung Yen");
                break;
            case 34:
                System.out.println("Noi Sinh: Thai Binh");
                break;
            case 35:
                System.out.println("Noi Sinh: Ha Nam");
                break;
            case 36:
                System.out.println("Noi Sinh: Nam Dinh");
                break;
            case 37:
                System.out.println("Noi Sinh: Ninh Binh");
                break;
            case 38:
                System.out.println("Noi Sinh: Thanh Hoa");
                break;
            case 40:
                System.out.println("Noi Sinh: Nghe An");
                break;
            case 42:
                System.out.println("Noi Sinh: Ha Tinh");
                break;
            case 44:
                System.out.println("Noi Sinh: Quang Binh");
                break;
            case 45:
                System.out.println("Noi Sinh: Quang Tri");
                break;
            case 46:
                System.out.println("Noi Sinh: Thua Thien Hue");
                break;
            case 48:
                System.out.println("Noi Sinh: Da Nang");
                break;
            case 49:
                System.out.println("Noi Sinh: Quang Nam");
                break;
            case 51:
                System.out.println("Noi Sinh: Quang Ngai");
                break;
            case 52:
                System.out.println("Noi Sinh: Binh Đinh");
                break;
            case 54:
                System.out.println("Noi Sinh: Phu Yen");
                break;
            case 56:
                System.out.println("Noi Sinh: Khanh Hoa");
                break;
            case 58:
                System.out.println("Noi Sinh: Ninh Thuan");
                break;
            case 60:
                System.out.println("Noi Sinh: Binh Thuan");
                break;
            case 62:
                System.out.println("Noi Sinh: Kon Tum");
                break;
            case 64:
                System.out.println("Noi Sinh: Gia Lai");
                break;
            case 66:
                System.out.println("Noi Sinh: Dak Lak");
                break;
            case 67:
                System.out.println("Noi Sinh: Dak Nong");
                break;
            case 68:
                System.out.println("Noi Sinh: Lam Dong");
                break;
            case 70:
                System.out.println("Noi Sinh: Binh Phuoc");
                break;
            case 72:
                System.out.println("Noi Sinh: Tay Ninh");
                break;
            case 74:
                System.out.println("Noi Sinh: Binh Duong");
                break;
            case 75:
                System.out.println("Noi Sinh: Đong Nai");
                break;
            case 77:
                System.out.println("Noi Sinh: Ba Ria - Vung Tau");
                break;
            case 79:
                System.out.println("Noi Sinh: Ho Chi Minh");
                break;
            case 80:
                System.out.println("Noi Sinh: Long An");
                break;
            case 82:
                System.out.println("Noi Sinh: Tien Giang");
                break;
            case 83:
                System.out.println("Noi Sinh: Ben Tre");
                break;
            case 84:
                System.out.println("Noi Sinh: Tra Vinh");
                break;
            case 86:
                System.out.println("Noi Sinh: Vinh Long");
                break;
            case 87:
                System.out.println("Noi Sinh: Dong Thap");
                break;
            case 89:
                System.out.println("Noi Sinh: An Giang");
                break;
            case 91:
                System.out.println("Noi Sinh: Kien Giang");
                break;
            case 92:
                System.out.println("Noi Sinh: Can Tho");
                break;
            case 93:
                System.out.println("Noi Sinh: Hau Giang");
                break;
            case 94:
                System.out.println("Noi Sinh: Soc Trang");
                break;
            case 95:
                System.out.println("Noi Sinh: Bac Lieu");
                break;
            case 96:
                System.out.println("Noi Sinh: Ca Mau");
                break;
            default:
                System.out.println(" ");

        }

    }

}
