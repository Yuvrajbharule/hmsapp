package com.hmsapp;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class A implements Runnable{

    public static void main(String[] args) {

        A a1 = new A();
        Thread t1 = new Thread(a1);
        t1.start();

    }

    @Override
    public void run() {
       

    }
}
