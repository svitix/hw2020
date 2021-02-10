package com.svitix.hw2020;

import com.sun.management.GarbageCollectionNotificationInfo;
import com.svitix.hw2020.gc.MemoryLeak;
import com.svitix.hw2020.gc.MemoryLeakMBean;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/*

-Xms4096m -Xmx4096m -XX:+UseG1GC

-Xms4096m -Xmx4096m -XX:+UseParallelGC


 */
public class MemoryLeakApp {

    public static void main(String... args) throws Exception {
        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());

        Statistics stat = Statistics.getInstance();
        switchOnMonitoring();

        long beginTime = System.currentTimeMillis();
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.svitix:type=MemoryLeak");

        MemoryLeakMBean memoryLeakMBean = new MemoryLeak(10_000);
        mbs.registerMBean(memoryLeakMBean, name);
        //memoryLeakMBean.setLimit(100_000);
        memoryLeakMBean.setLimit(50_000);
        memoryLeakMBean.run();

        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);

        System.out.println(stat.getStatistics());


    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        Statistics stat = Statistics.getInstance();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    stat.addTime(duration);
                    stat.incrementCountRunGc();


                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
