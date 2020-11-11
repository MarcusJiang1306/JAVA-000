package homework.myHomework;

import java.util.concurrent.TimeUnit;

public class ManualSpinMethod {

    private int sum;

    private void setSum() {
        this.sum = fibo(36);
    }

    private int getSum() throws InterruptedException {
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ManualSpinMethod manualSpinMethod = new ManualSpinMethod();

        new Thread(manualSpinMethod::setSum).start();

        int result; //这是得到的返回值
        while ((result = manualSpinMethod.getSum()) == 0) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
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
