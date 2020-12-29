package com.example.database.Controller;

import com.example.database.Models.Binding.AllChatBindingModel;
import com.example.database.Models.Respond.AllChatRespondModel;
import com.example.database.Models.Service.AllChatServiceModel;
import com.example.database.Services.AllChatServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AllChatController {

    private AllChatServiceImpl allChatService;
    private ModelMapper modelMapper;
    private AllChatRespondModel allChatRespondModel;

    @Autowired
    public AllChatController(AllChatServiceImpl allChatService, ModelMapper modelMapper) {
        this.allChatService = allChatService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/global/all")
    @ResponseBody
    ResponseEntity<List<AllChatBindingModel>> all() {
        try {
            return new ResponseEntity<>(allChatService.findAllMessages()
                    .stream()
                    .map(x -> modelMapper.map(x, AllChatBindingModel.class))
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/global/top/{n}/{from}/{to}")
    @ResponseBody
    ResponseEntity<List<AllChatBindingModel>> findTopNMessagesByTime(@PathVariable int n, @PathVariable String from,
                                                                     @PathVariable String to) {
        from = from.replace(" ", "T") + "Z";
        to = to.replace(" ", "T") + "Z";
        try {
            return new ResponseEntity<>(allChatService
                    .findTopNMessagesFromToDate(n, OffsetDateTime.parse(from), OffsetDateTime.parse(to))
                    .stream()
                    .map(x ->
                    modelMapper.map(x, AllChatBindingModel.class)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/global/top/{n}")
    @ResponseBody
    ResponseEntity<List<AllChatBindingModel>> findTopNMessages(@PathVariable int n) {
        try {
            return new ResponseEntity<>(allChatService.findTopNMessages(n).stream().map(x ->
                    modelMapper.map(x, AllChatBindingModel.class)).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/global/add")
    ResponseEntity<AllChatRespondModel> addMessage(@RequestBody AllChatBindingModel member) {

        AllChatServiceModel allChatServiceModel = modelMapper.map(member, AllChatServiceModel.class);
        allChatRespondModel = new AllChatRespondModel();
        allChatRespondModel.setFrom(member.getUsername());
        allChatRespondModel.setMessage(member.getMessage());
        allChatRespondModel.setOffsetDateTime(OffsetDateTime.now());

        try {

            allChatService.addMessageFromUser(allChatServiceModel);
            allChatRespondModel.setAdded(true);
            allChatRespondModel.setInformation("Message successfully added.");
            return new ResponseEntity<>(allChatRespondModel, HttpStatus.OK);
        } catch (Exception e) {
            allChatRespondModel.setAdded(false);
            allChatRespondModel.setInformation("Message was not added. Check message error.");
            e.printStackTrace();
            return new ResponseEntity<>(allChatRespondModel, HttpStatus.BAD_REQUEST);
        }
    }
}
