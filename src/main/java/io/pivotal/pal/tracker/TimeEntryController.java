package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TimeEntryController {


    @Autowired
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
     this.timeEntryRepository=timeEntryRepository;
    }

    @RequestMapping(value="/time-entries",method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        ResponseEntity responseEntity = new ResponseEntity<>(timeEntryRepository.create(timeEntry),HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value="/time-entries/{id}",method = RequestMethod.GET)
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long l) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.find(l)==null)
            responseEntity = new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        else
            responseEntity = new ResponseEntity<>(timeEntryRepository.find(l),HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value="/time-entries",method = RequestMethod.GET)
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity <List<TimeEntry>> (timeEntryRepository.list(),HttpStatus.OK);
    }

    @RequestMapping(value="/time-entries/{id}",method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") long l,@RequestBody TimeEntry expected) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.update(l,expected)==null )
            responseEntity = new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        else
            responseEntity = new ResponseEntity<>(timeEntryRepository.update(l,expected),HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value="/time-entries/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long l) {
        ResponseEntity responseEntity;
        responseEntity = new ResponseEntity<>(timeEntryRepository.delete(l),HttpStatus.NO_CONTENT);
        return responseEntity;

    }
}
