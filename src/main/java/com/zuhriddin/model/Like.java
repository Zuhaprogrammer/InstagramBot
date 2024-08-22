package com.zuhriddin.model;

import com.fasterxml.jackson.databind.util.PrimitiveArrayBuilder;
import lombok.*;

import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private UUID ID;
    private UUID userID;
    private UUID postID;
    private String localDateCRDT;

}
