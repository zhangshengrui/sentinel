package cn.gyyx.sentinel.app.attestation;

import cn.gyyx.sentinel.app.domain.Result;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
                if(time < System.currentTimeMillis() - 1000 * 60 * 5 || time > System.currentTimeMillis() + 1000 * 60 * 5){
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
            }else{
                //todo:MD5校验
                String xxx = "11";
                if(xxx.equals(params.get("sign"))){
                    result.setCode(401.5);
                    result.setMsg("sign error");
                    return result;
                }
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

    public static void main(String[] args) {
        String uri  = "http://host:port/foo/bar?key2=value2&key1=value1&auth_id=x&timestamp=1234567890&sign_type=MD5&sign=fb316e29172065a840090ddc759f4dff";
        String params []= uri.substring(uri.indexOf("?")+1).split("&");
        Map<String,String> map = new HashMap<>();
        String aa= "key2=";
        System.out.println(new Date(1523957372927l));
        System.out.println(System.currentTimeMillis());
    }
}
