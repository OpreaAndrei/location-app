package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.UserLoginDTO;
import org.scd.model.dto.UserRegisterDTO;
import org.scd.repository.RoleRepository;
import org.scd.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User login(UserLoginDTO userLoginDTO) throws BusinessException {

        if (Objects.isNull(userLoginDTO)) {
            throw new BusinessException(401, "Body null !");
        }

        if (Objects.isNull(userLoginDTO.getEmail())) {
            throw new BusinessException(400, "Email cannot be null ! ");
        }

        if (Objects.isNull(userLoginDTO.getPassword())) {
            throw new BusinessException(400, "Password cannot be null !");
        }

        final User user = userRepository.findByEmail(userLoginDTO.getEmail());

        if (Objects.isNull(user)) {
            throw new BusinessException(401, "Bad credentials !");
        }

        if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(401, "Bad credentials !");
        }

        return user;
    }

    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws BusinessException {
        if (Objects.isNull(userRegisterDTO)) {
            throw new BusinessException(401, "Body null");
        }
        if (Objects.isNull(userRegisterDTO.getEmail())) {
            throw new BusinessException(401, "Email can't be null");
        }
        if (!Objects.isNull(userRegisterDTO.getEmail())) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(userRegisterDTO.getEmail());
            if (!matcher.matches()) {
                throw new BusinessException(401, "Email should be in correct form");
            }
        }
        if (Objects.isNull(userRegisterDTO.getPassword())) {
            throw new BusinessException(401, "Password can't be null");
        }
        if (Objects.isNull(userRegisterDTO.getConfirmPassword())) {
            throw new BusinessException(401, "Confirm Password can't be null");
        }
        if (Objects.isNull(userRegisterDTO.getFirstName())) {
            throw new BusinessException(401, "First name can't be null");
        }
        if (Objects.isNull(userRegisterDTO.getLastName())) {
            throw new BusinessException(401, "Last name can't be null");
        }
        if (!Objects.isNull(userRepository.findByEmail(userRegisterDTO.getEmail()))) {
            throw new BusinessException(401, "Email already exist ");
        }
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())) {
            throw new BusinessException(401, "Password and Confirm password should match");
        }
        if ((userRegisterDTO.getFirstName().matches("[a-zA-Z ]*\\d+.*"))) {
            throw new BusinessException(401, "First name should be string ");
        }

        if ((userRegisterDTO.getLastName().matches("[a-zA-Z ]*\\d+.*"))) {
            throw new BusinessException(401, "Last name should be string ");
        }

        User user = new User();
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setRoles(roleRepository.findByRole("BASIC_USER"));

        return userRepository.save(user);
    }
}
