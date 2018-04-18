package cn.gyyx.sentinel.app.attestation;

import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.utils.MD5Utils;
import cn.gyyx.sentinel.app.utils.ParamsMapSortUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import java.util.*;

public class AttestationFilter {

    private final static  Logger logger  =  Logger.getLogger(AttestationFilter.class);

    public static Result attestation(String uri){
        Result result = new Result();
        try {
            if(StringUtils.isBlank(uri)){
                return result;
            }
            if(uri.indexOf("?")  == -1 || uri.indexOf("?")+1 == uri.length() ){
                result.setCode("401.1");
                result.setMsg("params is null");
                return result;
            }
            //判断参数格式
            String param []= uri.substring(uri.indexOf("?")+1).split("&");
            Map<String,String> params = new HashMap<>();
            for(String str:param){
                if(str.split("=").length < 2 ){
                    result.setCode("401.1");
                    result.setMsg("params value is illegal");
                    return result;
                }
                params.put(str.substring(0,str.indexOf("=")),str.substring(str.indexOf("=")+1));
            }
            //判断参数是否合法
            if(params.size() == 0){
                result.setCode("401.1");
                result.setMsg("params is null");
                return result;
            }
            if(StringUtils.isBlank(params.get("auth_id"))){
                result.setCode("401.1");
                result.setMsg("authId cannot be null");
                return result;
            }
            if(StringUtils.isBlank(params.get("app"))){
                result.setCode("401.1");
                result.setMsg("app cannot be null");
                return result;
            }
            if(StringUtils.isBlank(params.get("timestamp"))){//验证时间戳是否合法
                result.setCode("401.1");
                result.setMsg("timestamp cannot be null");
                return result;
            }
            try {
                Long time = Long.parseLong(params.get("timestamp"));
                if(time < System.currentTimeMillis()/1000  - 60 * 5 || time > System.currentTimeMillis() /1000 +  60 * 5){
                    result.setCode("401.7");
                    result.setMsg("sign timeout");
                    return result;
                }
            }catch (NumberFormatException number){
                result.setCode("401.8");
                result.setMsg("timestamp is illegal");
                return result;
            }
            if(StringUtils.isBlank(params.get("sign_type"))){//验证签名类型是否合法
                result.setCode("401.1");
                result.setMsg("signType cannot be null");
                return result;
            }
            if(!params.get("sign_type").equals("MD5")){
                result.setCode("401.3");
                result.setMsg("signType is illegal");
                return result;
            }
            if(StringUtils.isBlank(params.get("sign"))){//验证签名是否正确
                result.setCode("401.1");
                result.setMsg("sign cannot be null");
                return result;
            }
            if(params.get("sign").length() != 32){
                result.setCode("401.4");
                result.setMsg("sign length is illegal");
                return result;
            }
            result.setCode("200.0");
            result.setMsg("params is ok");
            result.setData(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode("401.0");
            result.setMsg("the system is broken");
            result.setData(null);
        }
        finally {
            return result;
        }

    }
    public static String md5Encode(Map<String,String>params,String authKey,String path){
        if(params.size() == 0 ){
            return "____";
        }
        try {
            Map<String,String> map = ParamsMapSortUtils.sortMapByKey(params);
            StringBuffer stringBuffer = new StringBuffer(path.substring(1)+"?");
            Set<String> keySet = map.keySet();
            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                if(!key.equals("sign") && !key.equals("sign_type")){
                    stringBuffer.append(key+"=");
                    stringBuffer.append(map.get(key)==null?"":map.get(key));
                    stringBuffer.append("&");
                }
            }
            if(stringBuffer.length()>0){
                stringBuffer.deleteCharAt(stringBuffer.length()-1);
            }
            stringBuffer.append(authKey);
            System.out.println(stringBuffer);
            return MD5Utils.MD5(stringBuffer.toString());
        }catch (Exception e){
            logger.error("md4encode map error");
            logger.error(e);
            return "___";
        }
    }

}
