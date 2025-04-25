package com.hotels.hotels.service;


import com.hotels.hotels.dao.HotelsRepository;
import com.hotels.hotels.entity.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelsServiceImpl implements HotelsService {


    private HotelsRepository hotelsRepository;

    @Autowired
    public HotelsServiceImpl(HotelsRepository hotelsRepository) {
        this.hotelsRepository = hotelsRepository;
    }

    @Override
    public Page<Hotel> findAll(Pageable pageable) {
        return hotelsRepository.findAll(pageable);
    }

    @Override
    public Hotel findById(int theId) {
        Optional<Hotel> result = hotelsRepository.findById(theId);

        Hotel hotel = null;

        if (result.isPresent()) {
            hotel = result.get();
        }
        else {
            throw new RuntimeException("Did not find hotel id - " + theId);
        }

        return hotel;
    }

    @Override
    public Hotel save(Hotel hotel) {
        return hotelsRepository.save(hotel);
    }

    @Override
    public void deleteById(int id) {
        hotelsRepository.deleteById(id);
    }

    public Page<Hotel> findByNameContaining(String name, Pageable pageable) {
        return hotelsRepository.findByNameContainingIgnoreCase(name, pageable);
    }

}