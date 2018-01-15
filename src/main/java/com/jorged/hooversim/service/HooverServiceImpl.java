package com.jorged.hooversim.service;

import com.jorged.hooversim.dao.MoveDAO;
import com.jorged.hooversim.exception.WrongConfigurationException;
import com.jorged.hooversim.model.Configuration;
import com.jorged.hooversim.model.Coordinates;
import com.jorged.hooversim.model.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class HooverServiceImpl implements HooverService {

    private static final Logger logger = LoggerFactory.getLogger(HooverServiceImpl.class);

    @Autowired
    private MoveDAO moveDAO;

    private Coordinates roomSize;
    private Coordinates actualPosition;
    private List<List<Integer>> patches;
    private char[] instructions;

    @Override
    public Coordinates goNorth(Coordinates origin, Integer wallY) {
        if (origin.getY() + 1 < wallY) {
            return new Coordinates(origin.getX(), origin.getY() + 1);
        }

        return origin;
    }

    @Override
    public Coordinates goSouth(Coordinates origin) {
        if (origin.getY() - 1 >= 0) {
            return new Coordinates(origin.getX(), origin.getY() - 1);
        }

        return origin;
    }

    @Override
    public Coordinates goEast(Coordinates origin, Integer wallX) {
        if (origin.getX() + 1 < wallX) {
            return new Coordinates(origin.getX() + 1, origin.getY());
        }

        return origin;
    }

    @Override
    public Coordinates goWest(Coordinates origin) {
        if (origin.getX() - 1 >= 0) {
            return new Coordinates(origin.getX() - 1, origin.getY());
        }

        return origin;
    }

    @Override
    public Boolean setupRoom(Configuration configuration) throws WrongConfigurationException {

        configuration.validate();

        roomSize = new Coordinates(configuration.getRoomSize().get(0), configuration.getRoomSize().get(1));
        actualPosition = new Coordinates(configuration.getCoords().get(0), configuration.getCoords().get(1));
        patches = configuration.getPatches();
        instructions = configuration.getInstructions().toCharArray();

        return true;
    }

    @Override
    public Result runSimulation() {

        Integer initialPatches = patches.size();

        patches.removeIf(patch -> Objects.equals(patch.get(0), actualPosition.getX())
                && Objects.equals(patch.get(1), actualPosition.getY()));

        for (char instruction : instructions) {

            Coordinates originalPosition = actualPosition;

            switch (instruction) {
                case 'N':
                    actualPosition = goNorth(actualPosition, roomSize.getY());
                    break;
                case 'S':
                    actualPosition = goSouth(actualPosition);
                    break;
                case 'E':
                    actualPosition = goEast(actualPosition, roomSize.getX());
                    break;
                case 'W':
                    actualPosition = goWest(actualPosition);
                    break;
                default:
                    break;
            }

            patches.removeIf(patch -> Objects.equals(patch.get(0), actualPosition.getX())
                    && Objects.equals(patch.get(1), actualPosition.getY()));

            logger.debug(" --- Move ----------");
            logger.debug("Patches:" + patches);
            logger.debug("Origin: " + originalPosition + " -> Destination: " + actualPosition);

//            moveDAO.addMove(originalPosition, actualPosition);
        }

        Result result = new Result(actualPosition, initialPatches - patches.size());

        return result;

    }

}
