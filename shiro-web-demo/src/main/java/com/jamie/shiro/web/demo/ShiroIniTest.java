package com.jamie.shiro.web.demo;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ShiroIniTest {
    public static void main(String[] args) {

        //创建默认的SecurityManager对象
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //设置身份验证的策略
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        //指定身份的策略，至少有一个匹配的策略
        authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        //将策略最后设定给securityManager
        securityManager.setAuthenticator(authenticator);
        //设置授权
        ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
        //解析对应的字符串到PermissionResolver()实例
        authorizer.setPermissionResolver(new WildcardPermissionResolver());
        //将授权最后设定给securityManager
        securityManager.setAuthorizer(authorizer);
        //注入dataSource
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro_test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MyRealm2 myRealm2 = new MyRealm2();
        myRealm2.setJdbcTemplate(jdbcTemplate);
        //设置的数据源
        securityManager.setRealm(myRealm2);
        //将securityManager绑定到上下文中
        SecurityUtils.setSecurityManager(securityManager);
        //得到与当前系统交互的Subject对象
        Subject subject = SecurityUtils.getSubject();
        //登录  得到用户名密码票据对象
        UsernamePasswordToken token = new UsernamePasswordToken("admin@jamie.com","admin");
        try {
            //是否验证过
            subject.login(token);
            System.out.println(subject.hasRole("test"));
            System.out.println("登录成功！！！");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            System.out.println("用户名或密码错误，登录失败！！！");
        }
    }
}
