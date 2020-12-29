package com.example.database.Controller;

import com.example.database.Models.Conversation.ConversationModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.database.Services.ConversationService;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@Transactional
public class ChatsController {

    private final ModelMapper modelMapper;
    private final UserController userController;
    private final ConversationService conversationService;

    @Autowired
    public ChatsController(UserController userController, ConversationService conversationService, ModelMapper modelMapper) {
        this.userController = userController;
        this.conversationService = conversationService;
        this.modelMapper = modelMapper;
    }

    private List<ConversationModel> conversationModels(List<Object[]> list) {
        List<ConversationModel> conversationModels = new ArrayList<>();

        for (Object[] o : list) {
            ConversationModel conversationModel = new ConversationModel(o[1].toString(),
                    o[2].toString(), o[3].toString(), OffsetDateTime.parse(o[4].toString()
                    .replace(" ", "T")
                    .replaceAll("\\.[0-9]", "") + "Z"));
            conversationModels.add(conversationModel);
        }

        return conversationModels;
    }
    @GetMapping("/all/{userOne}/{userTwo}")
    @ResponseBody
    ResponseEntity<List<ConversationModel>> allMessagesFromUserOneAndUserTwo(@PathVariable String userOne, @PathVariable String userTwo) {
        try {
            List<Object[]> list = conversationService.getAllMessagesByTwoUsers(userOne, userTwo);
            List<ConversationModel> conversationModels = conversationModels(list);
            return new ResponseEntity<>(conversationModels, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/all/add")
    ResponseEntity addMessageForUsers(@RequestBody ConversationModel conversationModel) {
        try {
            conversationService.addMessageBetweenUsers(conversationModel.getFrom_user(),
                    conversationModel.getTo_user(), conversationModel.getMessage());
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/all/{userOne}/{userTwo}/{n}/{from}/{to}")
    @ResponseBody
    ResponseEntity<List<ConversationModel>> topNMessagesFromUserOneAndUserTwoBetweenDates(@PathVariable String userOne,
                                                                                          @PathVariable String userTwo,
                                                                                          @PathVariable int n,
                                                                                          @PathVariable String from,
                                                                                          @PathVariable String to) {
        try {
            from = from.replace(" ", "T") + "Z";
            to = to.replace(" ", "T") + "Z";
            List<Object[]> objects = conversationService.getAllMessagesByTwoUsersDatesFromTo(userOne, userTwo, from, to);
            List<ConversationModel> conversationModels = conversationModels(objects);
            return new ResponseEntity<>(conversationModels.size() > n ? conversationModels
                    .subList(0, n) : conversationModels, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/all/{userOne}/{userTwo}/{n}")
    @ResponseBody
    ResponseEntity<List<ConversationModel>> topNMessagesFromUserOneAndUserTwo(@PathVariable String userOne,
                                                                              @PathVariable String userTwo,
                                                                              @PathVariable int n) {
        try {

            List<Object[]> objects = conversationService.getAllMessagesByTwoUsers(userOne, userTwo);
            List<ConversationModel> conversationModels = conversationModels(objects);
            return new ResponseEntity<>(conversationModels.size() > n ? conversationModels
                    .subList(0, n) : conversationModels, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

}
