package com.jorged.hooversim.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "MOVES")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Transient
    private Coordinates origin;

    @NonNull
    @Transient
    private Coordinates destination;

    @Column(name = "origin")
    private String getOriginString() {
        return origin.toString();
    }

    @Column(name = "destination")
    private String getDestinationString() {
        return origin.toString();
    }

}
