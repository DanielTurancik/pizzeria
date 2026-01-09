package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.dto.AdminUserEditForm;
import sk.pizzeria.demo.entity.Role;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.RoleRepository;
import sk.pizzeria.demo.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminUserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<User> searchUsers(String q) {
        String qq = (q == null) ? "" : q.trim().toLowerCase();

        List<User> all = userRepository.findAll();
        if (qq.isBlank()) {
            return all.stream()
                    .sorted(Comparator.comparing(User::getId))
                    .toList();
        }

        return all.stream()
                .filter(u ->
                        (u.getEmail() != null && u.getEmail().toLowerCase().contains(qq)) ||
                                (u.getFirstName() != null && u.getFirstName().toLowerCase().contains(qq)) ||
                                (u.getLastName() != null && u.getLastName().toLowerCase().contains(qq))
                )
                .sorted(Comparator.comparing(User::getId))
                .toList();
    }

    @Transactional(readOnly = true)
    public User getUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll().stream()
                .sorted(Comparator.comparing(Role::getName))
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminUserEditForm toForm(Integer userId) {
        User u = getUser(userId);
        AdminUserEditForm f = new AdminUserEditForm();
        f.setFirstName(u.getFirstName());
        f.setLastName(u.getLastName());
        f.setPhoneNumber(u.getPhoneNumber());
        f.setAddress(u.getAddress());
        f.setRoleIds(u.getRoles().stream().map(Role::getId).collect(Collectors.toSet()));
        return f;
    }

    @Transactional
    public void updateUser(Integer userId, AdminUserEditForm form) {
        User u = getUser(userId);

        Set<Role> roles = form.getRoleIds().stream()
                .map(rid -> roleRepository.findById(rid)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + rid)))
                .collect(Collectors.toSet());

        u.setFirstName(form.getFirstName().trim());
        u.setLastName(form.getLastName().trim());
        u.setPhoneNumber(form.getPhoneNumber());
        u.setAddress(form.getAddress());
        u.setRoles(roles);

        userRepository.save(u);
    }

    @Transactional
    public void toggleEnabled(Integer userId) {
        User u = getUser(userId);
        u.setEnabled(!u.isEnabled());
        userRepository.save(u);
    }
}
