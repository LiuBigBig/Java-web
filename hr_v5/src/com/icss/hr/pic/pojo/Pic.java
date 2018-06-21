package com.icss.hr.pic.pojo;

import java.io.InputStream;
import java.util.Date;

/**
 * Õºø‚ µÃÂ¿‡
 * @author Administrator
 *
 */
public class Pic {
	
	private Integer picId;
	
	private String picName;
	
	private String picInfo;
	
	private Long picSize;
	
	private String picAuthor;
	
	private byte[] picData;
	
	private Date picDatetime;

	public Pic() {
		super();
	}	

	public Pic(String picName, String picInfo, Long picSize, String picAuthor, byte[] picData) {
		super();
		this.picName = picName;
		this.picInfo = picInfo;
		this.picSize = picSize;
		this.picAuthor = picAuthor;
		this.picData = picData;
	}

	public Pic(String picName, String picInfo, Long picSize, String picAuthor, byte[] picData, Date picDatetime) {
		super();
		this.picName = picName;
		this.picInfo = picInfo;
		this.picSize = picSize;
		this.picAuthor = picAuthor;
		this.picData = picData;
		this.picDatetime = picDatetime;
	}

	public Pic(Integer picId, String picName, String picInfo, Long picSize, String picAuthor, byte[] picData,
			Date picDatetime) {
		super();
		this.picId = picId;
		this.picName = picName;
		this.picInfo = picInfo;
		this.picSize = picSize;
		this.picAuthor = picAuthor;
		this.picData = picData;
		this.picDatetime = picDatetime;
	}

	public Integer getPicId() {
		return picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicInfo() {
		return picInfo;
	}

	public void setPicInfo(String picInfo) {
		this.picInfo = picInfo;
	}

	public Long getPicSize() {
		return picSize;
	}

	public void setPicSize(Long picSize) {
		this.picSize = picSize;
	}

	public String getPicAuthor() {
		return picAuthor;
	}

	public void setPicAuthor(String picAuthor) {
		this.picAuthor = picAuthor;
	}

	public byte[] getPicData() {
		return picData;
	}

	public void setPicData(byte[] picData) {
		this.picData = picData;
	}

	public Date getPicDatetime() {
		return picDatetime;
	}

	public void setPicDatetime(Date picDatetime) {
		this.picDatetime = picDatetime;
	}

	@Override
	public String toString() {
		return "Pic [picId=" + picId + ", picName=" + picName + ", picInfo=" + picInfo + ", picSize=" + picSize
				+ ", picAuthor=" + picAuthor + ", picData=" + picData + ", picDatetime=" + picDatetime + "]";
	}
	
}