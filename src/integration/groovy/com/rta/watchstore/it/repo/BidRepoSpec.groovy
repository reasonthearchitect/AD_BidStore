package com.rta.watchstore.it.repo

import com.rta.watchstore.dto.Bid
import com.rta.watchstore.it.AbstractItTest
import com.rta.watchstore.repo.IBidRepo
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate


class BidRepoSpec extends AbstractItTest {

    @Autowired IBidRepo bidRepo;

    @Autowired RedisTemplate<String, Object> redisTemplate;

    @Test
    def "test if I can do duplicate keys"() {

        setup:
        this.bidRepo.save([id: "rhys", vin: "avin", amount: new BigDecimal(123)] as Bid);
        this.bidRepo.save([id: "dan", vin: "avin", amount: new BigDecimal(456)] as Bid);

        when:
        Bid rbid = this.bidRepo.getBid("avin");

        then:
        rbid.id == "dan"

        cleanup:
        this.redisTemplate.delete("avin");
    }

    @Test
    def "bid should exist"() {

        setup:
        this.bidRepo.save([id: "rhys", vin: "avin", amount: new BigDecimal(123)] as Bid);

        when:
        def exists = this.bidRepo.exists("avin");

        then:
        exists == true

        cleanup:
        this.redisTemplate.delete("avin");

    }

    @Test
    def "bid should not exist"() {

        when:
        def exists = this.bidRepo.exists("avin");

        then:
        exists == false
    }
}
