package com.hotels.hotels.service;

import com.hotels.hotels.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelsService {
    //List<Hotel> findAll();
    Page<Hotel> findAll(Pageable pageable);

    Page<Hotel> findByNameContaining(String name, Pageable pageable);

    Hotel findById(int theId);

    Hotel save(Hotel theEmployee);

    void deleteById(int theId);
}
