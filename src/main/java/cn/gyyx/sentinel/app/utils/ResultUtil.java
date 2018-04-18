/**  
 * @Title:  ResultUtil.java   
 * @Package cn.gyyx.app.utils   
 * @Description: ResultUtil 工具类，提供Result的统一对外操作
 * @author: zhangguangxin@gyyx.cn
 * @date:   2017年12月4日 下午1:13:08   
 * @version V1.0 
 */
package cn.gyyx.sentinel.app.utils;


import cn.gyyx.sentinel.app.domain.Result;

public class ResultUtil {

    public static Result<Object> success(Object object) {
        Result<Object> result = new Result<>();
        result.setCode("0.0");
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Result<Object> success() {
        return success(null);
    }

    public static Result<Object> error(String code, String msg) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
