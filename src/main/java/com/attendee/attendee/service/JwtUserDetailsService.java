//package com.attendee.attendee.service;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.attendee.attendee.config.JwtTokenUtil;
//import com.attendee.attendee.model.TipeUser;
//
//
//@Service
//public class JwtUserDetailsService implements UserDetailsService {
//
//	@Autowired
//	private UserService userService;
//		
//	@Autowired
//	private JwtTokenUtil jwtTokenUtil;
//	
//	@Autowired
//	private AuthenticationManager authenticationManager;
//	
//	
//	public void authenticate(String username, String password) throws Exception {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//		} catch (DisabledException e) {
//			throw new Exception("USER_DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//		}
//	}
//
//	public String getToken (String username, String password) throws Exception {
//		authenticate(username, password);
//
//		final UserDetails userDetails = loadUserByUsername(username);
//
//		final String token = jwtTokenUtil.generateToken(userDetails);
//		
//		return token;
//	}
//
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	
//		com.attendee.attendee.model.User user=userService.findUsername(username);
//		
//		if (user != null) {
//            List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
//            return buildUserForAuthentication(user, authorities);
//        }
//		return null;
//	}
//
//	private UserDetails buildUserForAuthentication(com.attendee.attendee.model.User user, List<GrantedAuthority> authorities) {
//		
//		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),true, true, true, true, authorities);
//	}
//
//	private List<GrantedAuthority> buildUserAuthority(Set<TipeUser> userRoles) {
//		
//		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//        userRoles.stream().forEach((userRole) -> {
//            grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getTipe().toString()));
//        });
//        
//        return new ArrayList<>(grantedAuthorities);
//	}
//	
//}