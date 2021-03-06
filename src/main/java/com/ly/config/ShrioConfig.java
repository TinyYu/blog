package com.ly.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShrioConfig {

    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);


        // 添加shiro的内置过滤器
        /**
         * anon : 无需认证就可以访问
         * authc : 必须认证了才能访问
         * user : 必须拥有 记住我 功能才能用
         * perms : 拥有对某个资源的权限才能访问
         * role : 拥有某个角色权限才能访问
         */

        // 拦截
        Map<String, String> map = new LinkedHashMap<>();
        map.put("/admin","anon");
        map.put("/admin/login","anon");
        map.put("/admin/*","authc");




        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        // 设置登陆的请求
        shiroFilterFactoryBean.setLoginUrl("/admin/toLogin");

        return shiroFilterFactoryBean;
    }

    // DefaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        // 关联realm
        defaultWebSecurityManager.setRealm(userRealm);

        return defaultWebSecurityManager;
    }


    // 创建realm对象
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

}
