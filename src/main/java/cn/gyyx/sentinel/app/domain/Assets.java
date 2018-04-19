package cn.gyyx.sentinel.app.domain;

import java.io.Serializable;

public class Assets implements Serializable {

    private String id;
    private String ip;
    private String ips;
    private String ipmi_ip;
    private String name;
    private String sn;
    private String gysn;
    private String idc_id;
    private String rack;
    private String os;
    private String setting;
    private String settings;
    private String brand;
    private String flag;
    private String virtual;
    private String manager;
    private String atype;
    private String vlan;
    private String contract_id;
    private String oper_user;
    private String update_date;

    private String ass_memo;
    private String app_memo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIps() {
        return ips;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public String getIpmi_ip() {
        return ipmi_ip;
    }

    public void setIpmi_ip(String ipmi_ip) {
        this.ipmi_ip = ipmi_ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getGysn() {
        return gysn;
    }

    public void setGysn(String gysn) {
        this.gysn = gysn;
    }

    public String getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(String idc_id) {
        this.idc_id = idc_id;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getOper_user() {
        return oper_user;
    }

    public void setOper_user(String oper_user) {
        this.oper_user = oper_user;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public String getAss_memo() {
        return ass_memo;
    }

    public void setAss_memo(String ass_memo) {
        this.ass_memo = ass_memo;
    }

    public String getApp_memo() {
        return app_memo;
    }

    public void setApp_memo(String app_memo) {
        this.app_memo = app_memo;
    }
}
