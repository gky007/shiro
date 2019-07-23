package com.jamie.shiro.web.demo;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MyRealm2 extends AuthorizingRealm{
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //权限验证调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("权限验证");
        String sql = "select ROLE_NAME from SHIRO_USER_ROLE where USER_NAME = ?";
        String username =(String) principalCollection.getPrimaryPrincipal();
        List<String> roles = jdbcTemplate.queryForList(sql, String.class, username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        return info;
    }
    //登录时候调用
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("doGetAuthenticationInfo");
        String sql = "select PASSWORD from SHIRO_USER  where USER_NAME = ?";
        String username =(String) authenticationToken.getPrincipal();
        String password = jdbcTemplate.queryForObject(sql, String.class, username);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,null,getName());
        return info;
    }
}
