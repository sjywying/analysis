package com.analysis.calculate.common.metadata;

public enum TopicType {

	NGINX("nginx", "-1", "nginx"),
	OTHER("未知操作", "0", "other"),
	MPAGE("中间页", "1", "mpage"),
	SEARCH("搜索", "2",	"search"),
	MPAGE_DOWNLOAD("中间页下载", "3", "mpage_download"),
	INDEX_PAGE("首页", "4", "index_page"),
	BANNER("首页", "5", "banner"),
	EXPRESS("首页", "6", "express"),
	ACTIVE("首页", "7", "active"),
	REGISTE("首页", "8", "registe");

	private String text;
	private String code;
	private String topic;

	private TopicType(String text, String code, String topic) {
		this.text = text;
		this.code = code;
		this.topic = topic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public String getTopic() {
		return topic;
	}

	public static TopicType codeOf(String code) {
		for (TopicType type : TopicType.values()) {
			if (type.code.equals(code)) {
				return type;
			}
		}
		return null;
	}
}