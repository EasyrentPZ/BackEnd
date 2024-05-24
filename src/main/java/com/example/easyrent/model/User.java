package com.example.easyrent.model;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Collection;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "lastname", nullable = false, length = 100)
    private String lastname;

    @Column(name = "phone_number", length = 100)
    private String phoneNumber;

    @Column(name = "bank_account", length = 100)
    private String bankAccount;

    @OneToMany(mappedBy = "owner")
    private Set<Property> properties = new LinkedHashSet<>();

    @OneToMany(mappedBy = "notifier")
    private Set<Ticket> tickets = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Contract> contracts = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles)
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    public void addRole(Role role)
    {
        Set<Role> roles = this.getRoles();
        roles.add(role);
    }

    public void addContract(Contract contract)
    {
        Set<Contract> contracts = this.getContracts();
        contracts.add(contract);
    }

    public void addProperty(Property property)
    {
        Set<Property> properties = this.getProperties();
        properties.add(property);
    }

}