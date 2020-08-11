package com.ly.config;

import com.ly.pojo.BlogUser;
import com.ly.service.BlogUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    BlogUserService blogUserService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        System.out.println("执行了 ==> 授权");
//
//        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//
//        // 1.拿到当前登陆的用户对象
//        Subject subject = SecurityUtils.getSubject();
//        BlogUser currentUser = (BlogUser) subject.getPrincipal(); // 拿到user对象
////        simpleAuthorizationInfo.addStringPermission(currentUser.getPerms()); // 设置当前用户的权限
//
//        return simpleAuthorizationInfo;
        return null;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了 ==> 认证");


        UsernamePasswordToken userToken = (UsernamePasswordToken)authenticationToken;

        // 查找用户
        BlogUser blogUser = blogUserService.getBlogUserByName(userToken.getUsername());
        if (blogUser == null){ // 用户名不存在
            return null; // 抛出异常 UnknownAccountException
        }

        // 密码认证
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(blogUser, blogUser.getPassword(), getName());
        return simpleAuthenticationInfo;
    }
}
