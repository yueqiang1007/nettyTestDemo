package com.rrkd.client;

import java.io.File;

/**
 * Created by Tony on 2017/7/19.
 * 文件过滤器，得到后缀为.log的日志文件
 */
public class FilenameFilter implements java.io.FilenameFilter {

    public FilenameFilter(String suffix) {
        super();
        this.suffix = suffix;
    }
    private String suffix;
    public boolean accept(File file, String name) {

        return name.endsWith(suffix);
    }

/*    public static void main(String[] args) {
        File file = new File("D://logs//sendlogs");
        String []names = file.list(new FilenameFilter("log"));
        for(String name:names){
            System.out.println("name----->"+name);
        }
    }*/

}
