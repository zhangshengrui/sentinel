package cn.gyyx.sentinel.app.domain;

import java.io.Serializable;

public class AppInfo implements Serializable {
    private Integer assets_id;           //资产ID
    private Integer main_id;             //主类ID
    private String app_name;            //应用名称
    private Integer dist_id;             //dist_id
    private Integer sub_id;              //分类ID
    private String oper_user;           //oper_user
    private String update_date;         //更新时间
    private String ip;                  //ip
    private Integer flag;                //0=停用 1=启用

    public Integer getAssets_id() {
        return assets_id;
    }

    public void setAssets_id(Integer assets_id) {
        this.assets_id = assets_id;
    }

    public Integer getMain_id() {
        return main_id;
    }

    public void setMain_id(Integer main_id) {
        this.main_id = main_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public Integer getDist_id() {
        return dist_id;
    }

    public void setDist_id(Integer dist_id) {
        this.dist_id = dist_id;
    }

    public Integer getSub_id() {
        return sub_id;
    }

    public void setSub_id(Integer sub_id) {
        this.sub_id = sub_id;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
