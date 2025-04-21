package com.wedevloop.orders.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDTO {
    private Long id;
    private String name;
    private double price;
    private String description;
}
