package br.com.lima.controllers;


import br.com.lima.model.Greeting;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Ola mundo, %s!";
    private final AtomicLong counter = new AtomicLong();

    // http://localhost:greeting?name=fulano
    @RequestMapping("/greeting")
    public Greeting greet(@RequestParam(value = "name", defaultValue = "Ola mundo") String name) {

        return new Greeting(counter.incrementAndGet(), String.format(template, name));

    }


}
