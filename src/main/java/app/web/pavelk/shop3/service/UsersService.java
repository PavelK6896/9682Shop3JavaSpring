package app.web.pavelk.shop3.service;


import app.web.pavelk.shop3.entity.user.Privilege;
import app.web.pavelk.shop3.entity.user.Role;
import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.entity.user.dto.SystemUser;
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

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

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

        return new org.springframework.security.core.userdetails.User(
                user.getPhone(), user.getPassword(), getAuthorities(user.getRoles()));

    }

    public Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }


    //создаст масив с ролями
    private List<GrantedAuthority> getGrantedAuthorities(Set<String> privileges) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }

        System.out.println("authorities =лист с авторити= " + authorities);
        return authorities;
    }

    //привилегии из ролей
    private Set<String> getPrivileges(Collection<Role> roles) {

        Set<String> privileges = new HashSet<>();
        List<Privilege> collection = new ArrayList<>();

        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }

        for (Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }


    public Iterable<User> findAll() {
        return usersRepository.findAll();
    }

    public User findByPhone(String phone) {
        return usersRepository.findByPhone(phone, User.class);
    }

    public UserDto findByUsernameDto(String phone) {
        return usersRepository.findByPhone(phone, UserDto.class);
    }

    public User saveUser(User user) {

        System.out.println(user.getPassword());

        String p = bCryptPasswordEncoder.encode(user.getPassword());
        System.out.println(p);

        user.setPassword(p);

        return usersRepository.save(user);
    }

    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Transactional
    public void deleteByPhone(String phone) {
        usersRepository.delete(usersRepository.findByPhone(phone, User.class));
    }

    public Iterable<Role> findAllById(List<Long> searchValues) {
        System.out.println("findAllById ");
        return rolesRepository.findAllById(searchValues);
    }

    public Iterable<Role> findAllRole() {
        System.out.println("all role ");
        return rolesRepository.findAll();
    }


    @Transactional
    public User saveRegisterUser(SystemUser systemUser) {
        User user = new User();
        if (findByPhone(systemUser.getPhone()) != null) {
            throw new RuntimeException("User with phone " + systemUser.getPhone() + " is already exist");
        }

        user.setPhone(systemUser.getPhone());

        user.setPassword(bCryptPasswordEncoder.encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());

//        user.setAddress(new Address());
//        Arrays.asList(rolesService.findByName("ROLE_CUSTOMER"))

        user.setRoles(rolesRepository.findById(1L).stream().collect(Collectors.toCollection(ConcurrentLinkedDeque::new)));

        return usersRepository.save(user);
    }


}
