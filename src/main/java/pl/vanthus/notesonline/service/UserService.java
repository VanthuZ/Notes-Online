package pl.vanthus.notesonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.vanthus.notesonline.model.User;
import pl.vanthus.notesonline.repository.RoleRepository;
import pl.vanthus.notesonline.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User getUserById(Long id){
        return  userRepository.getOne(id);
    }

    public String getCurrentUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public void registerUser(User user){
        user.addRole(roleRepository.getOne(1L));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean passwordCheck(String password, String password_confirm){
        if(password.equals(password_confirm)){
            return true;
        }
        return false;
    }

}
