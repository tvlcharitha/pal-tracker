package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TimeEntryController {



    private final CounterService counter;
    private final GaugeService gauge;

    @Autowired
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(CounterService counter, GaugeService gauge, TimeEntryRepository timeEntryRepository) {
        this.counter = counter;
        this.gauge = gauge;
        this.timeEntryRepository=timeEntryRepository;
    }

    @RequestMapping(value="/time-entries",method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {

        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        ResponseEntity responseEntity = new ResponseEntity<>(createdTimeEntry,HttpStatus.CREATED);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return responseEntity;
    }

    @RequestMapping(value="/time-entries/{id}",method = RequestMethod.GET)
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long l) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.find(l)==null)
            responseEntity = new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        else {
            counter.increment("TimeEntry.read");
            responseEntity = new ResponseEntity<>(timeEntryRepository.find(l), HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value="/time-entries",method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity <List<TimeEntry>> (timeEntryRepository.list(),HttpStatus.OK);
    }

    @RequestMapping(value="/time-entries/{id}",method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") long l,@RequestBody TimeEntry expected) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.update(l,expected)==null )
            responseEntity = new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        else {
            counter.increment("TimeEntry.updated");
            responseEntity = new ResponseEntity<>(timeEntryRepository.update(l, expected), HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value="/time-entries/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long l) {
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(timeEntryRepository.delete(l),HttpStatus.NO_CONTENT);
        return responseEntity;

    }
}
