package demo.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author: tongkeyon@163.com
 * @description:
 **/
public class SingletonTest {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        StarvingType3 instance1 = StarvingType3.getInstance();
        StarvingType3 instance2 = StarvingType3.getInstance();
        System.out.println(instance1 ==instance2);

        Constructor<StarvingType3> con = StarvingType3.class.getDeclaredConstructor();
        con.setAccessible(true);
        StarvingType3 starvingType1 = con.newInstance();
        System.out.println(instance1 == starvingType1);
    }
}
