package com.dev.model.schedule;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TaskSchedulerManager {

    private final ScheduledExecutorService scheduler;
    private final AtomicInteger taskIdCounter;
    private final Map<String,Map<Integer, ScheduledFuture<?>>> typeMap;

    public TaskSchedulerManager() {
        this.scheduler = Executors.newScheduledThreadPool(
                2, ThreadFactoryBuilder.create().setNamePrefix("scheduledPool-").build());
        this.taskIdCounter = new AtomicInteger(0);
        this.typeMap = new HashMap<>(){
            {
                put("typeA",new ConcurrentHashMap<>());
                put("typeB",new ConcurrentHashMap<>());
                put("typeC",new ConcurrentHashMap<>());
            }
        };
    }

    /**
     * 提交任务
     * @param targetTime 指定的执行时间 (格式: "yyyy-MM-dd HH:mm:ss")
     * @param task 执行的任务
     * @return 任务ID
     */
    public int scheduleTask(String type, LocalDateTime targetTime, Runnable task) {
        long delay = getDelayUntilTargetTime(targetTime);
        int taskId = taskIdCounter.incrementAndGet();

        ScheduledFuture<?> future = scheduler.schedule(() -> {
            try {
                task.run();
            } finally {
                Map<Integer, ScheduledFuture<?>> tasksMap = typeMap.get(type);
                tasksMap.remove(taskId); // 执行完毕后自动移除任务
            }
        }, delay, TimeUnit.MILLISECONDS);
        Map<Integer, ScheduledFuture<?>> tasksMap = typeMap.get(type);
        tasksMap.put(taskId, future);
        return taskId;
    }

    /**
     * 取消任务
     * @param taskId 任务ID
     * @return 是否成功取消
     */
    public boolean cancelTask(String type, int taskId) {
        Map<Integer, ScheduledFuture<?>> tasksMap = typeMap.get(type);
        ScheduledFuture<?> future = tasksMap.get(taskId);
        if (future != null) {
            boolean cancelled = future.cancel(false);
            if (cancelled) {
                tasksMap.remove(taskId);
            }
            return cancelled;
        }
        return false;
    }

    /**
     * 获取所有任务ID列表
     * @return 任务ID列表
     */
    public List<Integer> getAllTaskIds(String type) {
        Map<Integer, ScheduledFuture<?>> tasksMap = typeMap.get(type);
        return new ArrayList<>(tasksMap.keySet());
    }

    /**
     * 检查任务是否存在
     * @param taskId 任务ID
     * @return 是否存在
     */
    public boolean isTaskScheduled(String type, int taskId) {
        Map<Integer, ScheduledFuture<?>> tasksMap = typeMap.get(type);
        return tasksMap.containsKey(taskId);
    }

    /**
     * 获取指定任务的剩余时间（毫秒）
     * @param taskId 任务ID
     * @return 剩余时间或 -1（如果任务不存在或已完成）
     */
    public long getRemainingTime(String type, int taskId) {
        Map<Integer, ScheduledFuture<?>> tasksMap = typeMap.get(type);
        ScheduledFuture<?> future = tasksMap.get(taskId);
        if (future != null) {
            return future.getDelay(TimeUnit.MILLISECONDS);
        }
        return -1;
    }

    /**
     * 优雅关闭线程池
     */
    @PreDestroy
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }

    /**
     * 计算目标时间与当前时间的时间差
     * @param targetTime 指定的时间
     * @return 毫秒差值
     */
    private long getDelayUntilTargetTime(LocalDateTime targetTime) {
        LocalDateTime now = LocalDateTime.now();
        return Duration.between(now, targetTime).toMillis();
    }
}

