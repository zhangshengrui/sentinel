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
        result.setCode(401.0);
        result.setMsg("the system is broken");
        result.setData(null);

        try {
            if(StringUtils.isBlank(uri)){
                return result;
            }
            if(uri.indexOf("?")  == -1 || uri.indexOf("?")+1 == uri.length() ){
                result.setCode(401.1);
                result.setMsg("params is null");
                return result;
            }
            //判断参数格式
            String param []= uri.substring(uri.indexOf("?")+1).split("&");
            Map<String,String> params = new HashMap<>();
            for(String str:param){
                if(str.split("=").length < 2 ){
                    result.setCode(401.1);
                    result.setMsg("params value is illegal");
                    return result;
                }
                params.put(str.substring(0,str.indexOf("=")),str.substring(str.indexOf("=")+1));
            }
            //判断参数是否合法
            if(params.size() == 0){
                result.setCode(401.1);
                result.setMsg("params is null");
                return result;
            }
            if(StringUtils.isBlank(params.get("authId"))){
                result.setCode(401.1);
                result.setMsg("authId cannot be null");
                return result;
            }
            if(StringUtils.isBlank(params.get("app"))){
                result.setCode(401.1);
                result.setMsg("app cannot be null");
                return result;
            }
            if(StringUtils.isBlank(params.get("timestamp"))){//验证时间戳是否合法
                result.setCode(401.1);
                result.setMsg("timestamp cannot be null");
                return result;
            }
            try {
                Long time = Long.parseLong(params.get("timestamp"));
                if(time < System.currentTimeMillis()/1000  - 60 * 5 || time > System.currentTimeMillis() /1000 +  60 * 5){
                    result.setCode(401.7);
                    result.setMsg("sign timeout");
                    return result;
                }
            }catch (NumberFormatException number){
                result.setCode(401.8);
                result.setMsg("timestamp is illegal");
                return result;
            }
            if(StringUtils.isBlank(params.get("signType"))){//验证签名类型是否合法
                result.setCode(401.1);
                result.setMsg("signType cannot be null");
                return result;
            }
            if(!params.get("signType").equals("MD5")){
                result.setCode(401.3);
                result.setMsg("signType is illegal");
                return result;
            }
            if(StringUtils.isBlank(params.get("sign"))){//验证签名是否正确
                result.setCode(401.1);
                result.setMsg("sign cannot be null");
                return result;
            }
            if(params.get("sign").length() != 32){
                result.setCode(401.4);
                result.setMsg("sign length is illegal");
                return result;
            }
            result.setCode(200.0);
            result.setMsg("params is ok");
            result.setData(params);
            return result;
        }catch (Exception e){
            logger.error(e);
        }
        finally {
            return result;
        }

    }
    public static String md5Encode(Map<String,String>params,String authKey){
        if(params.size() == 0 ){
            return "____";
        }
        try {
            Map<String,String> map = ParamsMapSortUtils.sortMapByKey(params);
            StringBuffer stringBuffer = new StringBuffer();
            Set<String> keySet = map.keySet();
            Iterator<String> iter = keySet.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                if(key != "sign"){
                    stringBuffer.append(key+"="+map.get(key)+"&");
                }
            }
            stringBuffer.append(authKey);
            return MD5Utils.MD5(stringBuffer.toString());
        }catch (Exception e){
            logger.error("md4encode map error");
            logger.error(e);
            return "___";
        }
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("1111","2222");
        map.put("1111","2222");
        map.put("aa","2222");
        map.put("a","2222");
        map.put("吃","2222");
        map.put("在在","2222");
        StringBuffer stringBuffer =new StringBuffer();
        Map<String,String> map2 = ParamsMapSortUtils.sortMapByKey(map);
        Set<String> keySet = map2.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if(key != "sign"){
                stringBuffer.append("DAV8NNXIGTAA2WYB");
            }
        }
        System.out.println(stringBuffer);
        System.out.println(stringBuffer.deleteCharAt(stringBuffer.length()-1));
    }



}
