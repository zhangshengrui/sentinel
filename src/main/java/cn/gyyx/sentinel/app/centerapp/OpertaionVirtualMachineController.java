package cn.gyyx.sentinel.app.centerapp;

import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.service.OperationVirtualMachineBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OpertaionVirtualMachineController {

    @Autowired
    private OperationVirtualMachineBusiness operationVirtualMachineBusinessImpl;

    @RequestMapping(value = "createVirtualMachine")
    public  Result createVirtualMachine(HttpServletRequest request)
    {
        String requestUrl = request.getScheme() //当前链接使用的协议
                +"://" + request.getServerName()//服务器地址
                + ":" + request.getServerPort() //端口号
                + request.getContextPath() //应用名称，如果应用名称为
                + request.getServletPath() //请求的相对url
                + "?" + request.getQueryString();
        return operationVirtualMachineBusinessImpl.createVirtualMachine(requestUrl,request.getServletPath());
    }
}
