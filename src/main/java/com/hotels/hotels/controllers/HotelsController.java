package com.hotels.hotels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hotels.hotels.config.PaginationProperties;
import com.hotels.hotels.dao.HotelsRepository;
import com.hotels.hotels.dto.HotelCreateDTO;
import com.hotels.hotels.dto.HotelResponseDTO;
import com.hotels.hotels.entity.Hotel;
import com.hotels.hotels.mapper.HotelMapper;
import com.hotels.hotels.service.HotelsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelsController {

    private final HotelsService hotelsService;
    private final HotelsRepository hotelsRepository;
    private final PaginationProperties paginationProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public HotelsController(
            HotelsService hotelsService,
            HotelsRepository hotelsRepository,
            PaginationProperties paginationProperties,
            ObjectMapper objectMapper
    ) {
        this.hotelsService = hotelsService;
        this.hotelsRepository = hotelsRepository;
        this.paginationProperties = paginationProperties;
        this.objectMapper = objectMapper;
    }

    // GET /api/hotels?page=0&size=10&sort=roomPrice,desc&name=vilnius
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String search
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Hotel> hotelPage;

        if (search != null && !search.isEmpty()) {
            hotelPage = hotelsService.findByNameContaining(search, pageable);
        } else {
            hotelPage = hotelsService.findAll(pageable);
        }

        List<HotelResponseDTO> hotels = hotelPage.getContent().stream()
                .map(HotelMapper::toResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "status", "success",
                "results", hotelPage.getTotalElements(),
                "data", hotels
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> getHotel(@PathVariable Long hotelId) {
        Hotel hotel = hotelsService.findById(hotelId.intValue());
        if (hotel == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(HotelMapper.toResponse(hotel));
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> addHotel(@Valid @RequestBody HotelCreateDTO hotelDTO) {
        Hotel saved = hotelsService.save(HotelMapper.toEntity(hotelDTO));
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> updateHotel(
            @PathVariable Long hotelId,
            @Valid @RequestBody HotelCreateDTO hotelDTO) {

        Hotel existing = hotelsService.findById(hotelId.intValue());
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Hotel updatedHotel = HotelMapper.toEntity(hotelDTO);
        updatedHotel.setId(hotelId);
        updatedHotel.setCreatedAt(existing.getCreatedAt());
        updatedHotel.setUpdatedAt(java.time.LocalDateTime.now());

        Hotel saved = hotelsService.save(updatedHotel);
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> patchHotel(@PathVariable Long hotelId,
                                                       @RequestBody Map<String, Object> patchPayload) {
        Hotel tempHotel = hotelsService.findById(hotelId.intValue());

        if (tempHotel == null) {
            return ResponseEntity.notFound().build();
        }

        if (patchPayload.containsKey("id")) {
            return ResponseEntity.badRequest().body(null);
        }

        ObjectNode hotelNode = objectMapper.convertValue(tempHotel, ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(patchPayload, ObjectNode.class);
        hotelNode.setAll(patchNode);
        Hotel patchedHotel = objectMapper.convertValue(hotelNode, Hotel.class);

        Hotel saved = hotelsService.save(patchedHotel);
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long hotelId) {
        Hotel tempHotel = hotelsService.findById(hotelId.intValue());

        if (tempHotel == null) {
            return ResponseEntity.notFound().build();
        }

        hotelsService.deleteById(hotelId.intValue());
        return ResponseEntity.ok("Deleted hotel id - " + hotelId);
    }
}
