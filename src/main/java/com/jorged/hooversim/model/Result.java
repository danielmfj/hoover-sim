package com.jorged.hooversim.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    private Coordinates finalPosition;
    private Integer patches;

}
