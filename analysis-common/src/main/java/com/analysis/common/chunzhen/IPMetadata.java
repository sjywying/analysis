package com.analysis.common.chunzhen;

import com.analysis.common.utils.Strings;

/**
 * Created by crazyy on 15/3/25.
 */
public class IPMetadata {

    private static final char SPLIT = ':';

    private String startIp;
    private String endIp;
    private String address;
    private String netOperator;
    private String country;
    private String province;
    private String city;

    public boolean check() {
        if(Strings.isEmpty(country) || Strings.isEmpty(province) || Strings.isEmpty(city)) return false;
        else return true;
    }


    public String getStartIp() {
        return startIp;
    }

    public void setStartIp(String startIp) {
        this.startIp = startIp;
    }

    public String getEndIp() {
        return endIp;
    }

    public void setEndIp(String endIp) {
        this.endIp = endIp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNetOperator() {
        return netOperator;
    }

    public void setNetOperator(String netOperator) {
        this.netOperator = netOperator;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String builtStr() {
        StringBuilder sb = new StringBuilder(100);
        sb.append(startIp).append(SPLIT)
                .append(endIp).append(SPLIT)
                .append(country).append(SPLIT)
                .append(province).append(SPLIT)
                .append(city).append(SPLIT)
                .append(address).append(';').append(netOperator)
                .append("\n");
        return sb.toString();
    }
}
