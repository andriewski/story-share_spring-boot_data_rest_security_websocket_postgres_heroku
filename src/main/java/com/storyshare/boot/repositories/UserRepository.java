package com.storyshare.boot.repositories;

import com.storyshare.boot.pojos.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.avatar FROM User u WHERE u.id = :userID")
    String getUserAvatar(@Param("userID") long userID);

    @Query("SELECT u.name FROM User u WHERE u.id = :userID")
    String getUserName(@Param("userID") long userID);

    @Query("SELECT u.status FROM User u WHERE u.id = :userID")
    String getUserStatus(@Param("userID") long userID);

    @Query("SELECT u.role FROM User u WHERE u.id = :userID")
    String getUserRole(@Param("userID") long userID);

    User findByEmail(String email);
}
