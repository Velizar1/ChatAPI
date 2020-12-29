package com.example.database.Repositories;

import com.example.database.Entities.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
@Transactional
@Repository
public interface UserRepostitory extends JpaRepository<User,Integer> {

    User findUserByUsernameAndPassword(String username, String password);

    User findUserByUsername(String username);

    ArrayList<User> findAllBy();

    ArrayList<User> findAllByOrderByUsername();

    ArrayList<User> findAllByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);

    boolean existsByUsername(String username);

    @Modifying
    @Query(value = "update users u set u.deleted = ? where u.username = ?",
            nativeQuery = true)
    int updateUserStatus(boolean status, String name);
}
