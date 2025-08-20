/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {

        // TODO#1 Thread-1 가 resource1의 접근 권한을 획득하기 위해 대기 합니다.
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                log.debug("{}: locked resource 1", Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        });
        thread1.setName("Thread-1");

        // TODO#2 Thread-2 가 resource1의 접근 권한을 획득한 상태에서 resource2의 접근 권한을 대기하고 있습니다.
        Thread thread2 = new Thread(() -> {
            synchronized (resource1) { // resource1을 먼저 잠금
                log.debug("{}: locked resource 1", Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }

                synchronized (resource2) {
                    log.debug("{}: locked resource 2", Thread.currentThread().getName());
                }
            }
        });
        thread2.setName("Thread-2");

        // TODO#3 Thread-3 가 resource2의 접근 권한을 획득합니다.
        Thread thread3 = new Thread(() -> {
            synchronized (resource2) { // resource2을 먼저 잠금
                log.debug("{}: locked resource 2", Thread.currentThread().getName());

                try {
                    while (true) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        });
        thread3.setName("Thread-3");

        //TODO [insub] th3(re2) 잠금 TIMED-WAITING -> th2(re1) 잠금그고 re2 탈취 시도 but Blocked -> th1은 re1탈취 시도 but Blocked...
        try {
            thread3.start();
            Thread.sleep(1000);
            thread2.start();
            Thread.sleep(1000);
            thread1.start();
        } catch (InterruptedException e) {
            log.debug(e.getMessage());
        }

        System.out.println(thread3.getState());
        System.out.println(thread2.getState());
        System.out.println(thread1.getState());
    }

}
