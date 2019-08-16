package com.attendee.attendee.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.attendee.attendee.security.jwt.JwtResponse;
import com.attendee.attendee.storage.StorageService;
import com.fasterxml.jackson.annotation.JsonIgnore;
 
public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;
  	private UUID id;
    private String name;
    private String username;
     @JsonIgnore
    private String password;
    private UserCompany userCompany;
    private Collection<? extends GrantedAuthority> authorities;
    private JwtResponse token;
    private Resource foto;
    
    @Autowired
    private static StorageService storageService;

    
    public UserPrinciple(UUID id, String name, 
              String email, String password,UserCompany userCompany,
              Collection<? extends GrantedAuthority> authorities,Resource foto) {
        this.id = id;
        this.name = name;
        this.username = email;
        this.password = password;
        this.userCompany=userCompany;
        this.authorities = authorities;
        this.foto=foto;
    }
 
    public static UserPrinciple build(UserCompany userCompany) {
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	authorities.add(new SimpleGrantedAuthority(userCompany.getIdTipeUser().getTipe()));
    	
    	Resource foto = storageService.loadAsResource(userCompany.getIdUser().getFoto());

        return new UserPrinciple(
        		userCompany.getIdUser().getId(),
        		userCompany.getIdUser().getNama(),
        		userCompany.getIdUser().getEmail(),
        		userCompany.getIdUser().getPassword(),
        		userCompany,
                authorities,
                foto
        );
    }
 
    
    public JwtResponse getToken() {
		return token;
	}

	public void setToken(JwtResponse token) {
		this.token = token;
	}

	public UUID getId() {
        return id;
    }
 
    public String getName() {
        return name;
    }
 
    @Override
    public String getUsername() {
        return username;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
    
    public UserCompany getUserCompany() {
		return userCompany;
	}
    
    
	public Resource getFoto() {
		return foto;
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