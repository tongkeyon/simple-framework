package demo.singleton;

/**
 * @author: tongkeyon@163.com
 * @description:
 **/
public class StarvingType3 {
    private StarvingType3(){}

    private static class init{
         private static final StarvingType3 instance=new StarvingType3();
    }

    public  static StarvingType3 getInstance(){
        return  init.instance;
    }
}
