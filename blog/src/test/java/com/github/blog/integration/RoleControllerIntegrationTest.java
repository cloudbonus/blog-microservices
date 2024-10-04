package com.github.blog.integration;

import com.github.blog.config.ContainerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Raman Haurylau
 */
@Transactional
@SpringBootTest(classes = ContainerConfig.class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS, scripts = {"/db/insert-test-data-into-user-table.sql"})
@Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS, scripts = "/db/clean-test-data.sql")
public class RoleControllerIntegrationTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    @Rollback
    @WithUserDetails("admin")
    @DisplayName("role controller: create")
    void create_returnsCreatedRoleDto_whenDataIsValid() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "TEST"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithUserDetails
    @DisplayName("role controller: create - bad request exception")
    void create_throwsExceptionForbidden_whenUserDoesntHaveRightRole() throws Exception {
        mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "TEST"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback
    @WithUserDetails("admin")
    @DisplayName("role controller: update")
    void update_returnsUpdatedRoleDto_whenDataIsValid() throws Exception {
        mockMvc.perform(put("/roles/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name": "NEW"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @Rollback
    @WithUserDetails("admin")
    @DisplayName("role controller: delete")
    void delete_returnsDeletedRoleDto_whenDataIsValid() throws Exception {
        mockMvc.perform(delete("/roles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("role controller: find by id")
    void find_findsRoleById_whenDataIsValid() throws Exception {
        mockMvc.perform(get("/roles/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithUserDetails("admin")
    @DisplayName("role controller: find all")
    void find_findsAllRolesByUserId_whenDataIsValid() throws Exception {
        mockMvc.perform(get("/roles").param("userId", "6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }
}
