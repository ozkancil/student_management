package com.project.schoolmanagment.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String name;
    private Boolean isAdvisor;
    @JsonIgnore//we dont want to make it public
    private String password;
    //in spring boot to make our roles understandible and usable by SECURITY our roles must be extended. We need to have a class that extends from GrantedAuthorities.
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String name, Boolean isAdvisor, String password, String role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.isAdvisor = isAdvisor;
        this.password = password;
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role));
        this.authorities=grantedAuthorities;
    }

    public Collection<? extends GrantedAuthority>getAuthorities(){
        return authorities;
    }

    @Override
    public String getUsername(){
        return username;
    }
    @Override
    public String getPassword(){
        return username;
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
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        //matching the class type
        if(o==null || getClass()!=o.getClass()){
            return false;
        }
        //matching the ID
        UserDetailsImpl userDetails=(UserDetailsImpl) o;
        return Objects.equals(id,userDetails.getId());

    }
}
