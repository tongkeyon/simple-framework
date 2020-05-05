package demo.singleton;

/**
 * @author: tongkeyon@163.com
 * @description:
 **/
public class StarvingType2 {
    private StarvingType2(){}

    private static volatile  StarvingType2 instance;

    public static  StarvingType2 getInstance(){
        if (instance ==null){
            synchronized (StarvingType2.class){
                if (instance ==null){
                    instance=new StarvingType2();
                }
            }
        }
        return instance;
    }

}
