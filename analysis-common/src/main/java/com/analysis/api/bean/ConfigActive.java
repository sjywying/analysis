package com.analysis.api.bean;

import java.io.Serializable;

import com.analysis.common.utils.Strings;

public class ConfigActive implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ip;
	private String ctime;
	private String tid;
	private String imsi;
	private String imei;
	private String adccompany;
	private String model;
	private String av;
	private String pkgname;
	private String an;
	private String cityid;
	private String type;
	
	public boolean check() {
		if(Strings.isEmpty(tid)) return false;
		if(Strings.isEmpty(imsi)) return false;
		if(Strings.isEmpty(imei)) return false;
		if(Strings.isEmpty(adccompany)) return false;
		if(Strings.isEmpty(model)) return false;
		
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

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getAdccompany() {
		return adccompany;
	}

	public void setAdccompany(String adccompany) {
		this.adccompany = adccompany;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getAv() {
		return av;
	}

	public void setAv(String av) {
		this.av = av;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getPkgname() {
		return pkgname;
	}

	public void setPkgname(String pkgname) {
		this.pkgname = pkgname;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
