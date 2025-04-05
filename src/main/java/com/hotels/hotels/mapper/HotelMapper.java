package com.hotels.hotels.mapper;

import com.hotels.hotels.dto.HotelCreateDTO;
import com.hotels.hotels.dto.HotelResponseDTO;
import com.hotels.hotels.entity.Hotel;

import java.time.LocalDateTime;

public class HotelMapper {

     public static Hotel toEntity(HotelCreateDTO dto) {
            Hotel hotel = new Hotel();
            hotel.setName(dto.getName());
            hotel.setAddress(dto.getAddress());
            hotel.setRankingAverage(dto.getRankingAverage());
            hotel.setRoomPrice(dto.getRoomPrice());
            hotel.setPriceDiscount(dto.getPriceDiscount());
            hotel.setComfort(dto.getComfort());
            hotel.setSummary(dto.getSummary());
            hotel.setDescription(dto.getDescription());
            hotel.setImageCover(dto.getImageCover());
            hotel.setCreatedAt(LocalDateTime.now());
            hotel.setUpdatedAt(LocalDateTime.now());
            return hotel;
        }

        public static HotelResponseDTO toResponse(Hotel entity) {
            HotelResponseDTO dto = new HotelResponseDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setAddress(entity.getAddress());
            dto.setRankingAverage(entity.getRankingAverage());
            dto.setRoomPrice(entity.getRoomPrice());
            dto.setPriceDiscount(entity.getPriceDiscount());
            dto.setComfort(entity.getComfort());
            dto.setSummary(entity.getSummary());
            dto.setDescription(entity.getDescription());
            dto.setImageCover(entity.getImageCover());
            dto.setCreatedAt(entity.getCreatedAt());
            dto.setUpdatedAt(entity.getUpdatedAt());
            return dto;
        }
    }
