package fr.epsi.maintenance.service.impl;

import fr.epsi.maintenance.GenericConverter;
import fr.epsi.maintenance.dto.UserDto;
import fr.epsi.maintenance.entity.User;
import fr.epsi.maintenance.repository.UserRepository;
import fr.epsi.maintenance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    GenericConverter<UserDto,User> userConverter;

    @Override
    public boolean createUser(UserDto userDto) {
        User user = this.userConverter.dtoToEntity(userDto,User.class);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        this.userRepo.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = this.userRepo.findByUsername(mail);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password !");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
