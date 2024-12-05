package lk.ijse.gsbackend.service.impl;

import lk.ijse.gsbackend.customObj.UserResponse;
import lk.ijse.gsbackend.customObj.error.UserErrorResponse;
import lk.ijse.gsbackend.dao.UserDAO;
import lk.ijse.gsbackend.dto.impl.UserDTO;
import lk.ijse.gsbackend.entity.impl.UserEntity;
import lk.ijse.gsbackend.exception.UserNotFound;
import lk.ijse.gsbackend.service.UserService;
import lk.ijse.gsbackend.util.Mapping;
import lk.ijse.gsbackend.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceIMPL implements UserService, UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapping mapping;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public int saveUser(UserDTO user) throws Exception {
        if (userDAO.existsByEmail(user.getEmail())) {
            return VarList.Not_Acceptable;
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(user.getRole());
            userDAO.save(modelMapper.map(user, UserEntity.class));
            return VarList.Created;
        }
    }

    @Override
    public void updateUser(String email, UserDTO user) throws Exception {
        UserEntity findEmail = userDAO.findByEmail(email);

        if (findEmail == null) {
            throw new UserNotFound("User Not Found..!!");
        } else {
            findEmail.setEmail(email);
            findEmail.setPassword(user.getPassword());
        }
    }

    @Override
    public void deleteUser(String email) throws Exception {
        UserEntity findEmail = userDAO.findByEmail(email);

        if (findEmail == null) {
            throw new UserNotFound("User Not Found..!!");
        } else {
            userDAO.delete(findEmail);
        }
    }

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<UserEntity> allUsers = userDAO.findAll();
        return mapping.convertUserToDTOList(allUsers);
    }

    @Override
    public UserResponse getUserByEmail(String email) throws Exception {
        if(userDAO.findByEmail(email) != null) {
            return mapping.convertToUserDTO(userDAO.findByEmail(email));
        } else {
            return new UserErrorResponse(0, "User Not Found..!!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userDAO.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userDAO.findByEmail(username);
        return modelMapper.map(user, UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity user){
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;
    }
}
