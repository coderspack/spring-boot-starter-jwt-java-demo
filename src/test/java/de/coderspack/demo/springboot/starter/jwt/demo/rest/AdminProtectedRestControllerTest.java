package de.coderspack.demo.springboot.starter.jwt.demo.rest;

import de.coderspack.demo.springboot.starter.jwt.demo.util.AbstractRestControllerTest;
import de.coderspack.demo.springboot.starter.jwt.demo.util.LogInUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminProtectedRestControllerTest extends AbstractRestControllerTest {

   @Test
   public void getAdminProtectedGreetingForUser() throws Exception {
      final String token = LogInUtils.getTokenForLogin("user", "password", getMockMvc());

      getMockMvc().perform(get("/api/hiddenmessage")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isForbidden());
   }

   @Test
   public void getAdminProtectedGreetingForAdmin() throws Exception {
      final String token = LogInUtils.getTokenForLogin("admin", "admin", getMockMvc());

      getMockMvc().perform(get("/api/hiddenmessage")
         .contentType(MediaType.APPLICATION_JSON)
         .header("Authorization", "Bearer " + token))
         .andExpect(status().isOk())
         .andExpect(content().json(
            "{\n" +
               "  \"message\" : \"this is a hidden message!\"\n" +
               "}"
         ));
   }

   @Test
   public void getAdminProtectedGreetingForAnonymous() throws Exception {
      getMockMvc().perform(get("/api/hiddenmessage")
         .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isUnauthorized());
   }
}
