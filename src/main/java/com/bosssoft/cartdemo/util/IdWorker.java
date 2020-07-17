package com.bosssoft.cartdemo.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author hujierong
 * @date 2020-7-14
 * 雪花算法
 */
@Slf4j
public class IdWorker {

    /**
     * 5位工作Id
     */
    private long workerId;

    /**
     * 5位数据中心Id
     */
    private long datacenterId;

    /**
     * 12位序列号
     */
    private long sequence;

    /**
     * 初始时间戳
     */
    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;

    private long datacenterIdBits = 5L;

    /**
     * 最大工作Id
     */
    private long maxWorkerId = ~(-1L << workerIdBits);

    /**
     * 最大数据中心Id
     */
    private long maxDatacenterId = ~(-1L << datacenterIdBits);

    private long sequenceBits = 12L;

    /**
     * 序列号最大值
     */
    private long sequenceMask = ~(-1L << sequenceBits);

    /**
     * 工作Id需要左移的位数
     */
    private long workerIdShift = sequenceBits;

    /**
     * 数据中心Id需要左移的位数
     */
    private long datacenterIdShift = sequenceBits + workerIdBits;

    /**
     * 时间戳需要左移的位数
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 上次时间戳，初始为负数
     */
    private long lastTimestamp = -1L;

    public IdWorker (long workerId, long datacenterId, long sequence) {
        // 检查工作Id
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",maxWorkerId));
        }
        // 检查数据中心Id
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",maxDatacenterId));
        }
        log.debug("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid {}",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    /**
     * 返回当前时间戳
     * @return 时间戳
     */
    public long getTimestamp () {
        return System.currentTimeMillis();
    }

    private long tilNextMills (long lastTimestamp) {
        long timestamp = getTimestamp();
        while (timestamp < lastTimestamp) {
            timestamp = getTimestamp();
        }
        return timestamp;
    }

    /**
     * Id生成算法
     * @return Id
     */
    public synchronized long nextId () {
        long timestamp = getTimestamp();

        // 获取当前时间戳，如果小于上次时间戳，则系统时间获取出现问题
        if (timestamp < lastTimestamp) {
            log.debug("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 获取当前时间戳，如果等于上次时间戳，序列号加1，序列号达到最大值则等待下个时间戳，否则序列号为0
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0 ) {
                timestamp = tilNextMills(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        // 更新上次时间戳
        lastTimestamp = timestamp;

        /*
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为各部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }
}
