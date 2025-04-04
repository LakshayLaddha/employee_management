package com.example.ems.config;

import com.example.ems.model.Role;
import com.example.ems.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
    }

    private void initRoles() {
        // Only initialize if the roles table is empty
        if (roleRepository.count() == 0) {
            System.out.println("Initializing roles in database...");
            
            Role userRole = new Role();
            userRole.setName(Role.ERole.ROLE_USER);
            roleRepository.save(userRole);

            Role managerRole = new Role();
            managerRole.setName(Role.ERole.ROLE_MANAGER);
            roleRepository.save(managerRole);

            Role adminRole = new Role();
            adminRole.setName(Role.ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);
            
            System.out.println("Roles have been initialized");
        }
    }
}