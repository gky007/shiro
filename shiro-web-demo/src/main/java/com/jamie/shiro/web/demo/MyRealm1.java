package com.jamie.shiro.web.demo;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {

    @Override
    public String getName() {
        return "myrealml";
    }
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //限制数据源只支持UsernamePasswordToken
        return authenticationToken instanceof UsernamePasswordToken;
    }

    //设置验证信息
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());
        if(!"test".equals(username)){
            throw new UnknownAccountException();
        }
        if(!"123456".equals(password)){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
