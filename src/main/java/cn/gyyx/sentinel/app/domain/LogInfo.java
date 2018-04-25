package cn.gyyx.sentinel.app.domain;

public class LogInfo {
    private Integer id;         //主键
    private String oper_user;   //操作人
    private String update_date; //更新时间
    private Integer type;       //1:新增虚拟机 2.删除虚拟机 3更新虚拟机
    private String ip;          //更新IP
    private Integer virtual;    //虚拟机ID
    private String gysn;        //光宇编号
    private Integer idc_id;     //idc_id
    private Integer atype;      //设备类型
    private String request;     //请求内容
    private String response;    //返回内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getVirtual() {
        return virtual;
    }

    public void setVirtual(Integer virtual) {
        this.virtual = virtual;
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

    public Integer getAtype() {
        return atype;
    }

    public void setAtype(Integer atype) {
        this.atype = atype;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
