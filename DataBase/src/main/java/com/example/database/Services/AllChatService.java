package com.example.database.Services;

import com.example.database.Models.Service.AllChatServiceModel;

import java.time.OffsetDateTime;
import java.util.List;

public interface AllChatService {
    List<AllChatServiceModel> findAllMessages();

    List<AllChatServiceModel> findTopNMessagesFromToDate(int n, OffsetDateTime from, OffsetDateTime to);

    List<AllChatServiceModel> findTopNMessages(int n);

    boolean addMessageFromUser(AllChatServiceModel allChatServiceModel);
}
