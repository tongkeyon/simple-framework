package demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @program: simple-framework
 * @description:
 * @author: tongkeyon
 * @create:
 **/

@TestAnnotation(name = "Lily",nums = {1,5,3,4})
public class Main {

    @TestAnnotation(name = "Tom",nums = {1,2,3,4})
    private String name;

    public static void main(String[] args) throws NoSuchFieldException {
        Main main = new Main();
        //获取类上的所有注解
        Annotation[] annotations = main.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            //如果是该注解就进行强转
            //可以获取注解中的属性值
            if (annotation instanceof TestAnnotation){
                TestAnnotation testAnnotation=(TestAnnotation) annotation;
                System.out.println(testAnnotation.name());
                for (int num : testAnnotation.nums()) {
                    System.out.println(num);

                }
            }
        }

        //获取成员变量上的注解

           //获取类的成员变量
        Field field = main.getClass().getDeclaredField("name");
        field.setAccessible(true);
           //检查成员变量上是否存在注解
        boolean has = field.isAnnotationPresent(TestAnnotation.class);
        if (has){
            TestAnnotation annotation = field.getAnnotation(TestAnnotation.class);
            System.out.println(annotation.name());
        }


    }
}
