package com.tt.tutorials.spring.data.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface definition for Products
 *
 * @author Thomson Tang
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    /**
     * 查询包含指定描述信息的商品
     *
     * @param description 指定的商品描述信息
     * @param pageable    分页对象
     * @return
     */
    Page<Product> findByDescriptionContaining(String description, Pageable pageable);

    /**
     * 查询拥有指定属性的商品
     *
     * @param attribute 指定的商品属性
     * @param price     指定的商品价格
     * @return
     */
    @Query("select p from Product p where p.attributes[?1] = ?2")
    List<Product> findByAttributeAndPrice(String attribute, String price);
}
