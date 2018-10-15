package org.service.zuul;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class MyFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(MyFilter.class);
	
	
	/**
	 * run: 过滤器的具体逻辑。
	 */
	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s >>> %s", request.getMethod(),request.getRequestURL().toString()));
		Object accessToken = request.getParameter("token");
		if(accessToken == null){
			log.warn("token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try{
				ctx.getResponse().getWriter().write("token is empty");
				
			}catch(Exception e){}
			return null;
		}
		log.info("ok");
		return null;
	}

	/**
	 * shouldFilter: 这里可以写逻辑判断，是否需要过滤
	 */
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * fileOrder：过滤的顺序
	 */
	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**
	 * filterType:返回一个字符串代表过滤器的类型，
	 * 	1.pre: 路由之前
	 * 	2.routing: 路由之时
	 * 	3.post: 路由之后
	 * 	4.error：发送错误调用
	 */
	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

}
