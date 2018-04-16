package cn.gyyx.sentinel.app.controller;

import cn.gyyx.sentinel.app.domain.Result;
import cn.gyyx.sentinel.app.utils.ResultUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HelloController {

	@RequestMapping("/")
	Result<Object> home() {
		return ResultUtil.success("hello world!");
	}
	@RequestMapping("/hello")
	String hello() {
		return "Hello World!!!";
	}
	@RequestMapping("/index")
	String index() {
		return "Hello World...";
	}
}