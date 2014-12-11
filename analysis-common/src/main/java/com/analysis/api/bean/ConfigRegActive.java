package com.analysis.api.bean;

import java.io.Serializable;

import com.analysis.common.utils.Strings;

public class ConfigRegActive implements CheckInterface, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tid;
	private String ctime;
	private String imsi;
	private String imei;
	private String c;
	private String m;
	private String av;
	private String pname;
	private String an;
	private String ccode;
	private String ip;
	private String l;
	private String ua;
	private String type;
	
	@Override
	public boolean check() {
		if(Strings.isEmpty(tid)) return false;
		if(Strings.isEmpty(imsi)) return false;
		if(Strings.isEmpty(imei)) return false;
		if(Strings.isEmpty(c)) return false;
		if(Strings.isEmpty(m)) return false;
		
		return true;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
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

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
