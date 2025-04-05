package com.hotels.hotels.dto;

import jakarta.validation.constraints.*;

public class HotelCreateDTO {

    @NotBlank(message = "A hotel must have name")
    private String name;

    @NotBlank(message = "Must have address")
    private String address;

    @DecimalMin(value = "1.0", message = "Ranking must be above 1")
    @DecimalMax(value = "5.0", message = "Ranking must be below 5")
    private Double rankingAverage = 4.5; // default

    @NotNull(message = "Must have room price")
    @Positive(message = "Room price must be positive")
    private Double roomPrice;

    private Double priceDiscount;

    @NotNull(message = "A hotel must have stars level")
    @Min(value = 1, message = "Comfort must be at least 1")
    @Max(value = 7, message = "Comfort must be at most 7")
    private Integer comfort;

    @NotBlank(message = "A hotel must have summary")
    @Size(max = 255)
    private String summary;

    private String description;

    @NotBlank(message = "A hotel must have an image")
    private String imageCover;

    // Getters & Setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Double getRankingAverage() { return rankingAverage; }
    public void setRankingAverage(Double rankingAverage) { this.rankingAverage = rankingAverage; }

    public Double getRoomPrice() { return roomPrice; }
    public void setRoomPrice(Double roomPrice) { this.roomPrice = roomPrice; }

    public Double getPriceDiscount() { return priceDiscount; }
    public void setPriceDiscount(Double priceDiscount) { this.priceDiscount = priceDiscount; }

    public Integer getComfort() { return comfort; }
    public void setComfort(Integer comfort) { this.comfort = comfort; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageCover() { return imageCover; }
    public void setImageCover(String imageCover) { this.imageCover = imageCover; }
}
