package com.analysis.calculate.common.metadata;

public enum NginxTopicType {

	NGINX("nginx", "-1", "nginx"),
	OTHER("未知操作", "0", "other"),
//	MPAGE("中间页", "1", "mpage"),
//	SEARCH("搜索", "2",	"search"),
//	MPAGE_DOWNLOAD("中间页下载", "3", "mpage_download"),
//	INDEX_PAGE("首页", "4", "index_page"),
	ACTIVE("激活", "~", "active"),
	BITMAP("bitmap", "~", "nginx"),
	cooperation("合作伙伴", "/ypsearch/urldeal", "cooperation");
//	EXPRESS("首页", "6", "express");

	private String text;
	private String uri;
	private String topic;

	private NginxTopicType(String text, String uri, String topic) {
		this.text = text;
		this.uri = uri;
		this.topic = topic;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUri() {
		return uri;
	}

	public String getTopic() {
		return topic;
	}

	public static NginxTopicType uriOf(String uri) {
		for (NginxTopicType type : NginxTopicType.values()) {
			if (uri.startsWith(type.uri)) {
				return type;
			}
		}
		return null;
	}
	
}