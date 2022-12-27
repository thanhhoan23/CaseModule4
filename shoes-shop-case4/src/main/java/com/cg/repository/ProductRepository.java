package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT NEW com.cg.model.dto.ProductResponseDto(" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.color.nameColor," +
            "p.size.numberSize, " +
            "p.category.typeProduct, " +
            "pm.id, " +
            "pm.fileName, " +
            "pm.fileFolder, " +
            "pm.fileUrl " +
            ") " +
            "FROM Product AS p " +
            "JOIN ProductMedia AS pm " +
            "ON pm.product = p " +
            "AND p.deleted = FALSE "
    )
    List<ProductResponseDto> findAllProductDeleteFalse();

    List<Product> findAllByDeletedIsFalse();

    @Modifying
    @Query("UPDATE Product AS p SET p.deleted = 1 WHERE p.id = :productId")
    void softDelete(@Param("productId") long productId);

    @Query("SELECT NEW com.cg.model.dto.ProductResponseDto(" +
            "p.id, " +
            "p.title, " +
            "p.price, " +
            "p.quantity, " +
            "p.description, " +
            "p.color.nameColor," +
            "p.size.numberSize, " +
            "p.category.typeProduct, " +
            "pm.id, " +
            "pm.fileName, " +
            "pm.fileFolder, " +
            "pm.fileUrl " +
            ") " +
            "FROM Product AS p " +
            "JOIN ProductMedia AS pm " +
            "ON pm.product = p " +
            "AND p.deleted = FALSE "+
            "WHERE p.title LIKE %:stringSearch%"


    )
    List<ProductResponseDto> findAlByTitleContaining(@Param("stringSearch") String stringSearch);



//    List<ProductResponseDto> findByTitleContainsAndDeletedEquals(@Param("stringSearch") String stringSearch);



}
