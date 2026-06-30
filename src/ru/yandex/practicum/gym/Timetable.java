package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        TimeOfDay time = trainingSession.getTimeOfDay();
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();

        timetable.computeIfAbsent(dayOfWeek, d -> new TreeMap<>())
                .computeIfAbsent(time, t -> new ArrayList<>()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsByTime = timetable.get(dayOfWeek);

        if (sessionsByTime == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> result = new ArrayList<>();

        for (List<TrainingSession> sessions : sessionsByTime.values()) {
            result.addAll(sessions);
        }

        return result;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsByTime = timetable.get(dayOfWeek);

        if (sessionsByTime == null) {
            return new ArrayList<>();
        }

        return sessionsByTime.getOrDefault(timeOfDay, new ArrayList<>());
    }
}
