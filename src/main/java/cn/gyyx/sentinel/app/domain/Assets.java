package cn.gyyx.sentinel.app.domain;

import java.io.Serializable;

public class Assets implements Serializable {

    private Integer id;             //必要 资产ID    
    private String ip;              //必要 管理IP     
    private String ips;             //     所有IP列表
    private String ipmi_ip;         //     IPMIIP
    private String name;            //必要 老资产设备名称  
    private String gysn;            //必要 光宇编号     
    private Integer idc_id;         //必要 IDC_ID     
    private String os;              //必要 操作系统     
    private String setting;         //必要 真实配置     
    private Integer flag;           //必要 状态:0=报废, 1=正常, 2=故障, 3=待用 
    private Integer virtual;        //必要 虚拟机填写对应主机ID              
    private String manager;         //必要 负责人                              
    private Integer atype;           //必要 设备类型 1=服务器;2=交换机;3=存储;4=其它;5=配件;6=云主机

    private String oper_user;       //必要 操作人
    private String update_date;     //必要 update_date

    private String app_memo;        //     应用变更备注

    private Integer main_id;        //附加字段

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getGysn() {
        return gysn;
    }

    public void setGysn(String gysn) {
        this.gysn = gysn;
    }

    public Integer getIdc_id() {
        return idc_id;
    }

    public void setIdc_id(Integer idc_id) {
        this.idc_id = idc_id;
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

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getAtype() {
        return atype;
    }

    public void setAtype(Integer atype) {
        this.atype = atype;
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

    public String getApp_memo() {
        return app_memo;
    }

    public void setApp_memo(String app_memo) {
        this.app_memo = app_memo;
    }

    public Integer getMain_id() {
        return main_id;
    }

    public void setMain_id(Integer main_id) {
        this.main_id = main_id;
    }
}
