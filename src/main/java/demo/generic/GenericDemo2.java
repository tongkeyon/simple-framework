package demo.generic;

/**
 * @program: simple-framework
 * @description:
 * @author: tongkeyon
 * @create: 2020-05-03 22:10
 **/
public class GenericDemo2 {

    public<T>void set(T t){
        return;
    }

    public static void foo1(GenericDemo1<? extends Human> genericDemo1){
        System.out.println(genericDemo1.getData());
    }

    public static void foo2(GenericDemo1<? super Boy> genericDemo1){
        System.out.println(genericDemo1);
    }

    public static void main(String[] args) {
        Boy boy = new Boy();
        GenericDemo1<Boy> demo1 = new GenericDemo1<>();
        GenericDemo1<Human> demo2 = new GenericDemo1<>();
        GenericDemo1<Integer> demo3 = new GenericDemo1<>();
        foo1(demo1);
        foo1(demo2);

    }
}
