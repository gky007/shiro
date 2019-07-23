package com.jamie.shiro.web.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroTest {
    public static void main(String[] args) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-mysql.ini");
        //初始化
        SecurityManager securityManager = factory.getInstance();
        //用SecurityUtils来获取上下文
        SecurityUtils.setSecurityManager(securityManager);
        //得到与当前系统交互的Subject对象
        Subject subject = SecurityUtils.getSubject();
        //登录  得到用户名密码票据对象
        UsernamePasswordToken token = new UsernamePasswordToken("admin@jamie.com","admin");
        try {
            //是否验证过
            subject.login(token);
            if(subject.isAuthenticated()){
                System.out.println("登录成功了");
                if(subject.hasRole("admin")){
                    System.out.println("有admin角色");
                }else{
                    System.out.println("没有admin角色");
                }
                if(subject.isPermitted("search")){
                    System.out.println("有search权限");
                }else{
                    System.out.println("没有search权限");
                }
                if(subject.isPermitted("delete")){
                    System.out.println("有delete权限");
                }else {
                    System.out.println("没有delete权限");
                }
            }

        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("用户名或密码错误，登录失败！！！");
        }
    }
}
