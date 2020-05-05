package demo.singleton;

/**
 * @author: tongkeyon@163.com
 * @description:
 **/
public class EnumStarvingSingleton {

    private enum ContainerHolder {
        HOLDER;
        private  EnumStarvingSingleton singleton;
        ContainerHolder(){
            singleton=new EnumStarvingSingleton();
        }
    }

    public static EnumStarvingSingleton getInstance(){
        return ContainerHolder.HOLDER.singleton;
    }

}
