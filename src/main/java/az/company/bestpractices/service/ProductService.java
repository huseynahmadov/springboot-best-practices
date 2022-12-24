package az.company.bestpractices.service;

import az.company.bestpractices.entity.Product;
import az.company.bestpractices.dto.request.CreateProductRequest;
import az.company.bestpractices.dto.response.ProductResponse;
import az.company.bestpractices.exception.ProductNotFoundException;
import az.company.bestpractices.exception.ProductServiceBusinessException;
import az.company.bestpractices.mapper.ProductMapper;
import az.company.bestpractices.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(CreateProductRequest request) throws ProductServiceBusinessException {
        ProductResponse response;
        try {
            log.info("ProductService:createProduct execution started.");
            Product product = ProductMapper.convertToEntity(request);
            log.debug("ProductService:createProduct request parameters {}", ProductMapper.jsonAsString(request));

            Product productResult = productRepository.save(product);
            response = ProductMapper.convertToResponse(productResult);
            log.debug("ProductService:createProduct received response from Database {}",
                    ProductMapper.jsonAsString(request));

        } catch (Exception exception) {
            log.error("Exception occurred while persisting product to database, Exception message {}",
                    exception.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while create a new product");
        }
        log.info("ProductService:createProduct execution ended.");

        return response;
    }

    @Cacheable(value = "product")
    public List<ProductResponse> getAllProducts() throws ProductServiceBusinessException {
        List<ProductResponse> response = null;

        try {
            log.info("ProductService:getAllProducts execution started.");

            List<Product> products = productRepository.findAll();

            if (!CollectionUtils.isEmpty(products)) {
                response = products
                        .stream()
                        .map(ProductMapper::convertToResponse)
                        .collect(Collectors.toList());
            } else {
                response = Collections.emptyList();
            }

            log.debug("ProductService:getProducts retrieving products from database  {}",
                    ProductMapper.jsonAsString(response));

        } catch (Exception exception) {
            log.error("Exception occurred while retrieving products from database , Exception message {}",
                    exception.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch all products from Database");
        }

        return response;
    }

    @Cacheable(value = "product")
    public ProductResponse getProductById(Long productId) {
        ProductResponse response;
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

            return ProductMapper.convertToResponse(product);

        } catch (Exception exception) {
            log.error("Exception occurred while retrieving product {} from database , Exception message {}",
                    productId, exception.getMessage());
            throw new ProductServiceBusinessException(
                    "Exception occurred while fetch product from Database " + productId);
        }
    }

    @Cacheable(value = "product")
    public Map<String, List<ProductResponse>> getProductsByTypes() {
        try {
            log.info("ProductService:getProductsByTypes execution started.");

            Map<String, List<ProductResponse>> productsMap =
                    productRepository.findAll().stream()
                            .map(ProductMapper::convertToResponse)
                            .filter(response -> response.getProductType() != null)
                            .collect(Collectors.groupingBy(ProductResponse::getProductType));

            log.info("ProductService:getProductsByTypes execution ended.");
            return productsMap;

        } catch (Exception ex) {
            log.error("Exception occurred while retrieving product grouping by type from database ," +
                    "Exception message {}", ex.getMessage());
            throw new ProductServiceBusinessException("Exception occurred while fetch product from Database ");
        }
    }

}
