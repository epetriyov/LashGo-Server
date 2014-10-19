package com.lashgo.repository;

/**
 * Created by Eugene on 19.10.2014.
 */
public interface CheckWinnersDao {

    void addCheckWinner(int check);

    int getCheckWinner(int check);
}
