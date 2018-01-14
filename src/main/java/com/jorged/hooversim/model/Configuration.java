package com.jorged.hooversim.model;

import com.jorged.hooversim.exception.WrongConfigurationException;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jorged.hooversim.exception.WrongConfigurationException.MSG_INSTRUCTIONS;
import static com.jorged.hooversim.exception.WrongConfigurationException.MSG_PATCHES_COORDS;
import static com.jorged.hooversim.exception.WrongConfigurationException.MSG_STARTING_COORDS;

@Data
@AllArgsConstructor
public class Configuration {

    private List<Integer> roomSize;
    private List<Integer> coords;
    private List<List<Integer>> patches;
    private String instructions;

    public Boolean validate() throws WrongConfigurationException {

        return isEverythingInsideRoom() && isValidSetInstructions();

    }

    private Boolean isEverythingInsideRoom() throws WrongConfigurationException {

        if (coords.get(0) < 0 || coords.get(0) > roomSize.get(0)
                || coords.get(1) < 0 || coords.get(1) > roomSize.get(1)) {

            throw new WrongConfigurationException(MSG_STARTING_COORDS);

        }

        for (List<Integer> patch : patches) {
            if (patch.get(0) < 0 || patch.get(0) > roomSize.get(0)
                    || patch.get(1) < 0 || patch.get(1) > roomSize.get(1)) {

                throw new WrongConfigurationException(MSG_PATCHES_COORDS);

            }
        }

        return true;

    }

    private Boolean isValidSetInstructions() throws WrongConfigurationException {

        Pattern pattern = Pattern.compile("[NSEW]+");
        Matcher matcher = pattern.matcher(instructions);

        Boolean matches = matcher.matches();

        if (!matches) {
            throw new WrongConfigurationException(MSG_INSTRUCTIONS);
        }

        return true;

    }

}
