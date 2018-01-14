package com.jorged.hooversim.dao;

import com.jorged.hooversim.model.Coordinates;

public interface MoveDAO {

    void addMove(Coordinates origin, Coordinates destination);

}
