package com.example.database.Repositories;

import com.example.database.Entities.AllChat;
import com.example.database.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Repository
public interface AllChatRepository extends JpaRepository<AllChat, Integer> {

    ArrayList<AllChat> findAllBy();

    ArrayList<AllChat> findAllByAddedAtBetween(OffsetDateTime from, OffsetDateTime to);

    @Modifying
    @Query(value = "INSERT into all_chat(username,message,added_at " +
            "values (:username,:message,:added_at)", nativeQuery = true)
    void addMessage(@Param("username") String username, @Param("message") String message,
                    @Param("added_at") OffsetDateTime time);

}
