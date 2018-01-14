package com.jorged.hooversim;

import com.jorged.hooversim.exception.WrongConfigurationException;
import com.jorged.hooversim.model.Configuration;
import com.jorged.hooversim.model.Coordinates;
import com.jorged.hooversim.model.Result;
import com.jorged.hooversim.service.HooverService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HooverController {

    @Autowired
    private HooverService hooverService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/simulate", method = RequestMethod.POST)
    public ResponseEntity<Result> runSimulation(@RequestBody Configuration configuration) throws WrongConfigurationException {

        hooverService.setupRoom(configuration);
        Result result = hooverService.runSimulation();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
