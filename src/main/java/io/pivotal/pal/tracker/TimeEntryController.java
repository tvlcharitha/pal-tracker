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

    @RequestMapping(value="/time-entries",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity create(@RequestParam TimeEntry timeEntry) {
        ResponseEntity responseEntity = new ResponseEntity<>(timeEntryRepository.create(timeEntry),HttpStatus.CREATED);
        return responseEntity;
    }

    @ResponseBody
    public ResponseEntity<TimeEntry> read(long l) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.find(l)==null)
            responseEntity = new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        else
            responseEntity = new ResponseEntity<>(timeEntryRepository.find(l),HttpStatus.OK);
        return responseEntity;
    }

    @ResponseBody
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity <List<TimeEntry>> (timeEntryRepository.list(),HttpStatus.OK);
    }

    @ResponseBody
    public ResponseEntity update(long l, TimeEntry expected) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.update(l,expected)==null )
            responseEntity = new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        else
            responseEntity = new ResponseEntity<>(timeEntryRepository.update(l,expected),HttpStatus.OK);
        return responseEntity;
    }

    @ResponseBody
    public ResponseEntity<TimeEntry> delete(long l) {
        ResponseEntity responseEntity;
        if(timeEntryRepository.delete(l)==null)
            responseEntity = new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        else
            responseEntity = new ResponseEntity<>(timeEntryRepository.delete(l),HttpStatus.OK);
        return responseEntity;

    }
}
