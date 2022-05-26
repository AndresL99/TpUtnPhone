package com.utnphones.tputnphones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CallDto {

    String origin;
    String destination;
    String datetime;
    Long duration;
}
