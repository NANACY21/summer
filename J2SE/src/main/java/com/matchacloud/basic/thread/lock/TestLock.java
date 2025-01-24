package com.matchacloud.basic.thread.lock;

public class TestLock {

    private String str1 = "message1";
    private String str2 = "message2";

    public void test() {
        new Thread(() -> {
            synchronized (str1) {
                while (true) {
                    System.out.println("str1:" + str1);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (str2) {
                        System.out.println("str2:" + str2);
                    }
                }

            }
        }).start();
        new Thread(() -> {
            while (true) {
                synchronized (str2) {
                    System.out.println("str2:" + str2);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (str1) {
                        System.out.println("str1:" + str1);
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        new TestLock().test();
    }
}
