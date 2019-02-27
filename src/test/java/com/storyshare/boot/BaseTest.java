package com.storyshare.boot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.storyshare.boot.repositories.CommentRepository;
import com.storyshare.boot.repositories.MessageRepository;
import com.storyshare.boot.repositories.PostRepository;
import com.storyshare.boot.repositories.UserRepository;
import com.storyshare.boot.services.UserService;
import com.storyshare.boot.services.auth.MVCUser;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseTest {
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    protected ObjectMapper mapper;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    protected PasswordEncoder encoder;
    @Autowired
    protected MessageRepository messageRepository;
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CommentRepository commentRepository;

    protected void init() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    protected String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    protected <T> T toObject(MvcResult result, Class<T> clazz) throws IOException {
        return mapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    protected <T> List<T> getList(MvcResult result, Class<T> clazz) throws IOException {
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return mapper.readValue(result.getResponse().getContentAsString(), listType);
    }

    protected MVCUser getMockUser(String role) {
        return new MVCUser(1L, "customuser@tut.by", encoder.encode("123"),
                true, true, true, true,
                new ArrayList<GrantedAuthority>() {{
                    add(new SimpleGrantedAuthority(
                            "ROLE_" + role.toUpperCase()));
                }});
    }

    protected MVCUser getMockUser(Long id, String role) {
        return new MVCUser(id, "customuser@tut.by", encoder.encode("123"),
                true, true, true, true,
                new ArrayList<GrantedAuthority>() {{
                    add(new SimpleGrantedAuthority(
                            "ROLE_" + role.toUpperCase()));
                }});
    }
}
