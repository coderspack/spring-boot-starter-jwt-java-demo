package de.coderspack.demo.springboot.starter.jwt.demo.security.service;

import de.coderspack.demo.springboot.starter.jwt.demo.database.UserRepository;
import de.coderspack.demo.springboot.starter.jwt.demo.database.model.User;
import de.coderspack.spring.boot.jwt.library.security.CurrentJwtUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      // TODO [spring-boot-starter] add to documentation
      return CurrentJwtUser.getUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }

}
