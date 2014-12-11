package com.analysis.api.bean;

import java.io.Serializable;

import com.analysis.common.utils.Strings;

public class Register implements Serializable {
	
//	public static void main(String[] args) {
//		String log = "2014-12-05 23:47:43,112.97.39.23,e865914020006843,460014746986584,865914020006843,101702,UIMI4,APKPRJ112_1.0.0.12_20141105,com.ishow.client.android.plugin.company,w-Android-gen-1280x720,440300,zh-CN";
//		String[] logArr = log.split(",");
//		Registe bean = new Registe();
//		bean.setCtime(logArr[0]);
//		bean.setIp(logArr[1]);
//		bean.setTid(logArr[2]);
//		bean.setImsi(logArr[3]);
//		bean.setImei(logArr[4]);
//		bean.setC(logArr[5]);
//		bean.setM(logArr[6]);
//		bean.setAv(logArr[7]);
//		bean.setPname(logArr[8]);
//		bean.setCcode(logArr[9]);
//		bean.setL(logArr[10]);
//		System.out.println(bean.getTid());
//		System.out.println(JSON.toJSONString(bean));
//	}
	
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private String ctime;
	private String imsi;
	private String imei;
	private String c;
	private String m;
	private String av;
	private String pname;
	private String r;
	private String ccode;
	private String ip;
	private String l;
	private String ua;
	private String type;
	
	public boolean check() {
		if(Strings.isEmpty(tid)) return false;
		if(Strings.isEmpty(imsi)) return false;
		if(Strings.isEmpty(imei)) return false;
		if(Strings.isEmpty(c)) return false;
		if(Strings.isEmpty(m)) return false;
		
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
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getUa() {
		return ua;
	}
	public void setUa(String ua) {
		this.ua = ua;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
