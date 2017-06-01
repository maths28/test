package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by mathieu on 01/06/17.
 */

@Component
public class ScheduledTask {

    private Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private int i;


    @Scheduled(fixedRate = 5000)
    public void task(){
        i++;
        log.info("Sch. Task : " + Integer.toString(i));
    }

}
