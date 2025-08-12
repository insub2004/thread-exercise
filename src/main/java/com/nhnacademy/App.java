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

import com.nhnacademy.thread.CounterHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App
{
    public static void main( String[] args )
    {
        CounterHandler counterHandler = new CounterHandler(10l);
        Thread thread = new Thread(counterHandler);
        log.debug("thread-state:{}",thread.getState());

        thread.setName("my-counter");
        thread.start();
        //TODO#1 thread가 실행 후 (1-10 count 증가 후  아래 로그가 출력 됩니다.)
        //thread.join()을 호출 하면 thread가 종료될 때 까지 main thread가 대기하게 됩니다.
        try {
            thread.join();  // main 스레드가 나머지 my-counter 스레드가 종료될 때 까지 기다린다.
                            // 이때 main 스레드는 WAITING 상태가 된다.
                            // my-counter 스레드가 종료(TERMINATED) 되면
                            // main 스레드는 다시 RUNNABLE 상태가 되고 실행된다.
                            // 단점은 무한대기를 할 수 있다. -> 보완하기 위해서 join(ms)파라미터를 전달 (이 경우 main 스레드는 TIMED_WAITING 이 된다.)
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.debug("Application exit!");
        log.debug("thread-state:{}, thread-name:{}",thread.getState(), thread.getName());
    }
}