package com.storyshare.boot;

import com.google.gson.JsonObject;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.pojos.Post;
import com.storyshare.boot.pojos.User;
import com.storyshare.boot.repositories.dto.CommentDTO;
import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.repositories.dto.PostDTO;
import com.storyshare.boot.repositories.dto.UserDTO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ComponentScan("com.storyshare")
public class ControllersTests extends BaseTest {
    @Override
    protected void init() {
        super.init();
    }

    /* - - - - - - - - - - - - - - - - - - - User Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void createUser() throws Exception {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "test");
        jsonObject.addProperty("email", "test@tut.by");
        jsonObject.addProperty("password", "123");
        jsonObject.addProperty("avatar", "avatar.png");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isCreated());

        assertNotNull(userService.getUserByEmail("test@tut.by"));

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isResetContent());

        jsonObject.addProperty("email", "test");

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void changeUserRoleTest() throws Exception {
        User user = new User("test", "test@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        userRepository.saveAndFlush(user);

        mvc.perform(patch("/users/" + user.getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("user")
                .with(user(getMockUser("boss"))))
                .andExpect(status().isOk());

        assertEquals(userService.getUserByEmail("test@tut.by").getRole(), "admin");

        mvc.perform(patch("/users/" + user.getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("admin")
                .with(user(getMockUser("boss"))))
                .andExpect(status().isAccepted());

        assertEquals(userService.getUserByEmail("test@tut.by").getRole(), "user");

        mvc.perform(patch("/users/" + user.getId() + "/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content("admin")
                .with(user(getMockUser("user"))))
                .andExpect(status().isForbidden());
    }

    @Test
    public void changeUserStatusTest() throws Exception {
        User user = new User("test", "test@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        userRepository.saveAndFlush(user);

        mvc.perform(patch("/users/" + user.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("active")
                .with(user(getMockUser("admin"))))
                .andExpect(status().isNoContent());

        mvc.perform(patch("/users/" + user.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("active")
                .with(user(getMockUser("boss")))
                .sessionAttr(String.valueOf(user.getId()), user))
                .andExpect(status().isOk());

        assertEquals(userService.getUserByEmail("test@tut.by").getStatus(), "banned");

        mvc.perform(patch("/users/" + user.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("banned")
                .with(user(getMockUser("admin")))
                .sessionAttr(String.valueOf(user.getId()), user))
                .andExpect(status().isAccepted());

        assertEquals(userService.getUserByEmail("test@tut.by").getStatus(), "active");

        mvc.perform(patch("/users/" + user.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("m")
                .with(user(getMockUser("admin")))
                .sessionAttr(String.valueOf(user.getId()), user))
                .andExpect(status().isNoContent());

        mvc.perform(patch("/users/" + user.getId() + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content("admin")
                .with(user(getMockUser("user")))
                .sessionAttr(String.valueOf(user.getId()), user))
                .andExpect(status().isForbidden());
    }

    @Test
    public void getUserAvatarTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        userRepository.saveAndFlush(user);

        MvcResult result = mvc.perform(get("/users/" + user.getId() + "/avatar")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), user.getAvatar());
    }

    @Test
    public void getUserNameTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        userRepository.saveAndFlush(user);

        MvcResult result = mvc.perform(get("/users/" + user.getId() + "/name")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        assertEquals(result.getResponse().getContentAsString(), user.getName());
    }

    @Test
    public void getUserRoleAndStatusTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        userRepository.saveAndFlush(user);
        JsonObject roleAndStatus = new JsonObject();
        roleAndStatus.addProperty("role", user.getRole());
        roleAndStatus.addProperty("status", user.getStatus());

        MvcResult result1 = mvc.perform(get("/users/" + user.getId() + "/role,status")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser("boss"))))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mvc.perform(get("/users/" + user.getId() + "/role,status")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser("admin"))))
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(get("/users/" + user.getId() + "/role,status")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser("user"))))
                .andExpect(status().isForbidden());

        assertEquals(result1.getResponse().getContentAsString(), roleAndStatus.toString());
        assertEquals(result2.getResponse().getContentAsString(), roleAndStatus.toString());
    }

    /* - - - - - - - - - - - - - - - - - - - Post Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void getPostsTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post1 = new Post("test1", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post2 = new Post("test2", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post3 = new Post("test3", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post4 = new Post("test4", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post5 = new Post("test5", LocalDateTime.now(), user, "picture.png");

        postRepository.saveAndFlush(post1);
        postRepository.saveAndFlush(post2);
        postRepository.saveAndFlush(post3);
        postRepository.saveAndFlush(post4);
        postRepository.saveAndFlush(post5);

        MvcResult result = mvc.perform(get("/posts")
                .param("offset", "2")
                .param("limit", "2")
                .param("userID", String.valueOf(user.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        List<PostDTO> list = getList(result, PostDTO.class);
        PostDTO postDTO1 = new PostDTO(post3.getId(), user.getId(), post3.getText(),
                post3.getDate(), user.getName(), user.getAvatar(), post3.getPicture(), 0, false);
        PostDTO postDTO2 = new PostDTO(post2.getId(), user.getId(), post2.getText(),
                post2.getDate(), user.getName(), user.getAvatar(), post2.getPicture(), 0, false);

        assertEquals(list.size(), 2);
        assertEquals(list.get(0), postDTO1);
        assertEquals(list.get(1), postDTO2);
    }

    @Test
    public void createPostTest() throws Exception {
        User user = new User("test", "test@tut.by", "avatar.png", "123", "user");
        userRepository.saveAndFlush(user);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userID", user.getId());
        jsonObject.addProperty("picture", "picture.png");
        jsonObject.addProperty("text", "text");
        long before = postRepository.getNumberOfAllPosts();

        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser(user.getId(),"user"))))
                .andExpect(status().isCreated());
        long after = postRepository.getNumberOfAllPosts();
        assertNotEquals(before, after);

        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isUnauthorized());

        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser("user"))))
                .andExpect(status().isUnauthorized());

        jsonObject.addProperty("text", "tex");
        mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser(user.getId(),"user"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deletePostTest() throws Exception {
        User user = new User("test", "test@tut.by", "avatar.png", "123", "user");
        userRepository.saveAndFlush(user);
        Post post = new Post("test1", LocalDateTime.now(), user, "picture.png");
        postRepository.saveAndFlush(post);
        long before = postRepository.getNumberOfAllPosts();

        mvc.perform(delete("/posts/" + post.getId())
                .param("userID", user.getId().toString())
                .with(user(getMockUser(user.getId(), "user"))))
                .andExpect(status().isOk());

        long after = postRepository.getNumberOfAllPosts();
        assertNotEquals(before, after);

        mvc.perform(delete("/posts/" + post.getId())
                .param("userID", user.getId().toString()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getPostLengthTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post1 = new Post("test1", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post2 = new Post("test2", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post3 = new Post("test3", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post4 = new Post("test4", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post5 = new Post("test5", LocalDateTime.now(), user, "picture.png");

        postRepository.saveAndFlush(post1);
        postRepository.saveAndFlush(post2);
        postRepository.saveAndFlush(post3);
        postRepository.saveAndFlush(post4);
        postRepository.saveAndFlush(post5);

        MvcResult result = mvc.perform(get("/posts/length")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "5");
    }

    @Test
    public void getSinglePostTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post1 = new Post("test1", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post2 = new Post("test2", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Post post3 = new Post("test3", LocalDateTime.now(), user, "picture.png");

        postRepository.saveAndFlush(post1);
        postRepository.saveAndFlush(post2);
        postRepository.saveAndFlush(post3);

        MvcResult result = mvc.perform(get("/posts/" + post2.getId())
                .accept(MediaType.APPLICATION_JSON)
                .param("userID", String.valueOf(user.getId())))
                .andReturn();
        PostDTO expectedPost = toObject(result, PostDTO.class);
        PostDTO actualPost = new PostDTO(post2.getId(), user.getId(), post2.getText(),
                post2.getDate(), user.getName(), user.getAvatar(), post2.getPicture(), 0, false);

        assertEquals(expectedPost, actualPost);
    }

    @Test
    public void likeUnlikePostTest() throws Exception {
        User user = new User("test", "test@tut.by", "avatar.png", "123", "user");
        userRepository.saveAndFlush(user);
        Post post = new Post("test1", LocalDateTime.now(), user, "picture.png");
        postRepository.saveAndFlush(post);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userID", String.valueOf(user.getId()));
        jsonObject.addProperty("isLiked", "true");

        mvc.perform(patch("/posts/" + post.getId() + "/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser(user.getId(), "user"))))
                .andExpect(status().isOk());

        assertTrue(postRepository.getOne(post.getId()).getLikes().contains(user));
        jsonObject.addProperty("isLiked", "false");

        mvc.perform(patch("/posts/" + post.getId() + "/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser(user.getId(), "user"))))
                .andExpect(status().isOk());

        assertFalse(postRepository.getOne(post.getId()).getLikes().contains(user));

        mvc.perform(patch("/posts/" + post.getId() + "/like")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser("user"))))
                .andExpect(status().isUnauthorized());
    }

    /* - - - - - - - - - - - - - - - - - - - Comment Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void getCommentsTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post1 = new Post("test1", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment1 = new Comment(user, post1, "text1", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment2 = new Comment(user, post1, "text2", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment3 = new Comment(user, post1, "text3", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment4 = new Comment(user, post1, "text4", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment5 = new Comment(user, post1, "text5", LocalDateTime.now());
        postRepository.save(post1);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.saveAndFlush(comment5);

        MvcResult result = mvc.perform(get("/comments")
                .param("offset", "2")
                .param("limit", "2")
                .param("postID", String.valueOf(post1.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        List<CommentDTO> list = getList(result, CommentDTO.class);
        CommentDTO CommentDTO1 = new CommentDTO(user.getName(), comment3.getText(), comment3.getDate(),
                comment3.getId(), user.getId());
        CommentDTO CommentDTO2 = new CommentDTO(user.getName(), comment2.getText(), comment2.getDate(),
                comment2.getId(), user.getId());

        assertEquals(list.size(), 2);
        assertEquals(list.get(0), CommentDTO1);
        assertEquals(list.get(1), CommentDTO2);
    }

    @Test
    public void createCommentTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post = new Post("test1", LocalDateTime.now(), user, "picture.png");
        postRepository.saveAndFlush(post);
        long before = commentRepository.getNumberOfCommentsInThePost(post.getId());
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userID", user.getId());
        jsonObject.addProperty("postID", post.getId());
        jsonObject.addProperty("text", "commentText");

        mvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser(user.getId(),"user"))))
                .andExpect(status().isCreated());

        long after = postRepository.getNumberOfAllPosts();
        assertNotEquals(before, after);

        mvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isUnauthorized());

        jsonObject.addProperty("text", "");

        mvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString())
                .with(user(getMockUser(user.getId(),"user"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteCommentTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post = new Post("test1", LocalDateTime.now(), user, "picture.png");
        Comment comment = new Comment(user, post, "testText", LocalDateTime.now());
        commentRepository.saveAndFlush(comment);

        mvc.perform(delete("/comments/" + comment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("userID", String.valueOf(user.getId())))
                .andExpect(status().isUnauthorized());

        mvc.perform(delete("/comments/" + comment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("userID", String.valueOf(user.getId()))
                .with(user(getMockUser(user.getId(), "user"))))
                .andExpect(status().isOk());
    }

    @Test
    public void getCommentsLengthTest() throws Exception {
        User user = new User("test", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        Post post1 = new Post("test1", LocalDateTime.now(), user, "picture.png");
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment1 = new Comment(user, post1, "text1", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment2 = new Comment(user, post1, "text2", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment3 = new Comment(user, post1, "text3", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment4 = new Comment(user, post1, "text4", LocalDateTime.now());
        TimeUnit.MILLISECONDS.sleep(30);
        Comment comment5 = new Comment(user, post1, "text5", LocalDateTime.now());
        postRepository.save(post1);
        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);
        commentRepository.saveAndFlush(comment5);

        MvcResult result = mvc.perform(get("/comments/length")
                .param("offset", "2")
                .param("limit", "2")
                .param("postID", String.valueOf(post1.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(), "5");
    }

    /* - - - - - - - - - - - - - - - - - - - Message Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void getChatMessagesTest() throws Exception {
        User user1 = new User("test1", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user2 = new User("test2", "test2@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user3 = new User("test3", "test3@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        userRepository.save(user3);
        Message message1 = new Message("text1", LocalDateTime.now(), user1, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message2 = new Message("text2", LocalDateTime.now(), user2, user1);
        message2.setDeletedBySender(true);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message3 = new Message("text3", LocalDateTime.now(), user2, user1);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message4 = new Message("text4", LocalDateTime.now(), user1, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message5 = new Message("text5", LocalDateTime.now(), user2, user1);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.saveAndFlush(message5);

        MvcResult result1 = mvc.perform(get("/messages/chat")
                .param("userID", String.valueOf(user1.getId()))
                .param("anotherUserID", String.valueOf(user2.getId()))
                .param("offset", "2")
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user1.getId(), "user"))))
                .andExpect(status().isOk())
                .andReturn();

        List<MessageDTO> list1 = getList(result1, MessageDTO.class);
        assertEquals(list1.get(0), new MessageDTO(message3.getText(), message3.getDate(),
                message3.getSender().getId(), message3.getReceiver().getId(),
                message3.getSender().getName(), message3.getReceiver().getName()));
        assertEquals(list1.get(1), new MessageDTO(message2.getText(), message2.getDate(),
                message2.getSender().getId(), message2.getReceiver().getId(),
                message2.getSender().getName(), message2.getReceiver().getName()));

        MvcResult result2 = mvc.perform(get("/messages/chat")
                .param("userID", String.valueOf(user2.getId()))
                .param("anotherUserID", String.valueOf(user1.getId()))
                .param("offset", "2")
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user2.getId(), "user"))))
                .andExpect(status().isOk())
                .andReturn();

        List<MessageDTO> list2 = getList(result2, MessageDTO.class);
        assertEquals(list2.get(0), new MessageDTO(message3.getText(), message3.getDate(),
                message3.getSender().getId(), message3.getReceiver().getId(),
                message3.getSender().getName(), message3.getReceiver().getName()));
        assertEquals(list2.get(1), new MessageDTO(message1.getText(), message1.getDate(),
                message1.getSender().getId(), message1.getReceiver().getId(),
                message1.getSender().getName(), message1.getReceiver().getName()));

        mvc.perform(get("/messages/chat")
                .param("userID", String.valueOf(user2.getId()))
                .param("anotherUserID", String.valueOf(user1.getId()))
                .param("offset", "2")
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user3.getId(), "user"))))
                .andExpect(status().isNoContent());

        mvc.perform(get("/messages/chat")
                .param("userID", String.valueOf(user2.getId()))
                .param("anotherUserID", String.valueOf(user1.getId()))
                .param("offset", "2")
                .param("limit", "2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getMessagesTest() throws Exception {
        User user1 = new User("test1", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user2 = new User("test2", "test2@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user3 = new User("test3", "test3@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user4 = new User("test4", "test4@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        Message message1 = new Message("text1", LocalDateTime.now(), user1, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message2 = new Message("text2", LocalDateTime.now(), user2, user1);
        message2.setDeletedBySender(true);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message3 = new Message("text3", LocalDateTime.now(), user3, user1);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message4 = new Message("text4", LocalDateTime.now(), user4, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message5 = new Message("text5", LocalDateTime.now(), user1, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message6 = new Message("text6", LocalDateTime.now(), user3, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message7 = new Message("text7", LocalDateTime.now(), user4, user1);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message8 = new Message("text8", LocalDateTime.now(), user4, user2);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message9 = new Message("text9", LocalDateTime.now(), user2, user4);
        TimeUnit.MILLISECONDS.sleep(30);
        Message message10 = new Message("text10", LocalDateTime.now(), user1, user4);
        message10.setDeletedBySender(true);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.save(message4);
        messageRepository.save(message5);
        messageRepository.save(message6);
        messageRepository.save(message7);
        messageRepository.save(message8);
        messageRepository.save(message9);
        messageRepository.saveAndFlush(message10);

        MvcResult result1 = mvc.perform(get("/messages/list")
                .param("userID", String.valueOf(user1.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user1.getId(), "user"))))
                .andExpect(status().isOk())
                .andReturn();
        List<MessageDTO> list1 = getList(result1, MessageDTO.class);
        assertEquals(list1.get(0), new MessageDTO(message7.getText(), message7.getDate(),
                message7.getSender().getId(), message7.getReceiver().getId(),
                message7.getSender().getName(), message7.getReceiver().getName()));
        assertEquals(list1.get(1), new MessageDTO(message5.getText(), message5.getDate(),
                message5.getSender().getId(), message5.getReceiver().getId(),
                message5.getSender().getName(), message5.getReceiver().getName()));
        assertEquals(list1.get(2), new MessageDTO(message3.getText(), message3.getDate(),
                message3.getSender().getId(), message3.getReceiver().getId(),
                message3.getSender().getName(), message3.getReceiver().getName()));

        MvcResult result2 = mvc.perform(get("/messages/list")
                .param("userID", String.valueOf(user4.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user4.getId(), "user"))))
                .andExpect(status().isOk())
                .andReturn();
        List<MessageDTO> list2 = getList(result2, MessageDTO.class);
        assertEquals(list2.get(0), new MessageDTO(message10.getText(), message10.getDate(),
                message10.getSender().getId(), message10.getReceiver().getId(),
                message10.getSender().getName(), message10.getReceiver().getName()));
        assertEquals(list2.get(1), new MessageDTO(message9.getText(), message9.getDate(),
                message9.getSender().getId(), message9.getReceiver().getId(),
                message9.getSender().getName(), message9.getReceiver().getName()));

        mvc.perform(get("/messages/list")
                .param("userID", String.valueOf(user1.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user4.getId(), "user"))))

                .andExpect(status().isNoContent());
        mvc.perform(get("/messages/list")
                .param("userID", String.valueOf(user1.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void downloadHistoryMessageTest() throws Exception {
        User user1 = new User("test1", "test1@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user2 = new User("test2", "test2@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        User user3 = new User("test3", "test3@tut.by", "avatar.png",
                encoder.encode("123"), "user");
        Message message1 = new Message("text1",
                LocalDateTime.of(1993, 1, 2, 12, 1, 10, 160),
                user1, user2);
        Message message2 = new Message("text2",
                LocalDateTime.of(1993, 1, 3, 13, 2, 11, 170),
                user2, user1);
        message2.setDeletedBySender(true);
        Message message3 = new Message("text3",
                LocalDateTime.of(1993, 1, 4, 14, 3, 12, 180),
                user3, user1);
        Message message4 = new Message("text5",
                LocalDateTime.of(1993, 1, 5, 15, 4, 13, 190),
                user1, user2);
        messageRepository.save(message1);
        messageRepository.save(message2);
        messageRepository.save(message3);
        messageRepository.saveAndFlush(message4);

        MvcResult result = mvc.perform(get("/messages/history")
                .param("userID", String.valueOf(user1.getId()))
                .param("anotherUserID", String.valueOf(user2.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user1.getId(), "user"))))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(result.getResponse().getContentAsString(),
                "Me: \ttext1\t1993-01-02 12:01:10.0\n" +
                      "test2: \ttext2\t1993-01-03 13:02:11.0\n" +
                      "Me: \ttext5\t1993-01-05 15:04:13.0\n");

        mvc.perform(get("/messages/history")
                .param("userID", String.valueOf(user1.getId()))
                .param("anotherUserID", String.valueOf(user2.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .with(user(getMockUser(user2.getId(), "user"))))
                .andExpect(status().isNoContent());

        mvc.perform(get("/messages/history")
                .param("userID", String.valueOf(user1.getId()))
                .param("anotherUserID", String.valueOf(user2.getId()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    /* - - - - - - - - - - - - - - - - - - - Login Logout Test - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void loginLogoutTest() throws Exception {
        mvc.perform(logout());

        User user = new User("testUser", "test@tut.by", "avatar.png",
                encoder.encode("123"), "boss");
        userRepository.saveAndFlush(user);

        MvcResult result = mvc.perform(formLogin("/login")
                .user("email", "test@tut.by")
                .password("password", "123")
                .acceptMediaType(MediaType.APPLICATION_JSON))
                .andExpect(authenticated())
                .andReturn();

        UserDTO expectedUser = new UserDTO(user.getId(), user.getName(), user.getAvatar(), user.getRole());
        UserDTO actualUser = toObject(result, UserDTO.class);

        assertEquals(expectedUser, actualUser);

        mvc.perform(logout());
    }

    /* - - - - - - - - - - - - - - - - - - - Pilot Controller - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void getLocaleTest() throws Exception {
        MvcResult result1 = mvc.perform(get("/locale")
                .param("locale", "en")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        MvcResult result2 = mvc.perform(get("/locale")
                .param("locale", "ru")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        ResourceBundle rb1 = ResourceBundle.getBundle("localization_en");
        ResourceBundle rb2 = ResourceBundle.getBundle("localization_ru");
        JsonObject json1 = new JsonObject();
        JsonObject json2 = new JsonObject();

        Enumeration<String> listOfKeys1 = rb1.getKeys();
        Enumeration<String> listOfKeys2 = rb2.getKeys();
        String nextElement;

        while (listOfKeys1.hasMoreElements() && listOfKeys2.hasMoreElements()) {
            nextElement = listOfKeys1.nextElement();
            json1.addProperty(nextElement, rb1.getString(nextElement));
            nextElement = listOfKeys2.nextElement();
            json2.addProperty(nextElement, rb2.getString(nextElement));
        }

        assertEquals(json1.size(), json2.size());
        assertTrue(!result1.getResponse().getContentAsString().isEmpty());
        assertEquals(json1.toString(), result1.getResponse().getContentAsString());
        assertEquals(json2.toString(), result2.getResponse().getContentAsString());
    }

    /* - - - - - - - - - - - - - - - - - - - StaticResources Controller - - - - - - - - - - - - - - - - - - - - - - - */
    @Test
    public void staticResourcesTest() throws Exception {
        String index = "<!DOCTYPE html>";
        MvcResult result1 = mvc.perform(get("/login/?story/1?home#0")).andReturn();
        MvcResult result2 = mvc.perform(get("/story/1?home#0")).andReturn();
        MvcResult result3 = mvc.perform(get("/login/?home#0")).andReturn();
        MvcResult result4 = mvc.perform(get("/login")).andReturn();
        MvcResult result5 = mvc.perform(get("/chat/1")).andReturn();
        MvcResult result6 = mvc.perform(get("/home#0")).andReturn();
        MvcResult result7 = mvc.perform(get("/ban")).andReturn();

        assertTrue(result1.getResponse().getContentAsString().startsWith(index));
        assertTrue(result2.getResponse().getContentAsString().startsWith(index));
        assertTrue(result3.getResponse().getContentAsString().startsWith(index));
        assertTrue(result4.getResponse().getContentAsString().startsWith(index));
        assertTrue(result5.getResponse().getContentAsString().startsWith(index));
        assertTrue(result6.getResponse().getContentAsString().startsWith(index));
        assertTrue(result7.getResponse().getContentAsString().startsWith(index));
    }
}

