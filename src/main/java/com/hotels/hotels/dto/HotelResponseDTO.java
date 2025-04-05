package com.hotels.hotels.dto;



import java.time.LocalDateTime;

public class HotelResponseDTO {

    private Long id;
    private String name;
    private String address;
    private Double rankingAverage;
    private Double roomPrice;
    private Double priceDiscount;
    private Integer comfort;
    private String summary;
    private String description;
    private String imageCover;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getteriai ir setteriai

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRankingAverage() {
        return rankingAverage;
    }

    public void setRankingAverage(Double rankingAverage) {
        this.rankingAverage = rankingAverage;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Double getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(Double priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public Integer getComfort() {
        return comfort;
    }

    public void setComfort(Integer comfort) {
        this.comfort = comfort;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

