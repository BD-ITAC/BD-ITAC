package br.ita.bditac.mobile.alertas;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationIdGenerator {

    private final static AtomicInteger id = new AtomicInteger(0);

    private static NotificationIdGenerator ourInstance = new NotificationIdGenerator();

    private NotificationIdGenerator() {
    }

    public static int getNewID() {
        return id.incrementAndGet();
    }

}
