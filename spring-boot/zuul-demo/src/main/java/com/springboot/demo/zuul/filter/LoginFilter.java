package com.springboot.demo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.springboot.demo.zuul.common.FilterType;
import com.springboot.demo.zuul.util.ResponseTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * ZuulFilter 过滤器的顶级父类
 *
 * shouldFilter：返回一个Boolean值，判断该过滤器是否需要执行。返回true执行，返回false不执行
 *
 * run：过滤器的具体业务逻辑
 *
 * filterType：返回字符串，代表过滤器的类型。包含以下4种：
 pre：请求在被路由之前执行
 route：在路由请求时调用
 post：在route和errror过滤器之后调用
 error：处理请求时发生错误调用
 *
 * filterOrder：通过返回的int值来定义过滤器的执行顺序，数字越小优先级越高。
 *
 * 应用场景：
 * 1、请求鉴权：一般放在pre类型，如果发现没有访问权限，直接就拦截了
 * 2、异常处理：一般会在error类型和post类型过滤器中结合来处理。
 * 3、服务调用时长统计：pre和post结合使用。
 * */
@Component
public class LoginFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterType.PRE.type();
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器执行时 实际进行的逻辑操作
     * */
    @Override
    public Object run() throws ZuulException {
        // 获取zuul提供的上下文对象
        RequestContext context = RequestContext.getCurrentContext();
        // 从上下文对象中获取请求对象
        HttpServletRequest request = context.getRequest();
        // 获取token信息
        String token = request.getParameter("access_token");
        // 模拟登录校验逻辑
        ResponseTemplate template;
        if (StringUtils.hasText(token) && token.length() > 10){
            template = ResponseTemplate.createOk();
            template.setMessage("登录校验成功");
        }else{
            template = ResponseTemplate.createError();
            template.setMessage("登录校验失败");
            // 过滤该请求，不对其进行路由
            context.setSendZuulResponse(false);
            context.setResponseBody(template.toString());
        }
        context.set("token", token);
        return null;
    }
}
