package com.hotels.hotels.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hotels.hotels.dto.HotelCreateDTO;
import com.hotels.hotels.dto.HotelResponseDTO;
import com.hotels.hotels.entity.Hotel;
import com.hotels.hotels.mapper.HotelMapper;
import com.hotels.hotels.service.HotelsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hotels")
public class HotelsController {

    private final HotelsService hotelsService;
    private final ObjectMapper objectMapper;

    @Autowired
    public HotelsController(HotelsService hotelsService, ObjectMapper objectMapper) {
        this.hotelsService = hotelsService;
        this.objectMapper = objectMapper;
    }

    // GET /api/hotels
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<HotelResponseDTO> hotels = hotelsService.findAll()
                .stream()
                .map(HotelMapper::toResponse)
                .collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "status", "success",
                "results", hotels.size(),
                "data", hotels
        );

        return ResponseEntity.ok(response);
    }

    // GET /api/hotels/{hotelId}
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> getHotel(@PathVariable Long hotelId) {
        Optional<Hotel> result = Optional.ofNullable(hotelsService.findById(hotelId.intValue()));

        return result.map(hotel -> ResponseEntity.ok(HotelMapper.toResponse(hotel)))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/hotels
    @PostMapping
    public ResponseEntity<HotelResponseDTO> addHotel(@Valid @RequestBody HotelCreateDTO hotelDTO) {
        Hotel saved = hotelsService.save(HotelMapper.toEntity(hotelDTO));
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    // PUT /api/hotels/{hotelId}
    @PutMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> updateHotel(
            @PathVariable Long hotelId,
            @Valid @RequestBody HotelCreateDTO hotelDTO) {

        Hotel existing = hotelsService.findById(hotelId.intValue());
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Hotel updatedHotel = HotelMapper.toEntity(hotelDTO);
        updatedHotel.setId(hotelId); // išsaugom ID
        updatedHotel.setCreatedAt(existing.getCreatedAt()); // paliekam originalią datą
        updatedHotel.setUpdatedAt(java.time.LocalDateTime.now());

        Hotel saved = hotelsService.save(updatedHotel);
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    // PATCH /api/hotels/{hotelId}
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

    // DELETE /api/hotels/{hotelId}
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
