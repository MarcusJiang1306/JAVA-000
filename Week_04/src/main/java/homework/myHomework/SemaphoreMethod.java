package homework.myHomework;

import java.util.concurrent.Semaphore;

public class SemaphoreMethod {

    private int sum;
    final Semaphore semaphore = new Semaphore(1);

    private void setSum() {
        try {
            semaphore.acquire();
            this.sum = fibo(36);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphore.release();
        }

    }

    private void printResult(long stratTime) {

        try {
            semaphore.acquire();
            // 确保  拿到result 并输出
            int result = sum;
            System.out.println("异步计算结果为：" + result);
            System.out.println("使用时间："+ (System.currentTimeMillis()-stratTime) + " ms");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        SemaphoreMethod semaphoreMethod = new SemaphoreMethod();

        new Thread(semaphoreMethod::setSum).start();
        new Thread(() -> semaphoreMethod.printResult(start)).start();

        // 然后退出main线程
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
