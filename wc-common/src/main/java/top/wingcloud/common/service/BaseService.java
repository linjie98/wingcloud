package top.wingcloud.common.service;

import top.wingcloud.common.encryption.Algorithm;
import top.wingcloud.common.encryption.MessageDigestUtils;

public abstract class BaseService {

    /**
     * 密码加密算法
     * @param user_password
     * @return
     */
    protected String encryptPassword(String user_password){
        return MessageDigestUtils.encrypt(user_password, Algorithm.SHA1);
    }

    /**
     * 生成API鉴权的Token
     * @param user_name
     * @param user_password
     * @return
     */
    protected String getToken(String user_name,String user_password){
        return MessageDigestUtils.encrypt(user_name+user_password, Algorithm.SHA1);
    }
}