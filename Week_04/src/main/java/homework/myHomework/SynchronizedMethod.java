package homework.myHomework;

public class SynchronizedMethod {

    private int sum;

    private synchronized void setSum() {
        this.sum = fibo(36);
        notify();
    }

    private synchronized int getSum() throws InterruptedException {
        wait();
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        SynchronizedMethod synchronizedMethod = new SynchronizedMethod();

        new Thread(synchronizedMethod::setSum).start();

        int result = synchronizedMethod.getSum(); //这是得到的返回值

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    public static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
