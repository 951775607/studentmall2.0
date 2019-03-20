package com.lhq.studentmall.service.impl;



import com.lhq.studentmall.dao.LocalAuthDao;
import com.lhq.studentmall.dao.PersonInfoDao;
import com.lhq.studentmall.entity.LocalAuth;
import com.lhq.studentmall.enume.LocalAuthStateEnum;
import com.lhq.studentmall.exceptions.LocalAuthExecution;
import com.lhq.studentmall.service.LocalAuthService;
import com.lhq.studentmall.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

//	@Override
//	@Transactional
//	public LocalAuthExecution register(LocalAuth localAuth) throws RuntimeException {
//		if (localAuth == null || localAuth.getPassword() == null
////				|| localAuth.getUserName() == null) {
////			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
////		}
////		try {
////			localAuth.setCreateTime(new Date());
////			localAuth.setLastEditTime(new Date());
////			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
//			if (localAuth.getPersonInfo() != null
//					&& localAuth.getPersonInfo().getUserId() == null) {
//				if (profileImg != null) {
//					localAuth.getPersonInfo().setCreateTime(new Date());
//					localAuth.getPersonInfo().setLastEditTime(new Date());
//					localAuth.getPersonInfo().setEnableStatus(1);
//					try {
//						addProfileImg(localAuth, profileImg);
//					} catch (Exception e) {
//						throw new RuntimeException("addUserProfileImg error: "
//								+ e.getMessage());
//					}
//				}
//				try {
//					PersonInfo personInfo = localAuth.getPersonInfo();
//					int effectedNum = personInfoDao
//							.insertPersonInfo(personInfo);
//					localAuth.setUserId(personInfo.getUserId());
//					if (effectedNum <= 0) {
//						throw new RuntimeException("添加用户信息失败");
//					}
//				} catch (Exception e) {
//					throw new RuntimeException("insertPersonInfo error: "
//							+ e.getMessage());
//				}
//			}
//			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
//			if (effectedNum <= 0) {
//				throw new RuntimeException("帐号创建失败");
//			} else {
//				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
//						localAuth);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("insertLocalAuth error: "
//					+ e.getMessage());
//		}
//	}

	//本地账号和微信账号绑定
	@Override
	@Transactional
	public LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException {
		if (localAuth == null || localAuth.getPassword() == null
				|| localAuth.getUserName() == null
				|| localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
		//查询是否已经绑定过平台账号
		LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth
				.getPersonInfo().getUserId());
		if (tempAuth != null) {
			//如果绑定直接退出
			return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			//如果没有绑定则进行绑定
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effectedNum = localAuthDao.insertLocalAuth(localAuth);
			if (effectedNum <= 0) {
				throw new RuntimeException("帐号绑定失败");
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS,
						localAuth);
			}
		} catch (Exception e) {
			throw new RuntimeException("insertLocalAuth error: "
					+ e.getMessage());
		}
	}

	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                              String password, String newPassword) {
		if (userId != null && userName != null && password != null
				&& newPassword != null && !password.equals(newPassword)) {
			try {
				//更新密码
				int effectedNum = localAuthDao.updateLocalAuth(userId,
						userName, MD5.getMd5(password),
						MD5.getMd5(newPassword), new Date());
				if (effectedNum <= 0) {
					throw new RuntimeException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new RuntimeException("更新密码失败:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

//	private void addProfileImg(LocalAuth localAuth,
//			CommonsMultipartFile profileImg) {
//		String dest = FileUtil.getPersonInfoImagePath();
//		String profileImgAddr = ImageUtil.generateThumbnail(profileImg, dest);
//		localAuth.getPersonInfo().setProfileImg(profileImgAddr);
//	}

}
