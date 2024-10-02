package org.nam.urlshortener.service;

import org.nam.urlshortener.entity.Counter;
import org.nam.urlshortener.repository.CounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {

    private final CounterRepository counterRepository;

    @Autowired
    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public long getNextSequence(String className) {
        Counter counter = counterRepository.findCounterByClassName(className);
        if (counter == null) {
            counter = new Counter();
            counter.setClassName(className);
        }
        long sequence = counter.getSequence();

        if (sequence == 2020598544545L) {
            throw new IllegalArgumentException("Sequence limit reached");
        }

        counter.setSequence(counter.getSequence() + 1);
        counterRepository.save(counter);
        return counter.getSequence();
    }
}
