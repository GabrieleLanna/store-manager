package com.gabrielelanna.store_manager.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long id;
    private String description;
    private String category;
    private boolean available;
    private double price;

}
