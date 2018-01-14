package com.jorged.hooversim.service;

import com.jorged.hooversim.exception.WrongConfigurationException;
import com.jorged.hooversim.model.Configuration;
import com.jorged.hooversim.model.Coordinates;
import com.jorged.hooversim.model.Result;

public interface HooverService {

    Coordinates goNorth(Coordinates origin, Integer wallY);
    Coordinates goSouth(Coordinates origin);
    Coordinates goEast(Coordinates origin, Integer wallX);
    Coordinates goWest(Coordinates origin);

    Boolean setupRoom(Configuration configuration) throws WrongConfigurationException;
    Result runSimulation();

}
