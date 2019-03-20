package com.lhq.studentmall.interceptor.shopadmin;


import com.lhq.studentmall.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 功能描述:
 *
 * @param: 店家管理系统登陆验证拦截器
 * @return:
 **/
public class ShopLoginInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 功能描述:拦截用户登录前的操作，覆盖继承的HandlerInterceptor里面的方法即可
	 *
	 * @param:
	 * @return:
	 **/
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//从session中获取用户信息
		Object userObj = request.getSession().getAttribute("user");
		if (userObj != null) {
			//如果用户信息不为空在将session里的用户信息转换成personinfo实体类对象
			PersonInfo user = (PersonInfo) userObj;
			//确保userId不为空且改账号可用状态为1，并且用户类型为店家
			if (user != null && user.getUserId() != null
					&& user.getUserId() > 0 && user.getEnableStatus() == 1
					&& user.getEnableStatus() == 1){
				return true;
			}
		}
		//如果不满足登录条件，则直接跳转到账号登录页面
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<script>");
		out.println("window.open ('" + request.getContextPath()
				+ "/local/login?usertype=2','_self')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}
}
