package com.attendee.attendee.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.UserCompanyDao;
import com.attendee.attendee.model.UserCompany;
import com.attendee.attendee.model.UserPrinciple;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    UserCompanyDao userCompanyService;
 
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
    	try {
    		UserCompany user = userCompanyService.findByUsername(username);
            return UserPrinciple.build(user);
    	}catch(UsernameNotFoundException e) {     
    		throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
    	}

    }

}