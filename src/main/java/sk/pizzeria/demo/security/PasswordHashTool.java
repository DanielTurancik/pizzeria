package sk.pizzeria.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashTool {
    public static void main(String[] args) {
        var enc = new BCryptPasswordEncoder();

        System.out.println("zakaznik123 => " + enc.encode("zakaznik123"));
        System.out.println("kuchar123   => " + enc.encode("kuchar123"));
        System.out.println("kurier123   => " + enc.encode("kurier123"));
        System.out.println("admin123    => " + enc.encode("admin123"));
    }
}
