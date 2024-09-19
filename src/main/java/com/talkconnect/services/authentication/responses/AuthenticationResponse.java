package com.talkconnect.services.authentication.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.talkconnect.models.entities.roles.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    
  @JsonProperty("id") // Add annotation for the ID field
  private Long id;
  @JsonProperty("role")
  private Role role;
  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;
}
