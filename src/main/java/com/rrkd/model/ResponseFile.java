package com.rrkd.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseFile implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1425307876096494974L;
	
	
	public ResponseFile(){
		
	}
	
	private String file_name;

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	/**
	 * 开始 读取点
	 */
	private long start;
	/**
	 * 文件的 MD5值
	 */
	private String file_md5;
	/**
	 * 文件下载地址
	 */
	private String file_url;
	/**
	 * 上传是否结束
	 */
	private boolean end;
	/**
	 * 进度
	 */
	private int progress ;

	public long getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getFile_md5() {
		return file_md5;
	}

	public void setFile_md5(String file_md5) {
		this.file_md5 = file_md5;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("\tprogress:");
		sb.append(progress);
		sb.append("\tstart:");
		sb.append(start);
		sb.append("\t\tfile_url:");
		sb.append(file_url);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		sb.append("\t\tuploadTime:");
		sb.append(df.format(new Date()));
		return sb.toString();

		
	}
	
}
