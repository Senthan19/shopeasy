package com.senthan.shopeasy.Controller;

import com.senthan.shopeasy.Response.ApiResponse;
import com.senthan.shopeasy.Service.product.IProductService;
import com.senthan.shopeasy.exception.ProductNotFoundException;
import com.senthan.shopeasy.exception.ResourceNotFoundException;
import com.senthan.shopeasy.model.Product;
import com.senthan.shopeasy.request.AddProductRequest;
import com.senthan.shopeasy.request.UpdateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("Success: ", productList));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable("productId") Long id) {
        try {
            Product product =productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Found", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product product1 = productService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Add Product Success", product1));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Long productId, @RequestBody UpdateProductRequest request) {
        try {
            Product product = productService.updateProduct(request, productId);
            return ResponseEntity.ok(new ApiResponse("Update Success", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Found", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/products/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> products = productService.getProductByBrandAndName(brandName, productName);
            if (!products.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("Success:",products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product Not Found!",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductByCategoryAndBrand(category, brand);
            if (!products.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("Success:",products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product Not Found!",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductByName(name);
            if (!products.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("Success:",products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product Not Found!",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (!products.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("Success:",products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product Not Found!",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (!products.isEmpty()) {
                return ResponseEntity.ok(new ApiResponse("Success:",products));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product Not Found!",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/products/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            Long count = productService.countProductsByBrandAndName(brandName, productName);
            if (count  > 0) {
                return ResponseEntity.ok(new ApiResponse("Product Count:", count));
            }
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product Not Found!",null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
