package com.app.springSecurity;

import com.app.springSecurity.persistence.entity.PermissionEntity;
import com.app.springSecurity.persistence.entity.RoleEntity;
import com.app.springSecurity.persistence.entity.RoleEnum;
import com.app.springSecurity.persistence.entity.UserEntity;
import com.app.springSecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    // this method executes itself at the beginning of the application
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {

            // Permissions
            PermissionEntity createPermission = PermissionEntity.builder()
                    .name("CREATE").build();

            PermissionEntity readPermission = PermissionEntity.builder()
                    .name("READ").build();

            PermissionEntity updatePermission = PermissionEntity.builder()
                    .name("UPDATE").build();

            PermissionEntity deletePermission = PermissionEntity.builder()
                    .name("DELETE").build();

            PermissionEntity refactorPermission = PermissionEntity.builder()
                    .name("REFACTOR").build();


            // Roles

            RoleEntity roleAdmin = RoleEntity.builder()
                    .roleEnum(RoleEnum.ADMIN)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
                    .build();

            RoleEntity roleUser = RoleEntity.builder()
                    .roleEnum(RoleEnum.USER)
                    .permissionList(Set.of(createPermission, readPermission))
                    .build();

            RoleEntity roleInvited = RoleEntity.builder()
                    .roleEnum(RoleEnum.INVITED)
                    .permissionList(Set.of(readPermission))
                    .build();

            RoleEntity roleDeveloper = RoleEntity.builder()
                    .roleEnum(RoleEnum.DEVELOPER)
                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
                    .build();

            // Users

            UserEntity userWilder = UserEntity.builder()
                    .username("Wilder")
                    .password("123")
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .roles(Set.of(roleDeveloper))
                    .build();
        };

    }
}
