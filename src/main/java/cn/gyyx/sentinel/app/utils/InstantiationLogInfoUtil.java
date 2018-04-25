package cn.gyyx.sentinel.app.utils;

import cn.gyyx.sentinel.app.domain.Assets;
import cn.gyyx.sentinel.app.domain.LogInfo;
import cn.gyyx.sentinel.app.domain.Result;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class InstantiationLogInfoUtil {
    private final static Logger logger  =  Logger.getLogger(InstantiationLogInfoUtil.class);
    public static LogInfo instantiationLogInfoUtil(Assets assets, String request ,Integer type, Result result){
        try {
            LogInfo logInfo = new LogInfo();
            if(assets == null){
                return  null;
            }
            else{
                logInfo.setAtype(assets.getAtype());
                logInfo.setGysn(assets.getGysn());
                logInfo.setIdc_id(assets.getIdc_id());
                logInfo.setIp(assets.getIp());
                logInfo.setOper_user(assets.getOper_user());
                logInfo.setUpdate_date(new SimpleDateFormat("yyyy-MM-dd HH:MM:ss").format(new Date()));
                logInfo.setVirtual(assets.getVirtual());
                logInfo.setType(type);
                logInfo.setRequest(request);
                String response = (JSON.toJSONString(result).length() > 1000)?JSON.toJSONString(result).substring(0,900):JSON.toJSONString(result);
                logInfo.setResponse(response);
            }
            return logInfo;
        }catch (Exception e){
            logger.error("instantiationLogInfo fail");
            logger.error(e);
            return null;
        }
    }
}
