package com.netcracker.crm.dao;

import com.netcracker.crm.domain.model.Product;
import com.netcracker.crm.domain.request.ProductRowRequest;

import java.util.List;

/**
 * @author YARUS
 */
public interface ProductDao extends CrudDao<Product> {
    Product findByTitle(String title);

    List<Product> findAllByGroupId(Long id);

    List<Product> findAllByPattern(String pattern);
    List<Product> findWithoutGroupByPattern(String pattern);
    List<Product> findActualByPatternAndCustomerId(String pattern, Long customerId);
    List<Product> findByPatternAndCustomerIdAndRegionId(String pattern, Long customerId, Long regionId);

    Long getProductRowsCount(ProductRowRequest orderRowRequest);
    List<Product> findProductRows(ProductRowRequest orderRowRequest);

    Boolean hasCustomerAccessToProduct(Long productId, Long customerId);
}
