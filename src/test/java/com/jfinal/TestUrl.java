package com.jfinal;

import java.net.URL;

public class TestUrl {
    public static void main(String[] args) {
        URL url = TestUrl.class.getClassLoader().getSystemResource("templet/upload/person.xlsx");//
        System.out.println(url.getPath());

    }
}
