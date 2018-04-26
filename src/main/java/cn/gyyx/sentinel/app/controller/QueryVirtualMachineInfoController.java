package cn.gyyx.sentinel.app.controller;

import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.service.impl.QueryVirtualMachineInfoBusinessImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
@RestController
public class QueryVirtualMachineInfoController {

    @Autowired
    private QueryVirtualMachineInfoBusinessImpl queryVirtualMachineInfoBusiness;

    @RequestMapping(value = "queryVirtualIdByGysn")
    public Result queryVirtualIdByGysn(HttpServletRequest request) throws UnsupportedEncodingException {
        String requestUrl = request.getScheme() //当前链接使用的协议
                +"://" + request.getServerName()//服务器地址
                + ":" + request.getServerPort() //端口号
                + request.getContextPath() //应用名称，如果应用名称为
                + request.getServletPath() //请求的相对url
                + "?" + new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8");
        return queryVirtualMachineInfoBusiness.queryVirtualIdByGysn(requestUrl,request.getServletPath());
    }
}
