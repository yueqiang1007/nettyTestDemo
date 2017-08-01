package com.rrkd.client;

import com.rrkd.demo.Controller.DatasynchronousApplicationTests;
import com.rrkd.demo.Server.DataServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Tony on 2017/7/6.
 */

@Configuration
@EnableScheduling
public class SchedulingConfig {

    private final Logger logger =  LoggerFactory.getLogger(SchedulingConfig.class);

    @Scheduled(cron="0/1 * * * * ?")
    public void setLogger() {
        Thread current1 = Thread.currentThread();
    //    System.out.println("判断当前日期-->每天生成一个日志文件夹:");
        Date date = new Date();
        //格式化并转换String类型
        String path="D://logs//"+"sendlogs_"+new SimpleDateFormat("yyyy-MM-dd").format(date);
        //创建文件夹
        File f = new File(path);
        if (!f.exists())
            f.mkdirs();
        String url = "sendlogs_"+ new SimpleDateFormat("yyyy-MM-dd").format(date);
        System.setProperty ("myurl", url);
    }

    //每300秒执行一次 ，开始点 延迟5秒（此处不能小于1分钟）
    @Scheduled(cron="5/30 * * * * ?")
    public void executeUploadTask() {
        Thread current = Thread.currentThread();
        System.out.println("定时任务（每30S执行一次传输任务）:");
        logger.info("定时任务  name:"+current.getName());
        FileTransferClient fileTransferClient = new FileTransferClient();
        try {

            fileTransferClient.fileClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
