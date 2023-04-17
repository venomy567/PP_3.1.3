package com.security.spring_sec.service;
import com.security.spring_sec.model.Role;
import com.security.spring_sec.model.User;
import com.security.spring_sec.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServicesImp implements UserServices {

    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServicesImp (UserRepo userRepo){
        this.userRepo = userRepo;

    }

    @Transactional
    @Override
    public void add(User user) {
        user.setEnabled("1");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    @Override
    public List<User> getListUser() {
        return userRepo.findAll();
    }

    @Transactional
    @Override
    public User getById(Long id) {
        Optional<User> foundUser = userRepo.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    @Override
    public void update(Long id, User user) {
        user.setId(id);
        user.setEnabled("1");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    public User findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Net takogo polzovtelia");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword()
        , mapRolersToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolersToAuthorities(Collection<Role> role) {
        return role.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}