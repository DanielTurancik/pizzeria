// FILE: src/main/java/sk/pizzeria/demo/config/RoleSeeder.java
package sk.pizzeria.demo.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sk.pizzeria.demo.entity.Role;
import sk.pizzeria.demo.repository.RoleRepository;

@Component
public class RoleSeeder implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        ensureRole("ROLE_CUSTOMER", "Customer");
        ensureRole("ROLE_COOK", "Cook");
        ensureRole("ROLE_COURIER", "Courier");
        ensureRole("ROLE_ADMIN", "Admin / owner");
    }

    private void ensureRole(String name, String description) {
        if (roleRepository.existsByName(name)) return;

        Role r = new Role();
        r.setName(name);
        r.setDescription(description);
        roleRepository.save(r);
    }
}
