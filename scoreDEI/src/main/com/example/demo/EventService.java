package com.example.demo;

import com.example.data.Event;
import com.example.data.Game;
import com.example.repository.EventRepository;
import com.example.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEvents()
    {
        List<Event>eventRecords = new ArrayList<>();
        eventRepository.findAll().forEach(eventRecords::add);
        return eventRecords;
    }

    public void addEvent(Event event)
    {
        System.out.println(event);
        eventRepository.save(event);
    }

    public Optional<Event> getEvent(int id) {
        return eventRepository.findById(id);
    }

    public List<Event> eventsInGame(int id){
        return eventRepository.eventsInGame(id);
    }
}
