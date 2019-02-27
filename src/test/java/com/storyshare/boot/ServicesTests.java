package com.storyshare.boot;

import com.google.gson.JsonObject;
import com.storyshare.boot.pojos.Comment;
import com.storyshare.boot.pojos.Message;
import com.storyshare.boot.pojos.Post;
import com.storyshare.boot.pojos.User;
import com.storyshare.boot.repositories.dto.CommentDTO;
import com.storyshare.boot.repositories.dto.MessageDTO;
import com.storyshare.boot.repositories.dto.PostDTO;
import com.storyshare.boot.services.CommentService;
import com.storyshare.boot.services.MessageService;
import com.storyshare.boot.services.PostService;
import com.storyshare.boot.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("com.storyshare")
public class ServicesTests {
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private CommentService commentService;
    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    public void userServiceSaveAndGetTests() {
        User user1 = new User("Mark", "mark@tut.by", "avatar.jpg", "1234", "boss");
        User user2 = new User("Denis", "denis@tut.by", "avatar.png", "1234", "admin");
        User user3 = new User("Test1", "Test1@tut.by", "avatar.gif", "1234", "admin");

        User persistedUser1 = userService.save(user1);
        User persistedUser2 = userService.save(user2);
        User persistedUser3 = userService.save(user3);

        org.junit.Assert.assertNotNull(persistedUser1.getId());
        org.junit.Assert.assertNotNull(persistedUser2.getId());
        org.junit.Assert.assertNotNull(persistedUser3.getId());

        em.flush();
        em.clear();

        User foundedUser1 = userService.get(user1.getId());
        User foundedUser2 = userService.get(user2.getId());
        User foundedUser3 = userService.get(user3.getId());

        org.junit.Assert.assertEquals(user1, foundedUser1);
        org.junit.Assert.assertEquals(user2, foundedUser2);
        org.junit.Assert.assertEquals(user3, foundedUser3);
    }

    @Test
    @Transactional
    public void userServiceUpdate() {
        User user1 = new User("Mark", "mark@tut.by", "avatar.jpg", "1234", "boss");
        User user2 = new User("Denis", "denis@tut.by", "avatar.jpg", "1234", "admin");
        User user3 = new User("Test1", "Test1@tut.by", "avatar.jpg", "1234", "admin");
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        em.flush();
        em.clear();

        User foundedUser1 = userService.get(user1.getId());
        User foundedUser2 = userService.get(user2.getId());
        User foundedUser3 = userService.get(user3.getId());

        foundedUser1.setStatus("banned");
        foundedUser2.setEmail("test@tut.by");
        foundedUser3.setPassword("test");

        userService.update(foundedUser1);
        userService.update(foundedUser2);
        userService.update(foundedUser3);

        em.flush();
        em.clear();

        User updatedUser1 = userService.get(user1.getId());
        User updatedUser2 = userService.get(user2.getId());
        User updatedUser3 = userService.get(user3.getId());

        org.junit.Assert.assertEquals(updatedUser1, foundedUser1);
        org.junit.Assert.assertEquals(updatedUser2, foundedUser2);
        org.junit.Assert.assertEquals(updatedUser3, foundedUser3);
    }

    @Test
    @Transactional
    public void userServiceDeleteTest() {
        User user1 = new User("Mark", "mark@tut.by", "avatar.jpg", "1234", "boss");
        User user2 = new User("Denis", "denis@tut.by", "avatar.jpg", "1234", "admin");
        User user3 = new User("Test1", "Test1@tut.by", "avatar.jpg", "1234", "admin");
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);

        em.flush();
        em.clear();

        User foundedUser1 = userService.get(user1.getId());
        User foundedUser2 = userService.get(user2.getId());
        User foundedUser3 = userService.get(user3.getId());

        org.junit.Assert.assertEquals(user1, foundedUser1);
        org.junit.Assert.assertEquals(user2, foundedUser2);
        org.junit.Assert.assertEquals(user3, foundedUser3);

        userService.delete(foundedUser1);
        userService.delete(foundedUser2);
        userService.delete(foundedUser3);

        em.flush();
        em.clear();

        User deletedUser1 = userService.get(user1.getId());
        User deletedUser2 = userService.get(user2.getId());
        User deletedUser3 = userService.get(user3.getId());

        org.junit.Assert.assertNull(deletedUser1);
        org.junit.Assert.assertNull(deletedUser2);
        org.junit.Assert.assertNull(deletedUser3);
    }

    @Test
    @Transactional
    public void userServiceGetUserByEmailTest() {
        User user = new User("test", "test@tut.by", "test.jpg", "123", "user");
        userService.save(user);

        em.flush();
        em.clear();

        User foundedUserByEmail = userService.getUserByEmail(user.getEmail());
        org.junit.Assert.assertEquals(foundedUserByEmail, user);
    }

    @Test
    @Transactional
    public void userServiceAssignAdminTest() {
        User user = new User("test", "test@tut.by", "test.jpg", "1234", "user");
        userService.save(user);

        em.flush();
        em.clear();

        userService.assignAdmin(user.getId());

        em.flush();
        em.clear();

        User assignedAsAdmin = userService.get(user.getId());
        user.setRole("admin");
        org.junit.Assert.assertEquals(assignedAsAdmin.getRole(), user.getRole());
    }

    @Test
    @Transactional
    public void userServiceAssignUserTest() {
        User user = new User("test", "test@tut.by", "test.jpg", "12345", "admin");
        userService.save(user);

        em.flush();
        em.clear();

        userService.assignUser(user.getId());

        em.flush();
        em.clear();

        User assignedAsUser = userService.get(user.getId());
        user.setRole("user");
        org.junit.Assert.assertEquals(assignedAsUser.getRole(), user.getRole());
    }

    @Test
    @Transactional
    public void userServiceBanUserTest() {
        User user = new User("test", "test@tut.by", "test.jpg", "123456", "user");
        userService.save(user);

        em.flush();
        em.clear();

        userService.banUser(user);

        em.flush();
        em.clear();

        User bannedUser = userService.get(user.getId());
        user.setStatus("banned");
        org.junit.Assert.assertEquals(bannedUser.getStatus(), user.getStatus());
    }

    @Test
    @Transactional
    public void userServiceUnbanUserTest() {
        User user = new User("test", "test@tut.by", "test.jpg", "1234567", "user");
        user.setStatus("banned");
        userService.save(user);

        em.flush();
        em.clear();

        userService.unbanUser(user);

        em.flush();
        em.clear();

        User unbannedUser = userService.get(user.getId());
        user.setStatus("active");
        org.junit.Assert.assertEquals(unbannedUser.getStatus(), user.getStatus());
    }

    @Test
    @Transactional
    public void userServiceGetUserAvatarTest() {
        User user = new User("test1", "test@tut.by", "test.jpg", "123", "user");
        userService.save(user);

        em.flush();
        em.clear();

        String avatar = userService.getUserAvatar(user.getId());
        org.junit.Assert.assertEquals(user.getAvatar(), avatar);
    }

    @Test
    @Transactional
    public void userServiceGetUserNameTest() {
        User user = new User("test1", "test1@tut.by", "test.jpg", "1234", "user");
        userService.save(user);

        em.flush();
        em.clear();

        String name = userService.getUserName(user.getId());
        org.junit.Assert.assertEquals(user.getName(), name);
    }

    @Test
    @Transactional
    public void userServiceGetUserRoleAndStatusTest() {
        User user = new User("test1", "test1@tut.by", "test.jpg", "1234", "user");
        JsonObject expectedRoleAndStatus = new JsonObject();
        expectedRoleAndStatus.addProperty("role", user.getRole());
        expectedRoleAndStatus.addProperty("status", user.getStatus());
        userService.save(user);

        em.flush();
        em.clear();

        JsonObject actualRoleAndStatus = userService.getUserRoleAndStatus(user.getId());
        org.junit.Assert.assertEquals(expectedRoleAndStatus, actualRoleAndStatus);
    }

    @Test
    @Transactional
    public void messageServiceGetAllUserMessagesWithOtherUserTest() throws InterruptedException {
        User user1 = new User("test1", "test1@tut.by", "test1.jpg", "1231", "user");
        User user2 = new User("test2", "test2@tut.by", "test2.jpg", "1232", "user");
        User user3 = new User("test3", "test3@tut.by", "test3.jpg", "1233", "user");
        User user4 = new User("test4", "test4@tut.by", "test4.jpg", "1234", "user");

        Message message1 = new Message("1", LocalDateTime.now(), user1, user2);
        Thread.sleep(30);
        Message message2 = new Message("2", LocalDateTime.now(), user2, user1);
        Thread.sleep(30);
        Message message3 = new Message("3", LocalDateTime.now(), user1, user3);
        Thread.sleep(30);
        Message message4 = new Message("4", LocalDateTime.now(), user4, user1);
        Thread.sleep(30);
        Message message5 = new Message("5", LocalDateTime.now(), user1, user3);
        Thread.sleep(30);
        Message message6 = new Message("6", LocalDateTime.now(), user3, user1);
        message6.setDeletedByReceiver(true);

        messageService.save(message1);
        messageService.save(message2);
        messageService.save(message3);
        messageService.save(message4);
        messageService.save(message5);
        messageService.save(message6);

        em.flush();
        em.clear();

        List<MessageDTO> messageDTOList1 = messageService.getAllUserMessagesWithOtherUser(user1.getId(), user3.getId());
        org.junit.Assert.assertEquals(messageDTOList1.size(), 2);
        org.junit.Assert.assertEquals(messageDTOList1.get(0).getText(), message3.getText());
        org.junit.Assert.assertEquals(messageDTOList1.get(0).getDate().toLocalDateTime(), message3.getDate());
        org.junit.Assert.assertEquals(messageDTOList1.get(1).getText(), message5.getText());
        org.junit.Assert.assertEquals(messageDTOList1.get(1).getDate().toLocalDateTime(), message5.getDate());
        org.junit.Assert.assertEquals(messageService.getAllUserMessagesWithOtherUser(user1.getId(), user2.getId()).size(), 2);
    }

    @Test
    @Transactional
    public void messageServiceGetLastMessagesInUsersDialogsTest() throws InterruptedException {
        User user1 = new User("test1", "test1@tut.by", "test1.jpg", "12311", "user");
        User user2 = new User("test2", "test2@tut.by", "test2.jpg", "12322", "user");
        User user3 = new User("test3", "test3@tut.by", "test3.jpg", "12333", "user");
        User user4 = new User("test4", "test4@tut.by", "test4.jpg", "12344", "user");

        Message message1 = new Message("1", LocalDateTime.now(), user1, user2);
        Thread.sleep(30);
        Message message2 = new Message("2", LocalDateTime.now(), user2, user1);
        Thread.sleep(30);
        Message message3 = new Message("3", LocalDateTime.now(), user1, user3);
        Thread.sleep(30);
        Message message4 = new Message("4", LocalDateTime.now(), user4, user1);
        Thread.sleep(30);
        Message message5 = new Message("5", LocalDateTime.now(), user1, user3);
        Thread.sleep(30);
        Message message6 = new Message("6", LocalDateTime.now(), user3, user1);
        message6.setDeletedByReceiver(true);

        messageService.save(message1);
        messageService.save(message2);
        messageService.save(message3);
        messageService.save(message4);
        messageService.save(message5);
        messageService.save(message6);

        em.flush();
        em.clear();

        List<MessageDTO> messageDTOList2 = messageService.getLastMessagesInUsersDialogs(user1.getId());
        org.junit.Assert.assertEquals(messageDTOList2.get(0).getText(), message5.getText());
        org.junit.Assert.assertEquals(messageDTOList2.get(1).getText(), message4.getText());
        org.junit.Assert.assertEquals(messageDTOList2.get(2).getText(), message2.getText());
    }

    @Test
    @Transactional
    public void messageServiceDeleteMessageInCertainUserTest() throws InterruptedException {
        User user1 = new User("test1", "test11@tut.by", "test1.jpg", "1231", "user");
        User user2 = new User("test2", "test22@tut.by", "test2.jpg", "1232", "user");

        Message message1 = new Message("11", LocalDateTime.now(), user1, user2);
        Thread.sleep(30);
        Message message2 = new Message("22", LocalDateTime.now(), user2, user1);
        Thread.sleep(30);

        messageService.save(message1);
        messageService.save(message2);

        em.flush();
        em.clear();

        org.junit.Assert.assertEquals(messageService.get(message1.getId()).getDeletedBySender(), false);
        messageService.deleteMessageInCertainUser(user1.getId(), message1.getId());

        em.flush();
        em.clear();

        org.junit.Assert.assertEquals(messageService.get(message1.getId()).getDeletedBySender(), true);
    }

    @Test
    @Transactional
    public void messageServiceGetUserMessagesWithOtherUserWithOffsetTest() throws InterruptedException {
        User user1 = new User("test1", "test1@tut.by", "test1.jpg", "12311", "user");
        User user2 = new User("test2", "test2@tut.by", "test2.jpg", "12322", "user");
        User user3 = new User("test3", "test3@tut.by", "test3.jpg", "12333", "user");

        Message message1 = new Message("1", LocalDateTime.now(), user1, user2);
        Thread.sleep(30);
        Message message2 = new Message("2", LocalDateTime.now(), user2, user1);
        Thread.sleep(30);
        Message message3 = new Message("3", LocalDateTime.now(), user1, user3);
        Thread.sleep(30);
        Message message4 = new Message("4", LocalDateTime.now(), user1, user3);
        Thread.sleep(30);
        Message message5 = new Message("5", LocalDateTime.now(), user3, user1);
        message5.setDeletedByReceiver(true);
        Thread.sleep(30);
        Message message6 = new Message("6", LocalDateTime.now(), user3, user1);

        messageService.save(message1);
        messageService.save(message2);
        messageService.save(message3);
        messageService.save(message4);
        messageService.save(message5);
        messageService.save(message6);

        em.flush();
        em.clear();

        List<MessageDTO> messageDTOList3 = messageService.getUserMessagesWithOtherUserWithOffset(
                user1.getId(), user3.getId(), PageRequest.of(1, 2));
        org.junit.Assert.assertEquals(messageDTOList3.size(), 1);
        org.junit.Assert.assertEquals(messageDTOList3.get(0).getText(), message3.getText());
        org.junit.Assert.assertEquals(messageDTOList3.get(0).getDate().toLocalDateTime(), message3.getDate());
    }

    @Test
    @Transactional
    public void commentServiceGetNumberOfCommentsInThePostTest() throws InterruptedException {
        User user1 = new User("test1", "test1@tut.by", "test1.jpg", "123", "user");
        User user2 = new User("test2", "test2@tut.by", "test2.jpg", "123", "user");

        Post post1 = new Post("1111", LocalDateTime.now(), user1, "test.jpg");
        Thread.sleep(30);
        Post post2 = new Post("2222", LocalDateTime.now(), user2, "test.jpg");
        Thread.sleep(30);

        Comment comment1 = new Comment(user1, post1, "comment1", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment2 = new Comment(user1, post2, "comment2", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment3 = new Comment(user2, post1, "comment3", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment4 = new Comment(user2, post1, "comment4", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment5 = new Comment(user1, post1, "comment5", LocalDateTime.now());

        commentService.save(comment1);
        commentService.save(comment2);
        commentService.save(comment3);
        commentService.save(comment4);
        commentService.save(comment5);

        em.flush();
        em.clear();

        org.junit.Assert.assertEquals(commentService.getNumberOfCommentsInThePost(post1.getId()), 4);
        org.junit.Assert.assertEquals(commentService.getNumberOfCommentsInThePost(post2.getId()), 1);
    }

    @Test
    @Transactional
    public void commentServiceGetCommentsInThePostWithOffsetAndLimitTest() throws InterruptedException {
        User user1 = new User("test11", "test1@tut.by", "test1.jpg", "123", "user");
        User user2 = new User("test22", "test2@tut.by", "test2.jpg", "123", "user");

        Post post1 = new Post("1111", LocalDateTime.now(), user1, "test.jpg");
        Thread.sleep(30);
        Post post2 = new Post("2222", LocalDateTime.now(), user2, "test.jpg");
        Thread.sleep(30);

        Comment comment1 = new Comment(user1, post1, "comment11", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment2 = new Comment(user1, post2, "comment22", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment3 = new Comment(user2, post1, "comment33", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment4 = new Comment(user2, post1, "comment44", LocalDateTime.now());
        Thread.sleep(30);
        Comment comment5 = new Comment(user1, post1, "comment55", LocalDateTime.now());

        commentService.save(comment1);
        commentService.save(comment2);
        commentService.save(comment3);
        commentService.save(comment4);
        commentService.save(comment5);

        em.flush();
        em.clear();

        List<CommentDTO> commentDTOList = commentService.getCommentsInThePostWithOffsetAndLimit(post1.getId(),
                PageRequest.of(1, 2));
        org.junit.Assert.assertEquals(commentDTOList.get(0).getText(), comment3.getText());
        org.junit.Assert.assertEquals(commentDTOList.get(1).getText(), comment1.getText());
    }

    @Test
    @Transactional
    public void postServiceLikeUnlikePostByUserTest() throws InterruptedException {
        User user1 = new User("test11", "test1@tut.by", "test1.jpg", "123", "user");
        User user2 = new User("test22", "test2@tut.by", "test2.jpg", "123", "user");

        Post post1 = new Post("1111", LocalDateTime.now(), user1, "test.jpg");
        Thread.sleep(30);
        Post post2 = new Post("2222", LocalDateTime.now(), user2, "test.jpg");
        postService.save(post1);
        postService.save(post2);

        em.flush();
        em.clear();

        postService.likePostByUser(post1.getId(), user1.getId());
        postService.likePostByUser(post2.getId(), user2.getId());

        em.flush();
        em.clear();

        PostDTO expectedPost1 = postService.getSinglePostDTO(user1.getId(), post1.getId());
        PostDTO expectedPost2 = postService.getSinglePostDTO(user1.getId(), post2.getId());

        org.junit.Assert.assertTrue(expectedPost1.getIsLiked());
        org.junit.Assert.assertFalse(expectedPost2.getIsLiked());

        em.flush();
        em.clear();

        postService.unlikePostByUser(post1.getId(), user1.getId());

        PostDTO expectedUnlikedPost = postService.getSinglePostDTO(user1.getId(), post1.getId());
        org.junit.Assert.assertFalse(expectedUnlikedPost.getIsLiked());
    }

    @Test
    @Transactional
    public void postServiceGetNumberOfAllPostsTest() {
        User user1 = new User("test11", "test1@tut.by", "test1.jpg", "123", "user");
        User user2 = new User("test22", "test2@tut.by", "test2.jpg", "123", "user");

        Post post1 = new Post("1111", LocalDateTime.now(), user1, "test.jpg");
        Post post2 = new Post("2222", LocalDateTime.now(), user2, "test.jpg");
        Post post3 = new Post("3333", LocalDateTime.now(), user2, "test.jpg");
        Post post4 = new Post("4444", LocalDateTime.now(), user2, "test.jpg");
        Post post5 = new Post("5555", LocalDateTime.now(), user2, "test.jpg");
        postService.save(post1);
        postService.save(post2);
        postService.save(post3);
        postService.save(post4);
        postService.save(post5);

        em.flush();
        em.clear();

        org.junit.Assert.assertEquals(postService.getNumberOfAllPosts(), 5);
    }

    @Test
    @Transactional
    public void postServiceGetSinglePostDTOTest() throws InterruptedException {
        User user1 = new User("test1", "test1@tut.by", "test1.jpg", "123", "user");
        User user2 = new User("test2", "test2@tut.by", "test2.jpg", "123", "user");

        Post post1 = new Post("1111", LocalDateTime.now(), user1, "test.jpg");
        Thread.sleep(30);
        Post post2 = new Post("2222", LocalDateTime.now(), user2, "test.jpg");
        Thread.sleep(30);
        Post post3 = new Post("3333", LocalDateTime.now(), user1, "test.jpg");

        postService.save(post1);
        postService.save(post2);
        postService.save(post3);

        em.flush();
        em.clear();

        org.junit.Assert.assertEquals(postService.get(post1.getId()), post1);
        org.junit.Assert.assertEquals(postService.get(post2.getId()), post2);
        org.junit.Assert.assertEquals(postService.get(post3.getId()), post3);

        PostDTO actualPost1 = new PostDTO(post1.getId(), user1.getId(), post1.getText(), post1.getDate(),
                user1.getName(), user1.getAvatar(), post1.getPicture(),0, false);
        PostDTO expectedPost1 = postService.getSinglePostDTO(user1.getId(), post1.getId());
        org.junit.Assert.assertEquals(actualPost1, expectedPost1);

        em.flush();
        em.clear();

        postService.likePostByUser(post1.getId(), user1.getId());
        postService.likePostByUser(post1.getId(), user2.getId());

        em.flush();
        em.clear();

        PostDTO actualPost2 = new PostDTO(post1.getId(), user1.getId(), post1.getText(), post1.getDate(),
                user1.getName(), user1.getAvatar(), post1.getPicture(),2, true);
        PostDTO expectedPost2 = postService.getSinglePostDTO(user1.getId(), post1.getId());
        org.junit.Assert.assertEquals(actualPost2, expectedPost2);
    }

    @Test
    @Transactional
    public void postServiceGetPostsWithPaginationTest() throws InterruptedException {
        User user1 = new User("test1", "test1@tut.by", "test1.jpg", "123", "user");
        User user2 = new User("test2", "test2@tut.by", "test2.jpg", "123", "user");
        User user3 = new User("test3", "test3@tut.by", "test3.jpg", "123", "user");

        Post post1 = new Post("1111", LocalDateTime.now(), user1, "test.jpg");
        Thread.sleep(30);
        Post post2 = new Post("2222", LocalDateTime.now(), user2, "test.jpg");
        Thread.sleep(30);
        Post post3 = new Post("3333", LocalDateTime.now(), user3, "test.jpg");
        Thread.sleep(30);
        Post post4 = new Post("4444", LocalDateTime.now(), user3, "test.jpg");
        Thread.sleep(30);
        Post post5 = new Post("5555", LocalDateTime.now(), user1, "test.jpg");

        postService.save(post1);
        postService.save(post2);
        postService.save(post3);
        postService.save(post4);
        postService.save(post5);

        em.flush();
        em.clear();

        postService.likePostByUser(post3.getId(), user1.getId());
        postService.likePostByUser(post2.getId(), user2.getId());

        em.flush();
        em.clear();

        List<PostDTO> listPostDTO = postService.getPostsWithPagination(user1.getId(), PageRequest.of(1, 2));

        org.junit.Assert.assertEquals(listPostDTO.get(0).getText(), post3.getText());
        org.junit.Assert.assertEquals(listPostDTO.get(1).getText(), post2.getText());
        org.junit.Assert.assertTrue(listPostDTO.get(0).getIsLiked());
        org.junit.Assert.assertFalse(listPostDTO.get(1).getIsLiked());
    }
}

