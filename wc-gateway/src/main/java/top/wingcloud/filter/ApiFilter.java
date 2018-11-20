package top.wingcloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.wingcloud.common.redis.Redis;
import top.wingcloud.common.util.CookiesUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: linjie
 * @description: 服务网关验证拦截
 * @create: 2018/10/11 16:07
 */
@Component
public class ApiFilter extends ZuulFilter {
    @Autowired
    private Redis redis;
    @Autowired
    private CookiesUtil cookiesUtil;

    @Override
    public String filterType() {
        //前缀过滤
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        /**
         * 网关鉴权
         * 当登录接口访问时不进行鉴权
         * 当其他接口访问时进行鉴权
         */
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        /**
         * 登录接口不鉴权
         */
        String getServletPath =request.getServletPath();
        if (getServletPath.equals("/api/user/login")){
            System.out.println("登录接口，不拦截");
            return null;
        }
        /**
         * 获取redis中的myname、mytoken的value
         */
        String username = cookiesUtil.getCookieByName(request,"myname");
        System.out.println("redis"+username + redis.get(username));
        String redis_token = (String) redis.get(username);
        String token = cookiesUtil.getCookieByName(request, "mytoken");

        System.out.println("token_cookie" + cookiesUtil.getCookieByName(request, "mytoken"));
        //System.out.println("getRequestURL:"+getRequestURL);
        //System.out.println("cookie"+cookiesUtil.ReadCookieMap(request).get("mytoken").toString());
        /**
         * 如果token为空，则需要登录后才能访问
         */
        if (token==null){
            System.out.println("token为null");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty"); //当登录被拦截时返回页面的消息
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
        /**
         * 如果token与redis中的token不一致也需要重新登录
         */
        if (!token.equals(redis_token)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            System.out.println("token不相等");
            try {
                ctx.getResponse().getWriter().write("token不等"); //当登录被拦截时返回页面的消息
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        System.out.println("登录成功");
        return null;
    }
}
