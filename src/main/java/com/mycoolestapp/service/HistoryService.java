package com.mycoolestapp.service;

import com.mycoolestapp.entity.User;
import com.mycoolestapp.entity.history;
import com.mycoolestapp.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    public List<history> getLast10HistoryByUser(User user) {
        return historyRepository.findTop10ByUserOrderByGeneratedAtDesc(user);
    }
    public List<history> getHistoryByUser(User user) {
        return historyRepository.findByUser(user);
    }

    public history saveHistory(history historyRecord) {
        return historyRepository.save(historyRecord);
    }
}
