package com.analysis.collect.plugins.source.interceptor;

import java.io.Serializable;

/**
 * 	$remote_addr - $remote_user [$time_local] "$request" $status $body_bytes_sent "$http_referer" "$http_user_agent"
 * 
 *	172.16.1.94 - - [03/Nov/2014:11:07:51 +0800] "GET /aaaa/11swa?wewa=111&av=sdfa&type=adfasd HTTP/1.1" 200 5 "-" "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36"
 * 
 * @author Crazy/sjy
 *
 */
public class NginxLog implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String remote_addr;// 记录客户端的ip地址
    private String remote_user;// 记录客户端用户名称,忽略属性"-"
    private String time_local;// 记录访问时间与时区
//    private String request;// 记录请求的url与http协议
    private String request_method;
//    private String request_url;
    private String request_uri;
    private String request_params;
    private String request_protocol;
    private int status;// 记录请求状态；成功是200
    private long body_bytes_sent;// 记录发送给客户端文件主体内容大小
    private String http_referer;// 用来记录从那个页面链接访问过来的
    private String http_user_agent;// 记录客户浏览器的相关信息
    private String http_x_forwarded_for;
    
	public String getRemote_addr() {
		return remote_addr;
	}
	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}
	public String getRemote_user() {
		return remote_user;
	}
	public void setRemote_user(String remote_user) {
		this.remote_user = remote_user;
	}
	public String getTime_local() {
		return time_local;
	}
	public void setTime_local(String time_local) {
		this.time_local = time_local;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getBody_bytes_sent() {
		return body_bytes_sent;
	}
	public void setBody_bytes_sent(long body_bytes_sent) {
		this.body_bytes_sent = body_bytes_sent;
	}
	public String getHttp_referer() {
		return http_referer;
	}
	public void setHttp_referer(String http_referer) {
		this.http_referer = http_referer;
	}
	public String getHttp_user_agent() {
		return http_user_agent;
	}
	public void setHttp_user_agent(String http_user_agent) {
		this.http_user_agent = http_user_agent;
	}
	public String getHttp_x_forwarded_for() {
		return http_x_forwarded_for;
	}
	public void setHttp_x_forwarded_for(String http_x_forwarded_for) {
		this.http_x_forwarded_for = http_x_forwarded_for;
	}
	public String getRequest_method() {
		return request_method;
	}
	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}
	public String getRequest_uri() {
		return request_uri;
	}
	public void setRequest_uri(String request_uri) {
		this.request_uri = request_uri;
	}
	public String getRequest_params() {
		return request_params;
	}
	public void setRequest_params(String request_params) {
		this.request_params = request_params;
	}
	public String getRequest_protocol() {
		return request_protocol;
	}
	public void setRequest_protocol(String request_protocol) {
		this.request_protocol = request_protocol;
	}
	
}
