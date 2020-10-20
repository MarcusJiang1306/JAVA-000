public class Demo {

    public static void main(String[] args) {
        int a = 3;
        int b = 5;
        int c = b-a;
        int d = c * b;
        int e = d / c;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                continue;
            }else System.out.println(i);
        }
    }
}
