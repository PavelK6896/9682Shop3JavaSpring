package app.web.pavelk.shop3.service;


import app.web.pavelk.shop3.entity.product.Category;
import app.web.pavelk.shop3.entity.user.Role;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.entity.user.dto.UserDto;
import app.web.pavelk.shop3.repo.RolesRepository;
import app.web.pavelk.shop3.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setRolesRepository(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = usersRepository.findByPhone(phone, User.class);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return  new org.springframework.security.core.userdetails.User(
                user.getPhone(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }



    public User findByPhone(String phone) {
        return usersRepository.findByPhone(phone, User.class);
    }

    public UserDto findByUsernameDto(String phone) {
        return usersRepository.findByPhone(phone, UserDto.class);
    }

    public Iterable<User> findAll() {
        return usersRepository.findAll();
    }

    public Iterable<Role> findAllById(List<Long> searchValues) {
        return rolesRepository.findAllById(searchValues);
    }

    public User saveUser(User user) {

        System.out.println(user.getPassword());

        String p = bCryptPasswordEncoder.encode(user.getPassword());
        System.out.println(p);

        user.setPassword(p);

        return usersRepository.save(user);
    }

    public Iterable<Role> findAllRole() {
        return rolesRepository.findAll();
    }


    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public void deleteByPhone(String phone) {
        usersRepository.delete(usersRepository.findByPhone(phone, User.class));
    }
}