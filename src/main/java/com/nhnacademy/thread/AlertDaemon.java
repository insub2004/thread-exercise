package com.nhnacademy.thread;

public class AlertDaemon extends Thread {

    public AlertDaemon() {
        //TODO#1 - setDaemon() 메서드를 이용해서 daemon thread로 설정

        //TODO#2 - Thread 이름을 alert-daemon으로 설정


        // ShutdownHook: JVM이 종료되기 직전에 실행되는 Thread
        // 프로그램의 정상/비정상 종료 시 필요한 리소스 정리나 로그 기록 등을 처리하며,
        // 예기치 않은 종료 상황에서도 안전한 종료를 보장합니다.
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    //TODO#3 AlertDaemon Thread가 종료 시점에 적절한 메시지를 출력합니다.
                })
        );
    }

    @Override
    public void run() {
        //TODO#4 1초에 한 번씩 Alert Daemon message를 출력 합니다.
    }

}
