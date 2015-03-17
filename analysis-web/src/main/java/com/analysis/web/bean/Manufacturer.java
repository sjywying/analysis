package com.analysis.web.bean;

public class Manufacturer {

	private String cdate;
	private String channel;
	private long regnum;
	private long activenum;

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public long getRegnum() {
		return regnum;
	}

	public void setRegnum(long regnum) {
		this.regnum = regnum;
	}

	public long getActivenum() {
		return activenum;
	}

	public void setActivenum(long activenum) {
		this.activenum = activenum;
	}
}
