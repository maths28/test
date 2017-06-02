package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mathieu on 01/06/17.
 */

@Component
public class ScheduledTask {

    private Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private int i;

    @Value("${root.url}")
    private String rootUrl;

    private RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 5000)
    public void task(){
        i++;
        log.info("Sch. Task : " + Integer.toString(i));
    }

    @Scheduled(fixedRate = 1800000)
    public void sendActive(){
        Boolean res = restTemplate.getForObject(rootUrl+"active", Boolean.class);
        log.info(res.toString());
    }

}
