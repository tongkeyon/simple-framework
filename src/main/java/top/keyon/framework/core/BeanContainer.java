package top.keyon.framework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import top.keyon.framework.core.annotation.Component;
import top.keyon.framework.core.annotation.Controller;
import top.keyon.framework.core.annotation.Repository;
import top.keyon.framework.core.annotation.Service;
import top.keyon.framework.core.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: tongkeyon@163.com
 * @description:
 **/
@Slf4j
@NoArgsConstructor(access=AccessLevel.PRIVATE)
public class BeanContainer {

    /** 保存Bean实例的容器*/
    private final Map<Class<?>,Object> beanMap=new ConcurrentHashMap<>();

    /**保存注解*/
    private final List<Class<? extends Annotation> > BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Repository.class, Service.class);

    /**容器是否加载过*/
    private boolean loaded=false;

    /**
     * 判断容器是否加载过
     * @return
     */
    public  boolean isLoaded(){
        return this.loaded;
    }


    /**
     * 使用内部枚举的方式保证单例
     */
    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;
        ContainerHolder(){
            instance=new BeanContainer();
        }
    }

    /**
     * 单例模式创建容器对象
     * @return
     */
    public static BeanContainer getInstance(){
        return  ContainerHolder.HOLDER.instance;
    }

    /**
     * 将指定的包路径下标有BEAN_ANNOTATION注解的class加入到BeanMap中
     * @param packageName
     */
    public synchronized void  loadClass(String packageName){
        if (isLoaded()){
            return;
        }
        //获取package下的所有class
        Set<Class<?>> classSet = ClassUtil.getPackageClass(packageName);
        if (CollectionUtils.isEmpty(classSet)){
            log.warn("set is empty");
            return;
        }
        //遍历所有的class检查是否标有指定的注解
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                 if (clazz.isAnnotationPresent(annotation)){
                     beanMap.put(clazz,ClassUtil.newInstance(clazz,true));
                 }
            }
        }
        this.loaded=true;
    }

    /**
     * 向容器中添加一个实例
     * @param clazz
     * @param bean
     * @return
     */
    public Object addClass(Class<?> clazz,Object bean){
       return this.beanMap.put(clazz,bean);
    }

    /**
     * 删除容器中的实例
     * @param clazz
     * @return
     */
    public Object removeClass(Class<?> clazz){
        return this.beanMap.remove(clazz);
    }

    /**
     * 根据class返回容器中的实例
     * @param clazz
     * @return
     */
    public Object getBean(Class<?> clazz){
        return this.beanMap.get(clazz);
    }

    /**
     * 返回容器中所有的class
     * @return
     */
    public Set<Class<?>> getClasses(){
       return this.beanMap.keySet();
    }

    /**
     * 返回容器中所有的实例
     * @return
     */
    public Set<Object> getObjects(){
        return new HashSet<>(this.beanMap.values());
    }

    /**
     * 根据注解返回对应类型的实例
     * @param annotation
     * @return
     */
    public Set<Object> getClassByAnnotation(Class<? extends Annotation> annotation){
        HashSet<Object> result = new HashSet<>();
        Set<Class<?>> classSet = this.beanMap.keySet();
        if (CollectionUtils.isEmpty(result)){
            log.error("no instance find is this annotation -->{}",annotation);
            return null;
        }
        for (Class<?> clazz : classSet) {
             if (clazz.isAnnotationPresent(annotation)){
                 result.add(clazz);
             }
        }
        return result.size()==0? null:result;
    }


    /**
     * 通过接口或者父类来获取实现类或者子类的集合
     * @param interfaceOrClass
     * @return
     */
    public Set<Object> getClassBySuper(Class interfaceOrClass){
        HashSet<Object> result = new HashSet<>();
        Set<Class<?>> classSet = this.beanMap.keySet();
        if (CollectionUtils.isEmpty(result)){
            log.error("no instance find is this super -->{}",interfaceOrClass);
            return null;
        }
        for (Class<?> clazz : classSet) {
            if (clazz.isAssignableFrom(interfaceOrClass)){
                result.add(clazz);
            }
        }
        return result.size()==0? null:result;
    }

    public static void main(String[] args) {
        BeanContainer instance = BeanContainer.getInstance();
        instance.loadClass("demo.generic");
        instance.getObjects().forEach(System.out::println);
    }


}
