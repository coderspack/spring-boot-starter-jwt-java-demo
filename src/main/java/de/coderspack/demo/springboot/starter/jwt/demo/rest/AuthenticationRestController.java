package de.coderspack.demo.springboot.starter.jwt.demo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.coderspack.demo.springboot.starter.jwt.demo.rest.dto.LoginDto;
import de.coderspack.spring.boot.jwt.library.JWTFilter;
import de.coderspack.spring.boot.jwt.library.factory.TokenFactory;
import de.coderspack.spring.boot.jwt.library.factory.model.Login;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class AuthenticationRestController {

   private final TokenFactory tokenFactory;

   public AuthenticationRestController(TokenFactory tokenFactory) {
      this.tokenFactory = tokenFactory;
   }

   @PostMapping("/authenticate")
   public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDto loginDto) {

      // TODO [spring-boot-starter] add to documentation
      final String jwt = tokenFactory.createToken(map(loginDto));

      final HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

      return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
   }

   private Login map(LoginDto dto) {
      return new Login(dto.getUsername(), dto.getPassword(), dto.isRememberMe() != null ? dto.isRememberMe() : false);
   }

   /**
    * Object to return as body in JWT Authentication.
    */
   static class JWTToken {

      private String idToken;

      JWTToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
      }
   }
}
