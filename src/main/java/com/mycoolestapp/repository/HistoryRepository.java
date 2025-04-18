package com.mycoolestapp.repository;

import com.mycoolestapp.entity.User;
import com.mycoolestapp.entity.history;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<history, Long> {
    List<history> findByUser(User user);

    List<history> id(long id);

    List<history> findTop10ByUserOrderByGeneratedAtDesc(User user);

    List<history> findTopByUserIdOrderByGeneratedAtDesc(User user);
}
