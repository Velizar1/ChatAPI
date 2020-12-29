package com.example.database.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class AppController implements CommandLineRunner {
    private final UserController userController;
    private final ChatsController chatsController;

    @Autowired
    public AppController(ChatsController chatsController, UserController userController) {
        this.chatsController = chatsController;
        this.userController = userController;
    }

    public UserController getUserController() {
        return userController;
    }

    public ChatsController getChatsController() {
        return chatsController;
    }

    @Override
    public void run(String... args) throws Exception {

   /*     UserBindingModel userBindingModel=new UserBindingModel();
        userBindingModel.setUsername("gosho");
        userBindingModel.setPassword("11");
        userBindingModel.setDeleted(false);
        userBindingModel.setCreatedAt(OffsetDateTime.now());
        UserBindingModel userBindingModel2=new UserBindingModel();
        userBindingModel2.setUsername("pesho");
        userBindingModel2.setPassword("11");
        userBindingModel2.setDeleted(false);
        userBindingModel2.setCreatedAt(OffsetDateTime.now());
        userController.addUser(userBindingModel);
        userController.addUser(userBindingModel2);*/
        //addMessageBetweenUsers(userController.findUserByUsername("gosho").getUsername()
        //  ,userController.findUserByUsername("pesho").getUsername(),"blabla");
        //addMessageBetweenUsers("pesho","gosho","ragaraga");
        //addUser("gosho3","123");
        //addUser("pesho4","321");
        //createChatBetweenUser("gosho3", "pesho4");
        //createMessageBetweenUsers("gosho3","pesho4");
        //addMessageBetweenUsers("gosho3","pesho4","blablabla");
        //addMessageBetweenUsers("pesho4","gosho3","ragaraga");

       /*Conversation conversation=getConversationBetweenUsers("gosho","pesho");

        for (String mess:conversation.getMessage()) {
            System.out.println(mess);
        }*/

       /* User user = new User("gosho1", "123");
        User user2 = new User("pesho2", "1234");
        //userRepostitory.insertUser(user.getUsername(), user.getPassword(), user.getCreatedAt(), user.isDeleted());

        Conversation conversation = new Conversation(user.getUsername(), user2.getUsername(), "blanla");
        Messages messages = new Messages();
        messages.setUserOne(user);
        messages.setUserTwo(user2);
        messages.getConversationSet().add(conversation);

        //entityManager.persist(user);
        // entityManager.persist(user2);
        entityManager.persist(messages);
        entityManager.persist(conversation);
        entityManager.flush();
        //createChatBetweenUser(conversation.getFromUser(),conversation.getToUser());*/
    }
}
