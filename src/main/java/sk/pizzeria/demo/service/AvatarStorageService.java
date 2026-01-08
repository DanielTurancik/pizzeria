package sk.pizzeria.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.*;
import java.security.MessageDigest;
import java.time.Instant;

@Service
public class AvatarStorageService {

    private final Path uploadDir = Path.of("uploads", "avatars").toAbsolutePath().normalize();

    public String storeAvatar(MultipartFile file, Integer userId) {
        if (file == null || file.isEmpty()) return null;

        String contentType = (file.getContentType() == null) ? "" : file.getContentType().toLowerCase();
        if (!(contentType.equals("image/png") || contentType.equals("image/jpeg") || contentType.equals("image/webp"))) {
            throw new IllegalStateException("Nepodporovaný formát obrázka. Povolené: PNG, JPG, WEBP.");
        }

        if (file.getSize() > 5L * 1024 * 1024) {
            throw new IllegalStateException("Obrázok je príliš veľký (max 5MB).");
        }

        String ext = switch (contentType) {
            case "image/png" -> ".png";
            case "image/jpeg" -> ".jpg";
            case "image/webp" -> ".webp";
            default -> "";
        };

        String filename = "u" + userId + "-" + Instant.now().toEpochMilli() + "-" + shortHash(file) + ext;

        try {
            Files.createDirectories(uploadDir);

            Path target = uploadDir.resolve(filename).normalize();
            if (!target.startsWith(uploadDir)) {
                throw new IllegalStateException("Neplatná cesta uploadu.");
            }

            try (InputStream in = file.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            // Public URL that must match ResourceHandler mapping above
            return "/uploads/avatars/" + filename;
        } catch (Exception e) {
            throw new IllegalStateException("Nepodarilo sa uložiť profilový obrázok.", e);
        }
    }

    private static String shortHash(MultipartFile file) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((file.getOriginalFilename() == null ? "" : file.getOriginalFilename()).getBytes());
            md.update(Long.toString(file.getSize()).getBytes());
            byte[] d = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) sb.append(String.format("%02x", d[i]));
            return sb.toString();
        } catch (Exception e) {
            return "x";
        }
    }
}