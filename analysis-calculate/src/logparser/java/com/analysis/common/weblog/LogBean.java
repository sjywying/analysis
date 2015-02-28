package com.analysis.common.weblog;

import com.analysis.common.weblog.parser.RequestFieldParser;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * Created by crazyy on 15/2/26.
 */
public class LogBean {

    private static final String REMOTE_ADDR = "remote_addr";
    private static final String REMOTE_USER = "remote_user";
    private static final String TIME_LOCAL = "time_local";
    private static final String HTTP_REFERER = "http_referer";
    private static final String STATUS = "status";
    private static final String BODY_BYTES_SENT = "body_bytes_sent";
    private static final String HTTP_USER_AGENT = "http_user_agent";
    private static final String HTTP_X_FORWARDED_FOR = "http_x_forwarded_for";
    private static final String REQUEST_BODY = "request_body";
    private static final String REQUEST = "request";

    private String remote_user;
    private String remote_addr;
    private String time_local;
    private String http_referer;
    private Integer status;
    private Long body_bytes_sent;
//    private String request_body;
//    private String request;
    private String http_user_agent;
    private String http_x_forwarded_for;

    private String tid;
    private String imsi;
    private String imei;
    private String channel;
    private String model;
    private String av;
    private String pname;
    private String an;
    private String ccode;
    private String uri;
    private String reqMethod;
    private String httpprotocol;

    private Map<String, Object> params = new HashMap<String, Object>();

    public void put(String field, Object value) {
        if(REMOTE_ADDR.equals(field)) {
            remote_addr = String.valueOf(value);
        } else if (REMOTE_USER.equals(field)) {
            remote_user = String.valueOf(value);
        } else if (TIME_LOCAL.equals(field)) {
            time_local = String.valueOf(value);
        } else if (HTTP_REFERER.equals(field)) {
            http_referer = String.valueOf(value);
        } else if (STATUS.equals(field)) {
            status = (Integer) value;
        } else if (BODY_BYTES_SENT.equals(field)) {
            body_bytes_sent = (Long) value;
        } else if (HTTP_USER_AGENT.equals(field)) {
            http_user_agent = String.valueOf(value);
        } else if (HTTP_X_FORWARDED_FOR.equals(field)) {
            http_x_forwarded_for = String.valueOf(value);
        }
    }

    /**  TODO   params key that each request it is defferent **/
    /**  common params key, each request must be required. **/
    private static final String TID = "tid";
    private static final String IMSI = "imsi";
    private static final String IMEI = "imei";
    private static final String CHANNEL = "adccompany";
    private static final String MODEL = "model";
    private static final String AV = "av";
    private static final String PNAME = "pkgname";
    private static final String AN = "an";
    private static final String CCODE = "cityid";

    /**
     * get, post request params may be overrided to pre (nginx log mate_partten)
     *
     * @param field
     * @param value
     */
    public void put(String field, Map<String, Object> value) {
        if(value == null || value.size() == 0) return ;

        tid = String.valueOf(value.get(TID));
        imsi = String.valueOf(value.get(IMSI));
        imei = String.valueOf(value.get(IMEI));
        channel = String.valueOf(value.get(CHANNEL));
        model = String.valueOf(value.get(MODEL));
        av = String.valueOf(value.get(AV));
        an = String.valueOf(value.get(AN));
        ccode = String.valueOf(value.get(CCODE));
        pname = String.valueOf(value.get(PNAME));

        if(value.containsKey(TID)) value.remove(TID);
        if(value.containsKey(IMSI)) value.remove(IMSI);
        if(value.containsKey(IMEI)) value.remove(IMEI);
        if(value.containsKey(CHANNEL)) value.remove(CHANNEL);
        if(value.containsKey(MODEL)) value.remove(MODEL);
        if(value.containsKey(AV)) value.remove(AV);
        if(value.containsKey(AN)) value.remove(AN);
        if(value.containsKey(CCODE)) value.remove(CCODE);
        if(value.containsKey(PNAME)) value.remove(PNAME);

        if(REQUEST_BODY.equals(field)) {

        } else if (REQUEST.equals(field)) {
            uri = String.valueOf(value.get(RequestFieldParser.KEY_URI));
            reqMethod = String.valueOf(value.get(RequestFieldParser.KEY_METHOD));
            httpprotocol = String.valueOf(value.get(RequestFieldParser.KEY_HTTP));

            value.remove(RequestFieldParser.KEY_URI);
            value.remove(RequestFieldParser.KEY_METHOD);
            value.remove(RequestFieldParser.KEY_HTTP);
        }

        params.putAll(value);
    }


    public String buildString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(tid == null ? "" : tid).append('\t');
        sb.append(imsi == null ? "" : imsi).append('\t');
        sb.append(imei == null ? "" : imei).append('\t');
        sb.append(channel == null ? "" : channel).append('\t');
        sb.append(model == null ? "" : model).append('\t');
        sb.append(av == null ? "" : av).append('\t');
        sb.append(an == null ? "" : an).append('\t');
        sb.append(pname == null ? "" : pname).append('\t');
        sb.append(ccode == null ? "" : ccode).append('\t');
        sb.append(uri == null ? "" : uri).append('\t');
        sb.append(time_local).append('\t');
        sb.append(remote_addr).append('\t');
        sb.append(params).append('\t');
        sb.append(http_user_agent);
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("remote_user='").append(remote_user).append('\'');
        sb.append(", remote_addr='").append(remote_addr).append('\'');
        sb.append(", time_local='").append(time_local).append('\'');
        sb.append(", http_referer='").append(http_referer).append('\'');
        sb.append(", status=").append(status);
        sb.append(", body_bytes_sent=").append(body_bytes_sent);
        sb.append(", http_user_agent='").append(http_user_agent).append('\'');
        sb.append(", http_x_forwarded_for='").append(http_x_forwarded_for).append('\'');
        sb.append(", tid='").append(tid == null ? "" : tid).append('\'');
        sb.append(", imsi='").append(imsi == null ? "" : imsi).append('\'');
        sb.append(", imei='").append(imei == null ? "" : imei).append('\'');
        sb.append(", channel='").append(channel == null ? "" : channel).append('\'');
        sb.append(", model='").append(model == null ? "" : model).append('\'');
        sb.append(", av='").append(av == null ? "" : av).append('\'');
        sb.append(", an='").append(an == null ? "" : an).append('\'');
        sb.append(", pname='").append(pname == null ? "" : pname).append('\'');
        sb.append(", ccode='").append(ccode == null ? "" : ccode).append('\'');
        sb.append(", uri='").append(uri == null ? "" : uri).append('\'');
        sb.append(", reqMethod='").append(reqMethod).append('\'');
        sb.append(", httpprotocol='").append(httpprotocol).append('\'');
        sb.append(", params=").append(params);
        return sb.toString();
    }

    public String getTid() {
        return tid;
    }

    public String getImsi() {
        return imsi;
    }

    public String getImei() {
        return imei;
    }

    public String getChannel() {
        return channel;
    }

    public String getModel() {
        return model;
    }
}
