import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassloader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(HelloClassloader.class.getResource(name+".xlass").getPath());
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
            new FileInputStream(file).read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            super.findClass(name);
        }
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(~bytes[i]);
    }
        return defineClass(name, bytes, 0, bytes.length);
}

    public static void main(String[] args){

        try {
            Class<?> aClass = new HelloClassloader().findClass("Hello");
            Object object = aClass.newInstance();
            Method method = aClass.getMethod("hello");
            method.invoke(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
