package com.mycoolestapp;

public class GolfCoach implements Coach {
    @Override
    public String getDailyWorkout() {
        return "Tiger woods";
    }

    @Override
    public Integer getTimeWorkout() {
        return 3;
    }
}
