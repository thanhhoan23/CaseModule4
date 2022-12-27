package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Color;
import com.cg.model.Size;
import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponseDto {
    private Long id;
    private String title;
    private BigDecimal price;
    private Long quantity;
    private String description;
    private String color;
    private int size;
    private String category;

    private String mediaId;
    private String fileName;
    private String fileFolder;
    private String fileUrl;

    public ProductResponseDto(Long id, String title, BigDecimal price, Long quantity, String description, String color, int size, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.color = color;
        this.size = size;
        this.category = category;
    }

}
