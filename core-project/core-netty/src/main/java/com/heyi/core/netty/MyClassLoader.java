package com.heyi.core.netty;



import java.io.*;


public class MyClassLoader extends ClassLoader {

    public MyClassLoader(){
        super(null);
    }

/*
* 错误示范  会导致要加载类中其它类型的类也会用此类加载加载
* */
 /*   //没有向上请求父类加载器加载，直接由本加载完成类加载
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            byte[] b = loadClassData(name);
            return defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return super.loadClass(name);
    }*/

    //正确示例  仅处理自己要加载的类. 当父加载器找不到此类的时候 ，会再交还findClass 的当前线程加载器加载
    public Class findClass(String name) {
        byte[] b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }



    //用于加载类文件
    private byte[] loadClassData(String name) {
        String path = MyClassLoader.class.getResource("/").getPath();
        name = path + name.replace(".","/") + ".class";
        //使用输入流读取类文件
        InputStream in = null;
        //使用byteArrayOutputStream保存类文件。然后转化为byte数组
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(new File(name));
            out = new ByteArrayOutputStream();
            int i = 0;
            while ( (i = in.read()) != -1){
                out.write(i);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return out.toByteArray();

    }
}
