package com.example.demouserservice.controller;

import com.example.demouserservice.domain.User;
import com.example.demouserservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private List<User> users;

    @BeforeEach
    public void setUp() {
        users = createUsers();

    }

    @Test
    public void testFindAll() throws Exception {
        when(userService.findAll()).thenReturn(users);
        when(userService.findAll(LocalDate.now(), LocalDate.now())).thenReturn(Collections.singletonList(users.get(0)));


        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Nazar"))
                .andExpect(jsonPath("$[0].lastName").value("shuliak"))
                .andExpect(jsonPath("$[0].email").value("nazarsMail@gmail.com"))
                .andExpect(jsonPath("$[0].birthDate").value(LocalDate.now().minusYears(19).toString()))
                .andExpect(jsonPath("$[0].address").value("address"))
                .andExpect(jsonPath("$[0].phoneNumber").value("123456789"));
    }

    @Test
    public void testFindById() throws Exception {
        when(userService.findById(1L)).thenReturn(users.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName").value("Nazar"))
                .andExpect(jsonPath("$.lastName").value("shuliak"))
                .andExpect(jsonPath("$.email").value("nazarsMail@gmail.com"))
                .andExpect(jsonPath("$.birthDate").value(LocalDate.now().minusYears(19).toString()))
                .andExpect(jsonPath("$.address").value("address"))
                .andExpect(jsonPath("$.phoneNumber").value("123456789"));
    }

    @Test
    public void testCreate() throws Exception {
        User user = users.get(0);
        Long expectedId = user.getId();
        user.setId(null);


        when(userService.create(any())).thenReturn(users.get(0).setId(expectedId));
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content("{ \"email\": \"nazarsMail@gmail.com\", \"firstName\": \"Nazar\", \"lastName\": \"shuliak\", \"address\": \"address\", \"birthDate\": \"2003-04-20\", \"phoneNumber\": \"123456789\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/users/"+expectedId));
    }

    @Test
    public void testPartialUpdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/1")
                        .content("{ \"name\": \"John Doe\" }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testFullyUpdate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .content("{ \"name\": \"John Doe\", \"age\": 35 }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    private List<User> createUsers() {
        User user1 = User.builder()
                .id(1L)
                .email("nazarsMail@gmail.com")
                .address("address")
                .firstName("Nazar")
                .lastName("shuliak")
                .phoneNumber("123456789")
                .birthDate(LocalDate.now().minusYears(19))
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("maxMail@gmail.com")
                .address("address")
                .firstName("Max")
                .lastName("LastName")
                .phoneNumber("123456781")
                .birthDate(LocalDate.now().minusYears(30))
                .build();
        User user3 = User.builder()
                .id(2L)
                .email("maxMail@gmail.com")
                .address("address")
                .firstName("Max")
                .lastName("LastName")
                .phoneNumber("123456781")
                .birthDate(LocalDate.now().minusYears(30))
                .build();

        return List.of(user1,user2,user3);
    }

}

