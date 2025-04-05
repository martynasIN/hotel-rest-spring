package com.hotels.hotels.service;

import com.hotels.hotels.entity.Hotel;

import java.util.List;

public interface HotelsService {
    List<Hotel> findAll();

    Hotel findById(int theId);

    Hotel save(Hotel theEmployee);

    void deleteById(int theId);
}
