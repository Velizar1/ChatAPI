package com.example.database.Services;

import com.example.database.Entities.AllChat;
import com.example.database.Models.Service.AllChatServiceModel;
import com.example.database.Repositories.AllChatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AllChatServiceImpl implements AllChatService {

    private AllChatRepository allChatRepository;
    private ModelMapper modelMapper;

    @Autowired
    public AllChatServiceImpl(AllChatRepository allChatRepository, ModelMapper modelMapper) {
        this.allChatRepository = allChatRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<AllChatServiceModel> findAllMessages() {
        try {
            return allChatRepository.findAllBy().stream()
                    .map(x -> modelMapper.map(x, AllChatServiceModel.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AllChatServiceModel> findTopNMessagesFromToDate(int n, OffsetDateTime from, OffsetDateTime to) {
        try {
            List<AllChatServiceModel> allChatServiceModels = allChatRepository.findAllByAddedAtBetween(from, to)
                    .stream()
                    .map(x -> modelMapper.map(x, AllChatServiceModel.class))
                    .collect(Collectors.toList());
            return n > allChatServiceModels.size() ? allChatServiceModels : allChatServiceModels.subList(0, n);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AllChatServiceModel> findTopNMessages(int n) {
        try {
            List<AllChatServiceModel> allChatServiceModels = allChatRepository.findAllBy()
                    .stream()
                    .map(x -> modelMapper.map(x, AllChatServiceModel.class))
                    .collect(Collectors.toList());
            return n > allChatServiceModels.size() ? allChatServiceModels : allChatServiceModels.subList(0, n);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addMessageFromUser(AllChatServiceModel allChatServiceModel) {
        AllChat allChat = modelMapper.map(allChatServiceModel, AllChat.class);
        try {
            allChatRepository.saveAndFlush(allChat);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
