package com.itapp.inventorycontrol.service;

import com.itapp.inventorycontrol.entity.User;
import com.itapp.inventorycontrol.entity.UserRole;
import com.itapp.inventorycontrol.exception.ICErrorType;
import com.itapp.inventorycontrol.exception.ICException;
import com.itapp.inventorycontrol.repository.UserRepository;
import com.itapp.inventorycontrol.security.SignedInUsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final SignedInUsernameGetter signedInUsernameGetter;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.MANAGER);
        return userRepository.save(user);
    }

    public User createEmployee(User user) {
        User manager = signedInUsernameGetter.getUser();
        validateManager(manager);

        user.setCompany(manager.getCompany());
        user.setRegisteredBy(manager.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.EMPLOYEE);
        return userRepository.save(user);
    }

    public void removeEmployee(Long employeeId) {
        User manager = signedInUsernameGetter.getUser();
        validateManager(manager);

        User employee = getByIdOrThrow(employeeId);
        validateEmployeeInCompany(manager, employee);

        userRepository.deleteById(employeeId);
    }

    public User getByEmailOrThrow(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ICException(ICErrorType.IC_201));
    }

    public User getByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ICException(ICErrorType.IC_201));
    }

    private void validateManager(User manager){
        if(manager.getRole() != UserRole.MANAGER){
            throw new ICException(ICErrorType.IC_202);
        }
    }

    private void validateEmployeeInCompany(User manager, User employee) {
        if (manager.getCompany().getId() != employee.getCompany().getId()) {
            throw new ICException(ICErrorType.IC_201);
        }
    }
}
