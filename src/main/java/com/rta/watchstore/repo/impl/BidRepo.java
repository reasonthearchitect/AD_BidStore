package com.rta.watchstore.repo.impl;

import com.rta.watchstore.dto.Bid;
import com.rta.watchstore.repo.IBidRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
        return this.redisTemplate.hasKey(vin);
        //return !this.redisTemplate.opsForHash().entries(vin).isEmpty();
    }

    @Override public Map<String, Bid> getBids(List<String> vins) {
        Map<String, Bid> rmap = new HashMap<>(vins.size());

        for (String vin : vins) {
            Bid abid = new Bid();
            abid.setVin(vin);
            if (this.exists(vin)) {
                Map<Object, Object> valueMap = this.redisTemplate.opsForHash().entries(vin);
                abid.setId((String)valueMap.get("id"));
                abid.setAmount(new BigDecimal((String)valueMap.get("amount")));
            } else {
                abid.setId("No Bid");
                abid.setAmount(new BigDecimal(0));
            }
            rmap.put(vin, abid);
        }
        return rmap;
    }
}


