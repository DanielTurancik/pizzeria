package sk.pizzeria.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sk.pizzeria.demo.dto.ChangePasswordForm;
import sk.pizzeria.demo.dto.ProfileEditForm;
import sk.pizzeria.demo.dto.RegisterForm;
import sk.pizzeria.demo.entity.User;
import sk.pizzeria.demo.repository.UserRepository;
import sk.pizzeria.demo.validation.PasswordPolicy;

@Service
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AvatarStorageService avatarStorageService;

    public AccountService(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AvatarStorageService avatarStorageService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.avatarStorageService = avatarStorageService;
    }

    @Transactional
    public Integer register(RegisterForm form) {
        String email = form.getEmail() == null ? null : form.getEmail().trim().toLowerCase();
        if (email == null || email.isBlank()) throw new IllegalStateException("Email is required.");
        if (userRepository.existsByEmail(email)) throw new IllegalStateException("Email is already registered.");

        if (!PasswordPolicy.isStrong(form.getPassword())) {
            throw new IllegalStateException(PasswordPolicy.hint());
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(form.getPassword()));
        u.setFirstName(form.getFirstName().trim());
        u.setLastName(form.getLastName().trim());
        u.setPhoneNumber(form.getPhoneNumber());
        u.setAddress(form.getAddress());
        // Keep URL optional; can be set later via upload.
        u.setProfileImageUrl(form.getProfileImageUrl());
        u.setEnabled(true);

        User saved = userRepository.save(u);
        return saved.getId();
    }

    @Transactional
    public void updateProfile(Integer userId, ProfileEditForm form, MultipartFile avatarFile) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        u.setFirstName(form.getFirstName().trim());
        u.setLastName(form.getLastName().trim());
        u.setPhoneNumber(form.getPhoneNumber());
        u.setAddress(form.getAddress());

        // If user uploaded a file, prefer it over URL text field.
        String storedUrl = avatarStorageService.storeAvatar(avatarFile, userId);
        if (storedUrl != null) {
            u.setProfileImageUrl(storedUrl);
        } else {
            // Keep old image if field is blank; set to new URL if provided.
            String url = form.getProfileImageUrl();
            if (url != null && !url.isBlank()) {
                u.setProfileImageUrl(url.trim());
            }
        }

        userRepository.save(u);
    }

    @Transactional
    public void changePassword(Integer userId, ChangePasswordForm form) {
        User u = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        if (!passwordEncoder.matches(form.getCurrentPassword(), u.getPassword())) {
            throw new IllegalStateException("Current password is incorrect.");
        }

        if (form.getNewPassword() == null || !form.getNewPassword().equals(form.getNewPasswordConfirm())) {
            throw new IllegalStateException("New password confirmation does not match.");
        }

        if (!PasswordPolicy.isStrong(form.getNewPassword())) {
            throw new IllegalStateException(PasswordPolicy.hint());
        }

        if (passwordEncoder.matches(form.getNewPassword(), u.getPassword())) {
            throw new IllegalStateException("New password must be different from the current one.");
        }

        u.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userRepository.save(u);
    }
}