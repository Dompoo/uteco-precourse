package store.service.productService;

import java.util.List;
import store.dto.response.ProductResponse;

public interface ProductService {

    List<ProductResponse> readAllProducts();
}
