package com.analysis.calculate.active;

import java.io.Serializable;

import org.springframework.util.StringUtils;

public class ActiveBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
//	@JSONField(name = "ip")
	private String ip = "ip";
//	@JSONField(name = "ctime")
	private String ctime = "ctime";
//	@JSONField(name = "tid")
	private String tid = "tid";
//	@JSONField(name = "imsi")
	private String imsi = "imsi";
//	@JSONField(name = "imei")
	private String imei = "imei";
//	@JSONField(name = "adccompany")
	private String c = "c";
//	@JSONField(name = "model")
	private String m = "m";
//	@JSONField(name = "av")
	private String av = "av";
//	@JSONField(name = "pkgname")
	private String pname = "pname";
//	@JSONField(name = "an")
	private String r = "r";
//	@JSONField(name = "cityid")
	private String ccode = "ccode";
	
	public boolean check() {
		if(StringUtils.isEmpty(tid)) return false;
		if(StringUtils.isEmpty(imsi)) return false;
		if(StringUtils.isEmpty(imei)) return false;
		if(StringUtils.isEmpty(c)) return false;
		if(StringUtils.isEmpty(m)) return false;
		
		return true;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public String getAv() {
		return av;
	}
	public void setAv(String av) {
		this.av = av;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getR() {
		return r;
	}
	public void setR(String r) {
		this.r = r;
	}
	public String getCcode() {
		return ccode;
	}
	public void setCcode(String ccode) {
		this.ccode = ccode;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
}
