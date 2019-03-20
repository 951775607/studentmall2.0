package com.lhq.studentmall.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 功能描述:tomcat运行的时候解密数据库密码
 *
 * @param:
 * @return:
 **/
public class EncryptPropertyPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {
    //需要加密的字段数组
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password"};

    //对关键的属性进行转换
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptProp(propertyName)) {
            //对加密的字段进行解密
            String decryptValue = DESUtils.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    //该属性是否已加密
    private boolean isEncryptProp(String propertyName) {
        for (String encryptpropertyName : encryptPropNames) {
            if (encryptpropertyName.equals(propertyName))
                return true;
        }
        return false;
    }
}
