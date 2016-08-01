package com.rta.watchstore.rest;

import com.rta.watchstore.dto.Bid;
import com.rta.watchstore.repo.IBidRepo;
import com.rta.watchstore.stream.BidSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bidstore")
public class BidRest {

    @Autowired
    BidSource bidSource;

    @Autowired private IBidRepo bidRepo;

    @RequestMapping(value = "/placebid",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> placebid(@RequestBody Bid bid) {
        if (!this.bidRepo.exists(bid.getVin())) {
            this.bidRepo.save(bid);
            this.bidSource.send(bid);
            return ResponseEntity.ok().build();
        }
        Bid currentBid = this.bidRepo.getBid(bid.getVin());

        // This is not a greater bid than the current bid so reject it with an error.
        if (currentBid.getAmount().doubleValue() >= bid.getAmount().doubleValue()) {
            return ResponseEntity.badRequest().build();
        }
        this.bidRepo.save(bid);
        this.bidSource.send(bid);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/getbidsforlist",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Bid>> getBids(@RequestBody List<String> vins) {
        return new ResponseEntity<>(this.bidRepo.getBids(vins), new HttpHeaders(), HttpStatus.OK);
    }
}
