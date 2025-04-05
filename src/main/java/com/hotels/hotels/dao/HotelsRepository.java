package com.hotels.hotels.dao;

import com.hotels.hotels.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelsRepository extends JpaRepository<Hotel,Integer> {
}
