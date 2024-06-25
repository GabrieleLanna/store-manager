package com.gabrielelanna.store_manager.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SoldDTO {

    private Long id;
    private ItemDTO item;
    private LocalDateTime saleDate;

}
