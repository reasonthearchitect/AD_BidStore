package com.rta.watchstore.repo.impl;

import com.rta.watchstore.dto.Bid;
import com.rta.watchstore.repo.IBidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BidRepo implements IBidRepo {

    @Autowired RedisTemplate<String, Object> redisTemplate;


    @Override public void save(Bid bid){
        final Map< String, Object > properties = new HashMap<>();
        properties.put( "id", bid.getId() );
        properties.put( "amount", bid.getAmount() );
        this.redisTemplate.opsForHash().putAll( bid.getVin(), properties);
    }

    @Override public Bid getBid(String vin) {
        Bid rbid = new Bid();
        rbid.setVin(vin);
        rbid.setId((String)this.redisTemplate.opsForHash().get(vin, "id"));
        rbid.setAmount(new BigDecimal( (String)this.redisTemplate.opsForHash().get(vin, "amount")));

        //Map<Object, Object> rmap = this.redisTemplate.opsForHash().entries(vin);
        return rbid;
    }

    @Override public boolean exists(String vin) {
        return !this.redisTemplate.opsForHash().entries(vin).isEmpty();
    }
}


