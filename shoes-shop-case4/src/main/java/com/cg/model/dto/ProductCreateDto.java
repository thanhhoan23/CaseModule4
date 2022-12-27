package com.cg.model.dto;

import com.cg.model.Category;
import com.cg.model.Color;
import com.cg.model.Product;
import com.cg.model.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductCreateDto implements Validator {
    private Long id;

    private String title;

    private String price;

    private Integer color;
    private Integer size;
    private Integer category;

    private String description;

    private MultipartFile file;

    public Product toProduct(){
        return new Product()
                .setId(id)
                .setTitle(title)
                .setQuantity(0l)
                .setPrice(BigDecimal.valueOf(Long.parseLong(price)))
                .setDescription(description);
//                .setColor(color)
//                .setSize(size)
//                .setCategory(category);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
    ProductCreateDto productCreateDto = (ProductCreateDto) target;
    String price = productCreateDto.getPrice();
        if (price.length() == 0) {
            errors.rejectValue("price", "price.length", "Price not empty");
        }

        if (price.length() == 0) {
            errors.rejectValue("price", "price.length", "Price not empty");
        }

        if (price.length() > 11 || price.length() < 2) {
            errors.rejectValue("price", "price.length", "Price size must be between 2 and 11");
        }
    }
}
