package com.cg.controller.api;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.ProductCreateDto;
import com.cg.model.dto.ProductEditDto;
import com.cg.model.dto.ProductResponseDto;
import com.cg.repository.CategoryRepository;
import com.cg.repository.ColorRepository;
import com.cg.repository.SizeRepository;
import com.cg.service.color.IColorService;
import com.cg.service.product.IProductService;
import com.cg.service.productMedia.IProductMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductAPI {
    @Autowired
    private IProductService productService;

    @Autowired
    private IColorService colorService;
    @Autowired
    private IProductMediaService productMediaService;

    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam HashMap<String, String> hashMap) {
        List<?> list = productService.search(hashMap.get("search"));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
//        List<ProductResponseDto> productResponseDTOS = productService.findAllProduct();
        List<ProductResponseDto> productList = productService.findAllProductDeleteFalse();

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@ModelAttribute ProductCreateDto productCreateDto, BindingResult bindingResult) {

//        List<ObjectError> errors;
//        List<String> allErrors = new ArrayList<String>();
//        if (bindingResult.hasErrors()) {
//            errors = bindingResult.getAllErrors();
//            for (ObjectError error : errors) {
//                allErrors.add(error.getDefaultMessage());
//            }
//            throw new DataInputException(allErrors.toString());
//        } else {
        productCreateDto.setId(null);

        Product newProduct = productService.create(productCreateDto);

        ProductResponseDto productResponseDto = new ProductResponseDto();

        Optional<ProductMedia> productMediaOptional = productMediaService.findByProduct(newProduct);

        if (!productMediaOptional.isPresent()) {
            ProductMedia productMedia = new ProductMedia();
            productMedia.setId(null);
            productMedia.setFileName(null);
            productMedia.setFileFolder(null);
            productMedia.setFileUrl(null);
            productResponseDto = newProduct.toProductResponseDto(productMedia);
        } else {
            ProductMedia productMedia = productMediaOptional.get();
            productResponseDto = newProduct.toProductResponseDto(productMedia);
        }

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }


    @GetMapping("/size")
    public ResponseEntity<?> getAllSize() {
        List<Size> sizeList = sizeRepository.findAll();
        return new ResponseEntity<>(sizeList, HttpStatus.OK);
    }

    @GetMapping("/color")
    public ResponseEntity<?> getAllColor() {
        List<Color> colorList = colorRepository.findAll();
        return new ResponseEntity<>(colorList, HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @GetMapping("/{idDelete}")
    public ResponseEntity<?> findProductById(@PathVariable Long idDelete) {
        Optional<Product> product = productService.findById(idDelete);
        System.out.println(product.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);
        System.out.println(productOptional);
        if (!productOptional.isPresent()) {
            throw new DataInputException("Product invalid");
        }
        productService.softDelete(productOptional.get().getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/update/{idEdit}")
    public ResponseEntity<?> findEditProductById(@PathVariable Long idEdit) {
        Optional<Product> productOptional = productService.findById(idEdit);
        Product product = productOptional.get();
//        System.out.println("api" + product);
        Optional<ProductMedia> productMedia = productMediaService.findByProduct(product);
        return new ResponseEntity<>(productMedia.get(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> doUpdate(@ModelAttribute ProductEditDto productEditDto) {
        Optional<Product> productOptional = productService.findById(productEditDto.getId());

        if (productOptional == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product newProduct = productService.updateProduct(productEditDto);

        ProductResponseDto productResponseDto = new ProductResponseDto();

        Optional<ProductMedia> productMediaOptional = productMediaService.findByProduct(newProduct);

        if (!productMediaOptional.isPresent()) {
            ProductMedia productMedia = new ProductMedia();
            productMedia.setId(null);
            productMedia.setFileName(null);
            productMedia.setFileFolder(null);
            productMedia.setFileUrl(null);
            productResponseDto = newProduct.toProductResponseDto(productMedia);
        } else {
            ProductMedia productMedia = productMediaOptional.get();
            productResponseDto = newProduct.toProductResponseDto(productMedia);
        }

        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }
}
