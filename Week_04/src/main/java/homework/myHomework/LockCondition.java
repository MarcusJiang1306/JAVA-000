package homework.myHomework;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class LockCondition {
    private Lock lock ;
    private Condition condition;
    private int result = 0;

    public LockCondition() {
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public Condition getCondition() {
        return condition;
    }

    public Lock getLock() {
        return lock;
    }

    public static void main(String[] args) throws InterruptedException {
        LockCondition lockCondition = new LockCondition();

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        Lock lock = lockCondition.getLock();

        new Thread(() -> {
            lock.lock();
            try {
                lockCondition.getCondition().await();
                int result = lockCondition.result;
                // 确保  拿到result 并输出
                System.out.println("异步计算结果为：" + result);

                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }).start();
        TimeUnit.SECONDS.sleep(1);

        lockCondition.getResult();

        // 然后退出main线程
    }

    private void getResult() {
        lock.lock();
        try {
            result = sum();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private int sum() {
        return fibo(36);
    }

    public int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
