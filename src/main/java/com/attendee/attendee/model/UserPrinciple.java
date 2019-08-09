package com.attendee.attendee.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
 
public class UserPrinciple implements UserDetails {
	
	private static final long serialVersionUID = 1L;
  	private UUID id;
    private String name;
    private String username;
    private String email;
     @JsonIgnore
    private String password;
    private CompanyUnitPosisi idCompany;
    private Collection<? extends GrantedAuthority> authorities;
 
    public UserPrinciple(UUID id, String name, 
              String username, String email, String password,CompanyUnitPosisi idCompany,
              Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.idCompany=idCompany;
        this.authorities = authorities;
    }
 
    public static UserPrinciple build(UserCompany userCompany) {
    	
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	authorities.add(new SimpleGrantedAuthority(userCompany.getIdTipeUser().getTipe()));

        return new UserPrinciple(
        		userCompany.getIdUser().getId(),
        		userCompany.getIdUser().getNama(),
        		userCompany.getIdUser().getUsername(),
        		userCompany.getIdUser().getEmail(),
        		userCompany.getIdUser().getPassword(),
        		userCompany.getIdCompanyunitPosisi(),
                authorities
        );
    }
 
    public UUID getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    public String getEmail() {
        return email;
    }
 
    @Override
    public String getUsername() {
        return username;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
    
    public CompanyUnitPosisi getIdCompany() {
		return idCompany;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }
 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}