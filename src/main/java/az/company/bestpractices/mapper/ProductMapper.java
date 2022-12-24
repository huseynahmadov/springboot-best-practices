package az.company.bestpractices.mapper;

import az.company.bestpractices.entity.Product;
import az.company.bestpractices.dto.request.CreateProductRequest;
import az.company.bestpractices.dto.response.ProductResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProductMapper {

    public static Product convertToEntity(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setProductType(request.getProductType());
        product.setQuantity(request.getQuantity());
        product.setPrice(request.getPrice());
        product.setSupplierName(request.getSupplierName());
        product.setSupplierCode(request.getSupplierCode());
        return product;
    }

    public static ProductResponse convertToResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setProductType(product.getProductType());
        response.setQuantity(product.getQuantity());
        response.setPrice(product.getPrice());
        response.setSupplierName(product.getSupplierName());
        response.setSupplierCode(product.getSupplierCode());
        return response;
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
