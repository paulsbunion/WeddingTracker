package com.defrainphoto.weddingtracker.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.defrainphoto.weddingtracker.model.User;
import com.defrainphoto.weddingtracker.model.UserManager;
import com.defrainphoto.weddingtracker.model.UserRole;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
 
    static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
     
    @Autowired
    private UserManager userManager;
     
    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
    	
        User user = userManager.getUserByUserName(userName);
        logger.info("User : {}", user);
        
        if (user==null) {
            logger.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserName(), 
        		user.getPassword(), getGrantedAuthorities(user));
        
        return userDetails;
    }
 
     
    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
         
        for(UserRole userRole : userManager.getUserRoles(user)){
            logger.info("UserProfile : {}", userRole);
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRole()));
        }
        logger.info("authorities : {}", authorities);
        return authorities;
    }
     
}