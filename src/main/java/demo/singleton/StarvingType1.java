package demo.singleton;

/**
 * @author: tongkeyon@163.com
 * @description:
 **/
public class StarvingType1 {

    private StarvingType1(){

    }

    public static final StarvingType1 instance=new StarvingType1();

    public static StarvingType1 getInstance(){
        return  instance;
    }
}
