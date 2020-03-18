package com.springboot.demo.feign.Controller;

import com.springboot.demo.feign.Client.UserFeignClient;
import com.springboot.demo.feign.pojo.User;
import com.springboot.demo.feign.util.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("feign/user")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 通过userFeignClient客户端 像提供服务方获取数据
     * */
    @GetMapping(value = "getById/{id}")
    @ResponseBody
    public ResponseTemplate getUserById(@PathVariable Long id){
        ResponseTemplate template = ResponseTemplate.createOk();
        User user = userFeignClient.queryById(id);
        template.setData(user);
        return template;
    }


    /**
     * 测试服务提供者超时 服务进行降级处理
     * 服务提供者线程休眠5s 客户端开启的设置hystrix的超时时间为2s
     * */
    @GetMapping(value = "getById2/{id}")
    @ResponseBody
    public ResponseTemplate getUserById2(@PathVariable Long id){
        ResponseTemplate template = ResponseTemplate.createOk();
        User user = userFeignClient.queryById2(id);
        template.setData(user);
        return template;
    }

}
