package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> thursdayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(2, thursdayTrainings.size());
        Assertions.assertEquals(thursdayChildTrainingSession, thursdayTrainings.get(0));
        Assertions.assertEquals(thursdayAdultTrainingSession, thursdayTrainings.get(1));
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondayAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        List<TrainingSession> mondayAt14 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        Assertions.assertEquals(1, mondayAt13.size());
        Assertions.assertEquals(singleTrainingSession, mondayAt13.get(0));
        Assertions.assertEquals(0, mondayAt14.size());
    }

    @Test
    void testGetTrainingSessionsAtTheSameDayAndTime() {
        Timetable timetable = new Timetable();

        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Group adultGroup = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach childCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach adultCoach = new Coach("Васильев", "Иван", "Сергеевич");
        TrainingSession childTrainingSession = new TrainingSession(childGroup, childCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession adultTrainingSession = new TrainingSession(adultGroup, adultCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(childTrainingSession);
        timetable.addNewTrainingSession(adultTrainingSession);

        List<TrainingSession> mondayAt13 = timetable.getTrainingSessionsForDayAndTime(
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(2, mondayAt13.size());
        Assertions.assertEquals(childTrainingSession, mondayAt13.get(0));
        Assertions.assertEquals(adultTrainingSession, mondayAt13.get(1));
    }

    @Test
    void testEmptyTimetableReturnsNoTrainingSessionsForAnyDay() {
        Timetable timetable = new Timetable();

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.SATURDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.SUNDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDaySortedByTime() {
        Timetable timetable = new Timetable();

        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Group adultGroup = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Group secondAdultGroup = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach childCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach adultCoach = new Coach("Васильев", "Иван", "Сергеевич");
        TrainingSession childTrainingSession = new TrainingSession(childGroup, childCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession adultTrainingSession = new TrainingSession(adultGroup, adultCoach,
                DayOfWeek.MONDAY, new TimeOfDay(15, 0));
        TrainingSession secondAdultTrainingSession = new TrainingSession(secondAdultGroup, adultCoach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(childTrainingSession);
        timetable.addNewTrainingSession(adultTrainingSession);
        timetable.addNewTrainingSession(secondAdultTrainingSession);

        List<TrainingSession> mondayTrainings = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        Assertions.assertEquals(childTrainingSession, mondayTrainings.get(0));
        Assertions.assertEquals(secondAdultTrainingSession, mondayTrainings.get(1));
        Assertions.assertEquals(adultTrainingSession, mondayTrainings.get(2));
    }

    @Test
    void testGetCountByCoachesForEmptyTimetable() {
        Timetable timetable = new Timetable();

        Assertions.assertEquals(0, timetable.getCountByCoaches().size());
    }

    @Test
    void testGetCountByCoachesForSingleCoach() {
        Timetable timetable = new Timetable();

        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession mondayTrainingSession = new TrainingSession(childGroup, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession wednesdayTrainingSession = new TrainingSession(childGroup, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(mondayTrainingSession);
        timetable.addNewTrainingSession(wednesdayTrainingSession);

        List<CoachTrainingCount> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(1, countByCoaches.size());
        Assertions.assertEquals(coach, countByCoaches.get(0).getCoach());
        Assertions.assertEquals(2, countByCoaches.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesSortedByTrainingCount() {
        Timetable timetable = new Timetable();

        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach firstCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach secondCoach = new Coach("Семёнов", "Владимир", "Константинович");
        TrainingSession mondayTrainingSession = new TrainingSession(childGroup, firstCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession wednesdayTrainingSession = new TrainingSession(childGroup, firstCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));
        TrainingSession saturdayTrainingSession = new TrainingSession(childGroup, secondCoach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(saturdayTrainingSession);
        timetable.addNewTrainingSession(mondayTrainingSession);
        timetable.addNewTrainingSession(wednesdayTrainingSession);

        List<CoachTrainingCount> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(2, countByCoaches.size());
        Assertions.assertEquals(firstCoach, countByCoaches.get(0).getCoach());
        Assertions.assertEquals(2, countByCoaches.get(0).getCount());
        Assertions.assertEquals(secondCoach, countByCoaches.get(1).getCoach());
        Assertions.assertEquals(1, countByCoaches.get(1).getCount());
    }

    @Test
    void testGetCountByCoachesCountsEqualCoachObjectsAsOneCoach() {
        Timetable timetable = new Timetable();

        Group childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach firstCoach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach sameCoach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession mondayTrainingSession = new TrainingSession(childGroup, firstCoach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession wednesdayTrainingSession = new TrainingSession(childGroup, sameCoach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(15, 0));

        timetable.addNewTrainingSession(mondayTrainingSession);
        timetable.addNewTrainingSession(wednesdayTrainingSession);

        List<CoachTrainingCount> countByCoaches = timetable.getCountByCoaches();

        Assertions.assertEquals(1, countByCoaches.size());
        Assertions.assertEquals(firstCoach, countByCoaches.get(0).getCoach());
        Assertions.assertEquals(2, countByCoaches.get(0).getCount());
    }

}
