package com.rta.watchstore.repo;

import com.rta.watchstore.dto.Bid;

import java.util.List;
import java.util.Map;

public interface IBidRepo {

    void save(Bid bid);

    Bid getBid(String vin);

    boolean exists(String vin);

    Map<String, Bid> getBids(List<String> vins);
}
