package de.coderspack.demo.springboot.starter.jwt.demo.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.coderspack.demo.springboot.starter.jwt.demo.rest.dto.LoginDto;
import de.coderspack.spring.boot.jwt.autoconfigure.properties.JwtProperties;
import de.coderspack.spring.boot.jwt.library.security.JwtAuthenticationManager;
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

   private final JwtAuthenticationManager jwtAuthenticationManager;
   private final JwtProperties jwtProperties;

   public AuthenticationRestController(final JwtAuthenticationManager jwtAuthenticationManager,
                                       final JwtProperties jwtProperties) {
      this.jwtAuthenticationManager = jwtAuthenticationManager;
      this.jwtProperties = jwtProperties;
   }

   @PostMapping("/authenticate")
   public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginDto loginDto) {
      final String jwt = jwtAuthenticationManager.authenticate(loginDto.getUsername(), loginDto.getPassword(), loginDto.isRememberMe());

      final HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.add(jwtProperties.getHeader(), "Bearer " + jwt);

      return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
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
