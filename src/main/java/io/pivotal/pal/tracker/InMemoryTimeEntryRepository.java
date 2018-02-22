package io.pivotal.pal.tracker;


import java.util.*;


public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    HashMap<Long,TimeEntry> timeEntryData = new HashMap<>();


    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(timeEntryData.size()+1);
        timeEntryData.put(timeEntry.getId(),timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        TimeEntry timeEntry =null;
        timeEntry=timeEntryData.get(id);
        return timeEntry;
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntryData.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        timeEntryData.put(id,timeEntry);
        return timeEntryData.get(id);
    }

    @Override
    public TimeEntry delete(long id) {
        TimeEntry timeEntry=timeEntryData.get(id);
        timeEntryData.remove(id);
        return timeEntry;
    }
}
