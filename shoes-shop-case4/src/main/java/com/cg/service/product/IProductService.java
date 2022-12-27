package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductCreateDto;
import com.cg.model.dto.ProductEditDto;
import com.cg.model.dto.ProductResponseDto;
import com.cg.service.IGeneralService;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    public List<ProductResponseDto> findAllProduct();

    Product create(ProductCreateDto productCreateDto);

    void softDelete(long productId);

    List<ProductResponseDto> findAllProductDeleteFalse();

    Product updateProduct(ProductEditDto productEditDto);

    List<ProductResponseDto> search(String query);
}
