package com.lhq.studentmall.service;



import com.lhq.studentmall.entity.LocalAuth;
import com.lhq.studentmall.exceptions.LocalAuthExecution;

public interface LocalAuthService {
    /**
     * 通过账号和密码查找用户
     *
     * @param userName
     * @return
     */
    LocalAuth getLocalAuthByUserNameAndPwd(String userName, String password);

    /**
     * 通过id查找用户
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalAuthByUserId(long userId);

    /**todo
     * 注册
     * @param localAuth
     * @param profileImg
     * @return
     * @throws RuntimeException
     */
//	LocalAuthExecution register(LocalAuth localAuth) throws RuntimeException;

    /**
     * 绑定微信
     *
     * @param localAuth
     * @return
     * @throws RuntimeException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
            throws RuntimeException;

    /**
     * 修改密码
     *
     * @param localAuthId
     * @param userName
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String userName,
                                       String password, String newPassword);
}
