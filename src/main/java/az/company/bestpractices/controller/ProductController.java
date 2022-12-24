package az.company.bestpractices.controller;

import az.company.bestpractices.dto.request.CreateProductRequest;
import az.company.bestpractices.dto.response.ApiResponse;
import az.company.bestpractices.dto.response.ProductResponse;
import az.company.bestpractices.mapper.ProductMapper;
import az.company.bestpractices.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    public static final String SUCCESS_MESSAGE = "Success";
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {

        log.info("ProductController::createNewProduct request body {}", ProductMapper.jsonAsString(request));

        ProductResponse response = productService.createProduct(request);

        ApiResponse<ProductResponse> serviceResponse = ApiResponse.<ProductResponse>builder()
                .status(SUCCESS_MESSAGE)
                .results(response)
                .build();

        log.info("ProductController::createNewProduct response {}", ProductMapper.jsonAsString(response));

        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProducts() {

        List<ProductResponse> products = productService.getAllProducts();
        ApiResponse<List<ProductResponse>> response = ApiResponse.<List<ProductResponse>>builder()
                .status(SUCCESS_MESSAGE)
                .results(products)
                .build();

        log.info("ProductController::getProducts response {}", ProductMapper.jsonAsString(response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable long productId) {

        log.info("ProductController::getProduct by id  {}", productId);

        ProductResponse response = productService.getProductById(productId);
        ApiResponse<ProductResponse> responseDTO = ApiResponse.<ProductResponse>builder()
                .status(SUCCESS_MESSAGE)
                .results(response)
                .build();

        log.info("ProductController::getProduct by id  {} response {}", productId, ProductMapper.jsonAsString(response));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/types")
    public ResponseEntity<ApiResponse> getProductsGroupByType() {

        Map<String, List<ProductResponse>> products = productService.getProductsByTypes();

        ApiResponse<Map<String, List<ProductResponse>>> response = ApiResponse.<Map<String, List<ProductResponse>>>builder()
                .status(SUCCESS_MESSAGE)
                .results(products)
                .build();

        log.info("ProductController::getProductsGroupByType by types  {}", ProductMapper.jsonAsString(response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
