package com.cg.model;

import com.cg.model.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(precision = 9, scale = 0, nullable = false)
    private BigDecimal price;

    private Long quantity;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_color", nullable = false)
    private Color color;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_size", nullable = false)
    private Size size;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_category", nullable = false)
    private Category category;

    public  ProductResponseDto toDTO() {
        return new ProductResponseDto()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setColor(color.getNameColor())
                .setSize(size.getNumberSize())
                .setCategory(category.getTypeProduct());
    }
    public ProductResponseDto toProductResponseDto(ProductMedia productMedia) {
        return new ProductResponseDto()
                .setId(id)
                .setTitle(title)
                .setPrice(price)
                .setQuantity(quantity)
                .setDescription(description)
                .setColor(color.getNameColor())
                .setSize(size.getNumberSize())
                .setCategory(category.getTypeProduct())
                .setMediaId(productMedia.getId())
                .setFileName(productMedia.getFileName())
                .setFileFolder(productMedia.getFileFolder())
                .setFileUrl(productMedia.getFileUrl())
                ;
    }

}
