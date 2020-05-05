package demo.generic;

import top.keyon.framework.core.annotation.Component;

/**
 * @program: simple-framework
 * @description:
 * @author: tongkeyon
 * @create: 2020-05-03 22:03
 **/
@Component

public class GenericDemo1<T> {
    private T data;

     public T getData(){
         return data;
     }

     public void setData(T data){
         this.data=data;
     }
}
