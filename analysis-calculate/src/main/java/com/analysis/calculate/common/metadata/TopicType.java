package com.analysis.calculate.common.metadata;

public enum TopicType {

	REGISTER("注册", "1", "register"),
	CONFIGREGACTIVE("配置注册激活", "1", "configregactive");

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