package pl.vanthus.notesonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.vanthus.notesonline.model.User;
import pl.vanthus.notesonline.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id){
        return  userRepository.getOne(id);
    }
}
