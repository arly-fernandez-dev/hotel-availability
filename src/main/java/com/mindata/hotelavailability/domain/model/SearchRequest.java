package com.mindata.hotelavailability.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

public final class SearchRequest {

    @NotBlank(message = "hotelId is required")
    private final String hotelId;

    @NotBlank(message = "checkIn is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "checkIn must be in format dd/MM/yyyy")
    private final String checkIn;

    @NotBlank(message = "checkOut is required")
    @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "checkOut must be in format dd/MM/yyyy")
    private final String checkOut;

    @NotNull(message = "ages is required")
    @Size(min = 1, message = "ages must contain at least one element")
    private final List<Integer> ages;

    public SearchRequest() {
        this.hotelId = null;
        this.checkIn = null;
        this.checkOut = null;
        this.ages = null;
    }

    public SearchRequest(String hotelId, String checkIn, String checkOut, List<Integer> ages) {
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages != null ? Collections.unmodifiableList(ages) : null;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }
}
