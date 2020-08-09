package com.rytispetrauskas;

import java.util.ArrayList;

public class Main extends Thread{

    private static Array array = new Array();

    public static void main(String[] args)  throws InterruptedException {

        int intervalStart = 1;
        int intervalEnd = 10000;


        double time = Iteration(1, intervalStart, intervalEnd);
        System.out.println("1 1.0 " + time);
        array = new Array();
        for(int i = 2; i <= 12; i++){
            double timee = Iteration(i, intervalStart, intervalEnd);
            array = new Array();
            System.out.println(i + " " + time/timee + " " + timee);
        }
    }

    private static double Iteration(int threadNum, int intervalStart, int intervalEnd) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        final int threadSpace = (threadNum)*100;
        ArrayList<Thread> threads = new ArrayList<>();
        threads.add(new Thread(() -> CollatzIteration(intervalEnd,intervalStart, threadSpace)));
        threads.get(0).start();
        for(int i = 1; i < threadNum; i++){
            int finalI = i;
            threads.add(new Thread(() -> CollatzIteration(intervalEnd,100* finalI +1, threadSpace)));
            threads.get(i).start();
        }

        for (int i = 0; i < threadNum; i++)
        {
            threads.get(i).join();

        }

        ArrayList<ArrayList<Integer>> largestArray = array.getValue();
        int largest = largestArray.get(0).get(1);
        int counter = largestArray.get(0).get(0);
        for(int i = 1; i < threadNum-1; i++){
            if(largestArray.get(i).get(1) > largest){
                largest = largestArray.get(i).get(1);
                counter = largestArray.get(i).get(0);
            }
        }
        long endTime = System.currentTimeMillis();
        double completionTime = (endTime-startTime)/1000.;
        return completionTime;
    }

    private static void CollatzIteration(int intervalEnd, int threadStart, int threadSpace) {
        long subject = threadStart;
        long subjectCopy = 0;
        long subjectStart = threadStart;
        int largest;
        int counter = 0;
        int counter1 = 0;
        largest = threadStart;
        while (subject < intervalEnd) {
            while (subject != 1) {
                if (subject % 2 == 0) {
                    subject /= 2;
                } else {
                    subject = subject * 3 + 1;
                }
                counter++;
            }

            if (counter > counter1) {
                counter1 = counter;
                largest = Math.toIntExact(subjectCopy + subjectStart);
            }
            counter = 0;
//            System.out.println(" subject " + (subjectCopy + subjectStart) + " subjectStart " + subjectStart);
            subjectCopy++;
            if (subjectCopy == 100) {
                subjectCopy = 0;
                subjectStart = subjectStart + threadSpace;
            }
            subject = subjectCopy + subjectStart;
        }
        array.setValue(largest, counter1);
    }
}

