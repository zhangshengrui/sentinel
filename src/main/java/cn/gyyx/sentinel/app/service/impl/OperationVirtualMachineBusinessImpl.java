package cn.gyyx.sentinel.app.service.impl;

import cn.gyyx.sentinel.app.domain.AppInfo;
import cn.gyyx.sentinel.app.domain.Assets;
import cn.gyyx.sentinel.app.mapper.db1.SentinelDBMapper;
import cn.gyyx.sentinel.app.attestation.AttestationFilter;
import cn.gyyx.sentinel.app.domain.Attestation;
import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.mapper.db2.OperationDBMapper;
import cn.gyyx.sentinel.app.service.OperationVirtualMachineBusiness;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.util.*;

@Service
public class OperationVirtualMachineBusinessImpl implements OperationVirtualMachineBusiness {

    private final static Logger logger  =  Logger.getLogger(OperationVirtualMachineBusinessImpl.class);

    @Autowired
    private SentinelDBMapper operationVirtualMachineMapper;

    @Autowired
    private OperationDBMapper operationDBMapper;

    @Override
    @Transactional
    public Result createVirtualMachine(String uri,String path) {
        Result result =  AttestationFilter.attestation(uri);
        if(!result.getCode().equals("200.0")){
            return result;
        }
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            result.setData(null);
            Attestation attestation = new Attestation();
            attestation.setAuth_id(map.get("auth_id"));
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.2");
                result.setMsg("authId is illegal");
                return result;
            }
            attestation.setApp(map.get("app"));
            list =operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.6");
                result.setMsg("authId permission denied");
                return result;
            }
            if(list.size() != 1){
                result.setCode("401.6");
                result.setMsg("authId recognizable");
                return result;
            }
            String sign = AttestationFilter.md5Encode(map,list.get(0).getAuth_key(),path);
            if(!sign.equals(map.get("sign"))){
                result.setCode("401.5");
                result.setMsg("sign error");
                return result;
            }

            //检查必要参数
            List<String> necessary = new ArrayList<>(Arrays.asList("ip","name","gysn","idc_id","os","setting","flag","virtual","manager","atype","oper_user","update_date"));
            necessary.add("main_id");//新增时需要main_id
            result = checkModel(map.get("param"),necessary);

            //插入资产信息
            if(!result.getCode().equals("200.0")){
                return result;
            }
            Assets assets = (Assets)result.getData();
            result.setData(null);
            if(assets == null){
                result.setCode("401.0");
                result.setMsg("the system is broken");
                return result;
            }

            //判断当前gysn是否重复
            List<Assets> assetsList = operationDBMapper.queryAssetsByGysn(assets.getGysn());
            if(assetsList.size()!=0){
                result.setCode("403.4");
                result.setMsg("the gysn is repeated");
                return result;
            }
            Integer i = operationDBMapper.addVirtualAssets(assets);
            if(i<=0){
                result.setCode("200.0");
                result.setMsg("insert into a virtual machine fail");
                return result;
            }
            if(assets.getId() == 0){
                result.setCode("401.0");
                result.setMsg("insert into assets table fail");
                return result;
            }
            Integer subId = operationDBMapper.querySubidAppInfo(assets.getMain_id());
            AppInfo appInfo=new AppInfo();
            appInfo.setAssets_id(assets.getId());
            appInfo.setMain_id(assets.getMain_id());
            appInfo.setApp_name("备机");
            appInfo.setDist_id(-100);
            appInfo.setSub_id(subId);
            appInfo.setOper_user(assets.getOper_user());
            appInfo.setUpdate_date(assets.getUpdate_date());
            appInfo.setIp(assets.getIp());
            appInfo.setFlag(1);
            Integer i2 = operationDBMapper.addAppInfo(appInfo);
            if(i2 <= 0){
                result.setCode("401.0");
                result.setMsg("insert into app_info table fail");
                return result;
            }
            result.setCode("200.0");
            result.setMsg("insert into virtual success");
            result.setData("{\"insertId\": \""+assets.getId()+"\"}");
            return result;
        }catch (Exception e){
            logger.error("OperationVirtualMachineBusinessImpl is error");
            logger.error(e);
            result.setCode("401.0");
            result.setMsg("the system is broken");
            result.setData(e);
            return result;
        }
    }


    public static Result checkModel(String param,List<String> necessary){
        Result result = new Result();
        result.setData(null);
        result.setCode("401.0");
        try {
            if (StringUtils.isBlank(param)){
                result.setCode("403.1");
                result.setMsg("param is null");
                return result;
            }
            Map<String,String>map = JSON.parseObject(param,new HashMap().getClass());
            String setting = null;
            String os = null;
            for(String s:necessary){
                if(!map.containsKey(s)){
                    result.setCode("403.3");
                    result.setMsg("necessary param "+s+" is null");
                    return result;
                }
                if(s.equals("setting")){
                    setting = URLDecoder.decode(map.get("setting"),"utf-8");
                }
                if(s.equals("os")){
                    os = URLDecoder.decode(map.get("os"),"utf-8");
                }
            }
            Assets assets= JSON.parseObject(param,Assets.class);
            assets.setSetting(setting);
            assets.setOs(os);
            result.setCode("200.0");
            result.setMsg("modal is in data");
            result.setData(assets);
            return result;

        }catch (java.io.UnsupportedEncodingException e2){
            logger.error("UnsupportedEncodingException");
            logger.error(e2);
            result.setMsg("param setting UnsupportedEncodingException");
            return result;
        }catch (com.alibaba.fastjson.JSONException e1){
            logger.error("JSON to Object is fail");
            logger.error(e1);
            result.setMsg("JSON to Object is fail");
            return result;
        }
        catch (Exception e){
            logger.error("checkModel is fail");
            logger.error(e);
            result.setMsg("the system is broken");
            return result;
        }
    }

    @Override
    public Result updateVirtualMachine(String uri, String path) {
        Result result =  AttestationFilter.attestation(uri);
        if(!result.getCode().equals("200.0")){
            return result;
        }
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            result.setData(null);
            Attestation attestation = new Attestation();
            attestation.setAuth_id(map.get("auth_id"));
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setMsg("authId is illegal");
                result.setCode("401.2");
                return result;
            }
            attestation.setApp(map.get("app"));
            list =operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.6");
                result.setMsg("authId permission denied");
                return result;
            }
            if(list.size() != 1){
                result.setCode("401.6");
                result.setMsg("authId recognizable");
                return result;
            }
            String sign = AttestationFilter.md5Encode(map,list.get(0).getAuth_key(),path);
            if(!sign.equals(map.get("sign"))){
                result.setCode("401.5");
                result.setMsg("sign error");
                return result;
            }

            //检查必要参数
            List<String> necessary = new ArrayList<>(Arrays.asList("gysn","virtual","ip","ips","flag","app_memo","oper_user","update_date"));
            result = checkModel(map.get("param"),necessary);

            //插入资产信息
            if(!result.getCode().equals("200.0")){
                return result;
            }
            Assets assets = (Assets)result.getData();
            result.setData(null);
            if(assets == null){
                result.setCode("401.0");
                result.setMsg("the system is broken");
                return result;
            }
            Integer i = operationDBMapper.updateVirtualAssets(assets);
            if(i <= 0){
                result.setCode("401.0");
                result.setMsg("update assets table fail");
                return result;
            }
            result.setCode("200.0");
            result.setMsg("update virtual success");
            return result;
        }catch (Exception e){
            logger.error("OperationVirtualMachineBusinessImpl is error");
            logger.error(e);
            result.setMsg("the system is broken");
            result.setCode("401.0");
            result.setData(e);
            return result;
        }
    }

    @Override
    public Result deleteVirtualMachine(String uri, String path) {
        Result result =  AttestationFilter.attestation(uri);
        if(!result.getCode().equals("200.0")){
            return result;
        }
        try {
            Map<String,String> map = (Map<String, String>) result.getData();
            result.setData(null);
            Attestation attestation = new Attestation();
            attestation.setAuth_id(map.get("auth_id"));
            List<Attestation> list = operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setMsg("authId is illegal");
                result.setCode("401.2");
                return result;
            }
            attestation.setApp(map.get("app"));
            list =operationVirtualMachineMapper.queryAuthInfo(attestation);
            if(list.size() == 0){
                result.setCode("401.6");
                result.setMsg("authId permission denied");
                return result;
            }
            if(list.size() != 1){
                result.setCode("401.6");
                result.setMsg("authId recognizable");
                return result;
            }
            String sign = AttestationFilter.md5Encode(map,list.get(0).getAuth_key(),path);
            if(!sign.equals(map.get("sign"))){
                result.setCode("401.5");
                result.setMsg("sign error");
                return result;
            }

            //检查必要参数
            List<String> necessary = new ArrayList<>(Arrays.asList("id","gysn","oper_user","update_date"));
            result = checkModel(map.get("param"),necessary);

            //删除资产信息
            if(!result.getCode().equals("200.0")){
                return result;
            }
            Assets assets = (Assets)result.getData();
            result.setData(null);
            if(assets == null){
                result.setCode("401.0");
                result.setMsg("the system is broken");
                return result;
            }
            Integer i = operationDBMapper.deleteVirtualAssets(assets);
            if(i <= 0){
                result.setCode("401.0");
                result.setMsg("delete assets table fail");
                return result;
            }
            result.setCode("200.0");
            result.setMsg("delete virtual success");
            return result;
        }catch (Exception e){
            logger.error("OperationVirtualMachineBusinessImpl is error");
            logger.error(e);
            result.setData(e);
            result.setMsg("the system is broken");
            result.setCode("401.0");
            return result;
        }
    }
}
