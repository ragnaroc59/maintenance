package fr.epsi.maintenance.controler;

import fr.epsi.maintenance.dto.UserDto;
import fr.epsi.maintenance.entity.User;
import fr.epsi.maintenance.repository.UserRepository;
import fr.epsi.maintenance.security.config.JwtTokenUtil;
import fr.epsi.maintenance.security.model.ApiResponse;
import fr.epsi.maintenance.security.model.AuthToken;
import fr.epsi.maintenance.security.model.LoginUser;
import fr.epsi.maintenance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserControler {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth")
    public ApiResponse<AuthToken> register(@RequestBody LoginUser user) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        User userFromBase = userRepository.findByUsername(user.getUsername());
        String token = jwtTokenUtil.generateToken(userFromBase);
        return new ApiResponse<>(200,"success",new AuthToken(token,userFromBase.getUsername(),userFromBase.getId()));
    }

    @PostMapping(value = "/create")
    public boolean createUser(@RequestBody UserDto userDto) throws Exception{
        User user = this.userRepository.findByUsername(userDto.getUsername());
        if(user != null){
            throw new Exception("L'utilisateur existe déjà ");
        }
        return this.userService.createUser(userDto);
    }
}
