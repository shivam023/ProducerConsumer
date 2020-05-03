package com.shivams.pc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProducerConsumer{
    public static class PC{
        LinkedList<Integer> resource = new LinkedList<Integer>();
        int capacity = 2;

        public void produce() throws InterruptedException {
            int val = 1;
            while (true){
                synchronized (this) {
                    while (resource.size() == capacity)
                        wait();

                    System.out.println("Resource produced: " + val);
                    resource.add(val++);
                    notify();

                    Thread.sleep(4000);
                }
            }
        }

        public void consume() throws InterruptedException {
            while (true){
                synchronized (this) {
                    while (resource.size() == 0)
                        wait();

                    int val = resource.removeFirst();
                    System.out.println("Resource consumed: " + val);
                    notify();

                    Thread.sleep(4000);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        final PC pc = new PC();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}