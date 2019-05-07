package com.lhq.studentmall.web.superadmin;




import com.lhq.studentmall.entity.LocalAuth;
import com.lhq.studentmall.service.LocalAuthService;
import com.lhq.studentmall.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class LoginController {
	@Autowired
	private LocalAuthService localAuthService;

	/**
	 * 登录验证
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> loginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取前端传递过来的帐号和密码
		String userName = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		// 空值判断
		if (userName != null && password != null) {
			// 获取本地帐号授权信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(userName, password);
//			LocalAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
			if (localAuth != null) {
				// 若帐号密码正确，则验证用户的身份是否为超级管理员
				if (localAuth.getPersonInfo().getUserType() == 3) {
					modelMap.put("success", true);
					// 登录成功则设置上session信息
					request.getSession().setAttribute("user", localAuth.getPersonInfo());
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "非管理员没有权限访问");
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码均不能为空");
		}
		return modelMap;
	}

	/**
	 * 功能描述:退出登录
	 *
	 * @param:
	 * @return:
	 **/
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logout(HttpServletRequest request,
									   HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//清空session用户数据
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}
