package com.mycoolestapp;

import org.springframework.stereotype.Component;

@Component
public class BasketballCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "Practice Dribbling for 15 minutes";
    }

    @Override
    public Integer getTimeWorkout() {
        return 15;
    }
}
