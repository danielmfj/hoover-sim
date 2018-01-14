package com.jorged.hooversim.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorged.hooversim.exception.WrongConfigurationException;
import com.jorged.hooversim.model.Configuration;
import com.jorged.hooversim.model.Coordinates;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static com.jorged.hooversim.exception.WrongConfigurationException.MSG_INSTRUCTIONS;
import static com.jorged.hooversim.exception.WrongConfigurationException.MSG_PATCHES_COORDS;
import static com.jorged.hooversim.exception.WrongConfigurationException.MSG_STARTING_COORDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HooverServiceTest {

    private static final Coordinates origin = new Coordinates(5, 5);
    private HooverService hooverService = new HooverServiceImpl();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void goNorth() {
        Coordinates originAfterMove = hooverService.goNorth(origin, 10);
        Coordinates destination = new Coordinates(5, 6);

        assertEquals(destination, originAfterMove);
    }

    @Test
    public void goSouth() {
        Coordinates originAfterMove = hooverService.goSouth(origin);
        Coordinates destination = new Coordinates(5, 4);

        assertEquals(destination, originAfterMove);
    }

    @Test
    public void goEast() {
        Coordinates originAfterMove = hooverService.goEast(origin, 10);
        Coordinates destination = new Coordinates(6, 5);

        assertEquals(destination, originAfterMove);
    }

    @Test
    public void goWest() {
        Coordinates originAfterMove = hooverService.goWest(origin);
        Coordinates destination = new Coordinates(4, 5);

        assertEquals(destination, originAfterMove);
    }

    @Test
    public void handleCorrectConfigurationObject() throws WrongConfigurationException {
        URL url = this.getClass().getClassLoader().getResource("configuration.json");
        File confFile = new File(url.getFile());

        assertTrue(testConfiguration(confFile));
    }

    @Test
    public void handleWrongOriginLocationConfiguration() throws WrongConfigurationException {
        expectedException.expect(WrongConfigurationException.class);
        expectedException.expectMessage(MSG_STARTING_COORDS);

        URL url = this.getClass().getClassLoader().getResource("wrongCoordsConfiguration.json");
        File confFile = new File(url.getFile());

        testConfiguration(confFile);
    }

    @Test
    public void handleWrongPatchesConfiguration() throws WrongConfigurationException {
        expectedException.expect(WrongConfigurationException.class);
        expectedException.expectMessage(MSG_PATCHES_COORDS);

        URL url = this.getClass().getClassLoader().getResource("wrongPatchesConfiguration.json");
        File confFile = new File(url.getFile());

        testConfiguration(confFile);
    }

    @Test
    public void handleWrongInstructionsConfiguration() throws WrongConfigurationException {
        expectedException.expect(WrongConfigurationException.class);
        expectedException.expectMessage(MSG_INSTRUCTIONS);

        URL url = this.getClass().getClassLoader().getResource("wrongInstructionsConfiguration.json");
        File confFile = new File(url.getFile());

        testConfiguration(confFile);
    }

    private Boolean testConfiguration(File confFile) throws WrongConfigurationException {
        Boolean valid = false;
        ObjectMapper mapper = new ObjectMapper();

        try {

            String jsonConf = FileUtils.readFileToString(confFile, "UTF-8");
            valid = hooverService.setupRoom(mapper.readValue(jsonConf, Configuration.class));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return valid;
    }
}