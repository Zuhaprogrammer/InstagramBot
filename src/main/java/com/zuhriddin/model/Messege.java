package com.zuhriddin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Messege {
    private UUID id;
    private String test;
    private UUID fromId;
    private UUID toId;
    private String localDateTimeCRDT;
}
