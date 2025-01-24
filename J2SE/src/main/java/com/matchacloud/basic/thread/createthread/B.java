package com.matchacloud.basic.thread.createthread;

public class B extends Thread{
    public void run(){
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            System.out.println("B done");
        }
    }
}
