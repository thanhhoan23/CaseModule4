package com.cg.service.product;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dto.ProductCreateDto;
import com.cg.model.dto.ProductEditDto;
import com.cg.model.dto.ProductResponseDto;
import com.cg.repository.*;
import com.cg.service.color.IColorService;
import com.cg.service.upload.IUploadService;
import com.cg.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

@Service
@Transactional
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMediaRepository productMediaRepository;

    @Autowired
    private ProductMediaRepository mediaRepository;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtil uploadUtil;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product getById(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void remove(Long id) {
    }

    public List<ProductResponseDto> findAllProduct() {
        List<Product> productList = productRepository.findAllByDeletedIsFalse();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();

        for (Product product : productList) {
            productResponseDtos.add(product.toProductResponseDto(mediaRepository.findByProduct(product).get()));
        }
        return productResponseDtos;
    }


    @Override
    public Product create(ProductCreateDto productCreateDto) {
        Product product = productCreateDto.toProduct();
        Optional<Color> colorOptional = colorRepository.findAllById(Integer.valueOf(productCreateDto.getColor()));
        Optional<Size> sizeOptional = sizeRepository.findAllById(Integer.valueOf(productCreateDto.getSize()));
        Optional<Category> categoryOptional = categoryRepository.findAllById(Integer.valueOf(productCreateDto.getCategory()));

        if (!colorOptional.isPresent()) {
            throw new DataInputException("Color invalid");
        }
        if (!sizeOptional.isPresent()) {
            throw new DataInputException("Size invalid");
        }
        if (!categoryOptional.isPresent()) {
            throw new DataInputException("Category invalid");
        }

        product.setColor(colorOptional.get());
        product.setSize(sizeOptional.get());
        product.setCategory(categoryOptional.get());
        product.setId(null);
        product.setQuantity(0L);
        productRepository.save(product);

        String fileType = productCreateDto.getFile().getContentType();

        assert fileType != null;

        fileType = fileType.substring(0, 5);

        ProductMedia productMedia = new ProductMedia();
        productMedia.setId(null);
        productMedia.setFileType(fileType);
        productMediaRepository.save(productMedia);

        uploadAndSaveProductImage(productCreateDto, product, productMedia);

        return product;
    }

    @Override
    public void softDelete(long productId) {
        productRepository.softDelete(productId);
    }

    @Override
    public List<ProductResponseDto> findAllProductDeleteFalse() {
        return productRepository.findAllProductDeleteFalse();
    }

    private void uploadAndSaveProductImage(ProductCreateDto productCreateDto, Product product, ProductMedia productMedia) {
        try {
            Map util = uploadUtil.buildImageUploadParams(productMedia);
            MultipartFile file = productCreateDto.getFile();
            Map uploadResult = uploadService.uploadImage(file, util);
            System.out.println(uploadResult);
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productMedia.setFileName(productMedia.getId() + "." + fileFormat);
            productMedia.setFileUrl(fileUrl);
            productMedia.setFileFolder(uploadUtil.IMAGE_UPLOAD_FOLDER);
            productMedia.setCloudId(productMedia.getFileFolder() + "/" + productMedia.getId());
            productMedia.setProduct(product);
            productMediaRepository.save(productMedia);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("khong tim thay");
        }
    }

    @Override
    public Product updateProduct(ProductEditDto productEditDto) {
        Product product = productEditDto.toProduct();
        Optional<Color> colorOptional = colorRepository.findAllById(Integer.valueOf(productEditDto.getColor()));
        Optional<Size> sizeOptional = sizeRepository.findAllById(Integer.valueOf(productEditDto.getSize()));
        Optional<Category> categoryOptional = categoryRepository.findAllById(Integer.valueOf(productEditDto.getCategory()));

        if (!colorOptional.isPresent()) {
            throw new DataInputException("Color invalid");
        }
        if (!sizeOptional.isPresent()) {
            throw new DataInputException("Size invalid");
        }
        if (!categoryOptional.isPresent()) {
            throw new DataInputException("Category invalid");
        }

        product.setColor(colorOptional.get());
        product.setSize(sizeOptional.get());
        product.setCategory(categoryOptional.get());
        product.setId(productEditDto.getId());
        product.setQuantity(0L);
        productRepository.save(product);

        String fileType = productEditDto.getFile().getContentType();

        assert fileType != null;


        fileType = fileType.substring(0, 5);
        ProductMedia productMedia = mediaRepository.findByProduct(product).get();
//        productMedia.setId(null);
        productMedia.setFileType(fileType);
        productMediaRepository.save(productMedia);
        uploadAndSaveEditProductImage(productEditDto, product, productMedia);

        return product;
    }

    @Override
    public List<ProductResponseDto> search(String query) {
//        return productRepository.findByTitleContainsAndDeletedEquals("m", false)
//                .stream().map(Product::toDTO).collect(Collectors.toList());
//        List<ProductResponseDto> dtoList = new ArrayList<>();
        List<ProductResponseDto> list = productRepository.findAlByTitleContaining(query);
//        for (Product product : list) {
//            dtoList.add(product.toDTO());
//        }
        return list;
    }

    private void uploadAndSaveEditProductImage(ProductEditDto productEditDto, Product product, ProductMedia productMedia) {
        try {
            Map util = uploadUtil.buildImageUploadParams(productMedia);
            MultipartFile file = productEditDto.getFile();
            Map uploadResult = uploadService.uploadImage(file, util);
            System.out.println(uploadResult);
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            productMedia.setFileName(productMedia.getId() + "." + fileFormat);
            productMedia.setFileUrl(fileUrl);
            productMedia.setFileFolder(uploadUtil.IMAGE_UPLOAD_FOLDER);
            productMedia.setCloudId(productMedia.getFileFolder() + "/" + productMedia.getId());
            productMedia.setProduct(product);
            productMediaRepository.save(productMedia);

        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("khong tim thay");
        }
    }
}
