package com.github.yuyenews.concurrent.test;

import com.github.yuyenews.concurrent.MagicianConcurrent;


public class TaskTest {

    public static void main(String[] args) throws Exception {

        TestProducerOne producerOne = new TestProducerOne();
        TestProducerTwo producerTwo = new TestProducerTwo();

        TestConsumerOne one = new TestConsumerOne();
        TestConsumerTwo two = new TestConsumerTwo();
        TestConsumerThree three = new TestConsumerThree();

        MagicianConcurrent.getJobManager()
                .addProducer(producerOne)
                .addProducer(producerTwo)
                .addConsumer(one)
                .addConsumer(two)
                .addConsumer(three)
                .start();

        new Thread(()->{
            while (true){

                try {
                    System.out.println("taskCount1, 1p:"+one.getProducerTaskCount(producerOne.getId()) +", 2p:"+one.getProducerTaskCount(producerTwo.getId()));
                    System.out.println("taskCount2, 1p:"+two.getProducerTaskCount(producerOne.getId()) +", 2p:"+two.getProducerTaskCount(producerTwo.getId()));
                    System.out.println("taskCount3, 1p:"+three.getProducerTaskCount(producerOne.getId()) +", 2p:"+three.getProducerTaskCount(producerTwo.getId()));
                    System.out.println("----------------------------------------");
                    Thread.sleep(1000);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();

        while (true){
            Thread.sleep(10000000000L);
        }
    }
}
