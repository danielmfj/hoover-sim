package com.jorged.hooversim.dao;

import com.jorged.hooversim.model.Coordinates;
import com.jorged.hooversim.model.Move;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class MoveDAOImpl implements MoveDAO {

    @Getter
    @Setter
    private SessionFactory sessionFactory;

    @Override
    public void addMove(Coordinates origin, Coordinates destination) {

        Move move = new Move(origin, destination);

        Session session = this.sessionFactory.getCurrentSession();
        session.persist(move);
    }

}
