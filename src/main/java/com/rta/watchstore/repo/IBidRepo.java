package com.rta.watchstore.repo;

import com.rta.watchstore.dto.Bid;

import java.util.List;

public interface IBidRepo {

    void save(Bid bid);

    Bid getBid(String vin);

    boolean exists(String vin);
}
