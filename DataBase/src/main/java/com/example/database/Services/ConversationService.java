package com.example.database.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConversationService {


    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public ConversationService(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    public boolean createChatBetweenUser(String user1, String user2) {
        try {
            Query query = entityManager.createNativeQuery("create table if not exists " + user1 + "_" + user2 + "_conversation" +
                    "   ( PID INT NOT NULL AUTO_INCREMENT, \n" +
                    "    PRIMARY KEY(PID), " +
                    "    from_user VARCHAR(32),\n" +
                    "    to_user VARCHAR(32),\n" +
                    "    message VARCHAR(10000) COLLATE utf8_unicode_ci,\n" +
                    "    local_time timestamp DEFAULT CURRENT_TIMESTAMP ,\n" +
                    " FOREIGN KEY (from_user) REFERENCES users(username),\n" +
                    " FOREIGN KEY (to_user) REFERENCES users(username));");
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List getAllMessagesByTwoUsersDatesFromTo(String userOne, String userTwo, String from, String to) {
        try {
            if (checkExistenceOfDB(userOne + "_" + userTwo + "_conversation")) {
                return entityManager.createNativeQuery("select * from " + userOne + "_" + userTwo + "_conversation\n" +
                        "where local_time BETWEEN '" + from + "' and '" + to + "'")
                        .getResultList();
            } else if (checkExistenceOfDB(userTwo + "_" + userOne + "_conversation")) {
                return entityManager.createNativeQuery("select * from " + userTwo + "_" + userOne + "_conversation\n" +
                        "where local_time BETWEEN '" + from + "' and '" + to + "'")
                        .getResultList();
            } else {
                if (!createChatBetweenUser(userOne, userTwo)) return null;
                return getAllMessagesByTwoUsersDatesFromTo(userOne, userTwo, from, to);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object[]> getAllMessagesByTwoUsers(String userOne, String userTwo) {
        try {
            if (checkExistenceOfDB(userOne + "_" + userTwo + "_conversation")) {
                return entityManager.createNativeQuery("select * from " + userOne + "_" + userTwo + "_conversation")
                        .getResultList();
            } else if (checkExistenceOfDB(userTwo + "_" + userOne + "_conversation")) {
                return entityManager.createNativeQuery("select * from " + userTwo + "_" + userOne + "_conversation")
                        .getResultList();
            } else {
                if (!createChatBetweenUser(userOne, userTwo)) return null;
                return getAllMessagesByTwoUsers(userOne, userTwo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private final boolean checkExistenceOfDB(String dbName) {
        try {
            if (entityManager.createNativeQuery("SHOW TABLES")
                    .getResultList().contains(dbName)) {
                return true;
            } else return false;
        } catch (Exception e) {
            return false;
        }
    }

    private Query addQuery(String userOne, String userTwo, String message) {
        try {
            if (checkExistenceOfDB(userOne + "_" + userTwo + "_conversation")) {
                return entityManager.createNativeQuery("INSERT into " + userOne + "_" + userTwo + "_conversation" +
                        "(from_user,to_user,message)\n" +
                        "values (:from_user,:to_user,:message)")
                        .setParameter("from_user", userOne)
                        .setParameter("to_user", userTwo)
                        .setParameter("message", message);
            } else if (checkExistenceOfDB(userTwo + "_" + userOne + "_conversation")) {
                return entityManager.createNativeQuery("INSERT into " + userTwo + "_" + userOne + "_conversation" +
                        "(from_user,to_user,message)\n" +
                        "values (:from_user,:to_user,:message)")
                        .setParameter("from_user", userOne)
                        .setParameter("to_user", userTwo)
                        .setParameter("message", message);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addMessageBetweenUsers(String userOne, String userTwo, String message) {
        try {
            Query query = addQuery(userOne, userTwo, message);
            int val = 0;
            if (query == null) {
                if (!createChatBetweenUser(userOne, userTwo)) return false;
                addMessageBetweenUsers(userOne, userTwo, message);
            } else {
                val = query.executeUpdate();
            }
            return val != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
