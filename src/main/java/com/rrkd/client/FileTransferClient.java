package com.rrkd.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;


import com.rrkd.model.RequestFile;
import com.rrkd.util.MD5FileUtil;



public class FileTransferClient {
    public static void connect(int port, String host, final RequestFile echoFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null))); // 最大长度
                    //设置服务器端的编码和解码
                    ch.pipeline().addLast(new com.rrkd.client.NettyMessageDecoder());
                    ch.pipeline().addLast(new com.rrkd.client.NettyMessageEncoder());
                    ch.pipeline().addLast(new com.rrkd.client.FileTransferClientHandler(echoFile));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void fileClient() throws IOException, InterruptedException {
        int port = 8094;
        try {
            String url = System.getProperty("myurl");
            System.out.println("url----->"+url);
            ArrayList<String> listFileName = new ArrayList<String>();
            File file1 = new File("D://logs//"+url);
            //过滤得到文件夹中后缀为.log的文件
            String []names = file1.list(new FilenameFilter("log"));
            if(names != null){
                listFileName.addAll(Arrays.asList(names));
            }
            for (int i = 0; i < listFileName.size(); i++) { //String name : listFileName
                RequestFile echo = new RequestFile();
                File file= new File( "D://logs//"+url+"//"+listFileName.get(i));
                String fileName = file.getName();
                echo.setFile(file);
                echo.setFile_md5(MD5FileUtil.getFileMD5String(file));
                echo.setFile_name(fileName);
                echo.setFile_type(getSuffix(fileName));
                echo.setStarPos(echo.getEndPos());
                echo.setFile_size(file.length());
                //排除内容为空的文件
                if(file.length() ==0){
                    continue;
                }
                //服务器IP地址
                //FileTransferClient .connect(port, "10.190.3.8", echo);
                FileTransferClient.connect(port, "127.0.0.1", echo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getSuffix(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return fileType;
    }

}