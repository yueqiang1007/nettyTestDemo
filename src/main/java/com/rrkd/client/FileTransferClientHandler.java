package com.rrkd.client;

import com.rrkd.util.FileTransferProperties;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.rrkd.model.RequestFile;
import com.rrkd.model.ResponseFile;
import com.rrkd.model.SecureModel;

public class FileTransferClientHandler extends ChannelInboundHandlerAdapter {
	private int byteRead;
	private volatile long start = 0;
	public RandomAccessFile randomAccessFile;
	private RequestFile request;
	private final int minReadBufferSize = 8192;


	public FileTransferClientHandler(RequestFile ef) {
		if (ef.getFile().exists()) {
			if (!ef.getFile().isFile()) {
				System.out.println("Not a file :" + ef.getFile());
				return;
			}
		}
		this.request = ef;
	}

	public void channelActive(ChannelHandlerContext ctx) {
		SecureModel secure = new SecureModel();
		secure.setToken("2222222222222");
		ctx.writeAndFlush(secure);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof SecureModel){
			try {
				randomAccessFile = new RandomAccessFile(request.getFile(), "r");
				randomAccessFile.seek(request.getStarPos());
				byte[] bytes = new byte[minReadBufferSize];
				if ((byteRead = randomAccessFile.read(bytes)) != -1) {
					request.setEndPos(byteRead);
					request.setBytes(bytes);
					request.setFile_size(randomAccessFile.length());
					ctx.writeAndFlush(request);
				} else {
					System.out.println("文件已经读完");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException i) {
				i.printStackTrace();
			}
			return ;
		}

		if (msg instanceof ResponseFile) {
			ResponseFile response = (ResponseFile)msg;
			System.out.println(response.toString());
			/*String  progess = String.valueOf(response.getProgress());
			String path = file_dir + File.separator + response.getFile_name();
			System.out.println("path---->"+path);
			String url = request.getFile_url();
			System.out.println("url-->"+url);
			Map<String,String> map = new HashMap<String,String>();
			map.put(progess,path);
			Set keySet = map.keySet(); // key的set集合
			Iterator it = keySet.iterator();
			while(it.hasNext()){
				String k = String.valueOf(it.next()); // key  进度
				String v = map.get(k);  //value  文件名
				if(k.equals("100")){
			//		String url = "D://logs//sendlogs//" +v ;
					System.out.println("url------>"+url);
					File file1 = new File(path);
					file1.delete();
					System.out.println(v+"已经被删除！");
				}
			}*/
			if(response.isEnd()){
				randomAccessFile.close();
				//ctx.close();
			}else{
				start = response.getStart();
				if (start != -1) {
					randomAccessFile = new RandomAccessFile(request.getFile(), "r");
					randomAccessFile.seek(start);
					int a = (int) (randomAccessFile.length() - start);
					int sendLength = minReadBufferSize;
					if (a < minReadBufferSize) {
						sendLength = a;
					}
					byte[] bytes = new byte[sendLength];
					if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
						request.setEndPos(byteRead);
						request.setBytes(bytes);
						try {
							ctx.writeAndFlush(request);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						randomAccessFile.close();
						ctx.close();
					}
				}
			}
		}
	}
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

}
