package com.welltech.waterAffair.security.service;


import javax.annotation.Resource;

import com.welltech.waterAffair.repository.UserMapper;
import com.welltech.waterAffair.security.entity.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.welltech.waterAffair.domain.entity.User;


@Component
public class MyUserDetailsService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user;
        try {
            user = userMapper.findByUserName(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
        if(user == null){
            throw new UsernameNotFoundException("no user found");
        } else {
            try {
                return new MyUserDetails(user);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }

}
