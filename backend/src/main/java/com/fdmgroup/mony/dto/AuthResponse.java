package com.fdmgroup.mony.dto;

/**
 * Immutable JWT response.
 * @param accessToken JWT
 */
public record AuthResponse(String accessToken) {
}
