package ru.yandex.practicum.gym;

public class CoachTrainingCount implements Comparable<CoachTrainingCount> {
    private final Coach coach;
    private final int count;

    public CoachTrainingCount(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CoachTrainingCount o) {
        return o.count - this.count;
    }
}
