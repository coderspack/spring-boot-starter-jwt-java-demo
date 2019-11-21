package de.coderspack.demo.springboot.starter.jwt.demo.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractRestControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @BeforeEach
   public void setUp() {
      SecurityContextHolder.clearContext();
   }

   public MockMvc getMockMvc() {
      return mockMvc;
   }
}
