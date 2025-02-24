package com.example.appbanhang.ulttil;

import com.example.appbanhang.model.GioHang;

import java.util.List;

public class Server {
    public static String localhost = "192.168.100.37:8080";
    public static String duongdanloaisp = "http://"+localhost+"/appbanhang/getloaisp.php";
    public static String duongdanspmoi  = "http://"+localhost+"/appbanhang/sanphammoi.php";
    public static String duongdandienthoai = "http://"+localhost+"/appbanhang/getsanpham.php?page=";
    public static String duongdanlaptop= "http://"+localhost+"/appbanhang/getsanpham.php?page=";
}
