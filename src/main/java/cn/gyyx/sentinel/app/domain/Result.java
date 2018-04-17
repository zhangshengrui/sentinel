/**  
 * @Title:  Result.java   
 * @Package cn.gyyx.app.domain   
 * @Description: 返回结果类
 * @author: zhangguangxin@gyyx.cn
 * @date:   2017年12月4日 上午11:52:11   
 * @version V1.0 
 */
package cn.gyyx.sentinel.app.domain;

public class Result<T> {

    // 错误码
    private Double code;

    // 提示信息
    private String msg;

    // 具体的内容
    private T data;

    public Double getCode() {
        return code;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

