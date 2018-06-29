/**
 * 
 * 项目名称：tikie-eureka-zuul-client
 * 创建日期：2018年6月28日
 * 修改历史：
 * 		1、[2018年6月28日]创建文件 by zhaocs
 */
package com.tikie.eureka.zuul.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author zhaocs
 *
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger log=LoggerFactory.getLogger(MyFilter.class);
    /* (non-Javadoc)
     * @see com.netflix.zuul.IZuulFilter#run()
     */
    @Override
    public Object run() throws ZuulException {
        // filter需要执行的具体操作
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        if(log.isDebugEnabled()) {
            log.debug("-------token:{}------", token);
        }
        if(token==null){
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            return null;
        }
        log.info("ok");
        return null;
    }

    /* (non-Javadoc)
     * @see com.netflix.zuul.IZuulFilter#shouldFilter()
     */
    @Override
    public boolean shouldFilter() {
        // 表示是否需要执行该filter的run方法，true表示执行，false表示不执行
        return true;
    }

    /* (non-Javadoc)
     * @see com.netflix.zuul.ZuulFilter#filterOrder()
     */
    @Override
    public int filterOrder() {
        return 0;//定义filter的顺序，数字越小表示顺序越高，越先执行
    }

    /* (non-Javadoc)
     * @see com.netflix.zuul.ZuulFilter#filterType()
     */
    @Override
    public String filterType() {
        return "pre"; // 定义filter的类型，有pre、route、post、error四种
    }

}
