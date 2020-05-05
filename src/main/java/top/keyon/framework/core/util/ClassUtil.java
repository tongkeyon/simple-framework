package top.keyon.framework.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: tongkeyon@163.com
 * @description:操作class对象的工具类
 **/
@Slf4j
public class ClassUtil {


    private static final String FILE_PROTOCOL = "file";

    /**
     * 用户传入package的路径,返回路径下的所有class文件(package路径->绝对路径->搜索绝对路径下的文件->class文件)
     * @param path
     * @return
     */
    public static Set<Class<?>> getPackageClass(String path){
        //获取当前类的类加载器
        ClassLoader classLoader = getClassLoader();
        //通过类加载器来加载资源
        String modPath = path.replace(".", "/");
        URL resourceLocation = classLoader.getResource(modPath);
        if (resourceLocation ==null){
            log.warn("{} can not get from this path",path);
            return  null;
        }
        //根据不同的资源类型使用不同的方式加载对应的类资源
        Set<Class<?>> classSet=null;
        //加载文件类型的资源
        if (resourceLocation.getProtocol().equals(FILE_PROTOCOL)){
            classSet=new HashSet<>();
            File packageDir=new File(resourceLocation.getPath());
            extractFileClass(packageDir,path,classSet);
        }

        return classSet;
        

    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 递归提取目录下的所有class文件
     * @param packageDir
     * @param packageName
     * @param classSet
     */
    private static void extractFileClass(File packageDir, String packageName, Set<Class<?>> classSet) {
        //如果递归到的是文件就不用进行递归
        if (!packageDir.isDirectory()){
            return;
        }
        //如果是文件夹就递归获取文件夹下的文件或文件夹
        File[] subDir = packageDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                //是文件夹就过滤出来
                if (file.isDirectory()) {
                    return true;
                } else {//是文件
                    String filePath = file.getAbsolutePath();
                    //如果是class文件就直接加入到Set中
                    if (filePath.endsWith(".class")) {
                        addClass2Set(filePath);
                    }
                }
                return false;
            }

            //C:\coding\ideaSpace\simple-framework\target\classes\demo\generic\Boy.class 替换为 demo.generic.Boy
            //
            private void addClass2Set(String absolutePath) {
                //将传入的绝对路径名转为标准模式
                String replacePath = absolutePath.replace(File.separator, ".");
                String subStrPath = replacePath.substring(replacePath.indexOf(packageName));
                String standardPath = subStrPath.substring(0, subStrPath.lastIndexOf("."));
                Class<?> clazz = standardPath2Class(standardPath);
                classSet.add(clazz);
            }
        });

        if (subDir !=null){
            for (File file : subDir) {
                extractFileClass(file,packageName,classSet);

            }
        }

    }

    /**
     * 将路径转化为Class对象
     * @param standardPath
     */
    private static Class<?> standardPath2Class(String standardPath) {
        try {
           return   Class.forName(standardPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("path convert to class wrong "+e);
            throw new RuntimeException(e);
        }


    }

    /**
     * 根据传入的class对象创建实例
     * @param clazz
     * @return
     */
    public static Object newInstance(Class<?> clazz,boolean accessable){

        try {
            Constructor<?> con = clazz.getDeclaredConstructor();
            con.setAccessible(accessable);
            return  con.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }


}
