package br.com.lima.controllers;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TesteLogController {

    private Logger logger = LoggerFactory.getLogger(TesteLogController.class.getName());

    @GetMapping("/test")
    public String testeLog(){

        logger.trace("Modo TRACE log");
        logger.debug("Modo DEBUG log");
        logger.info("Modo INFO log");
        logger.warn("Modo WARN log");
        logger.error("Modo ERROR log");
        return "Logs gerados com sucesso";
    }



}
