package store.service.productService;

import java.time.LocalDate;
import java.util.List;
import store.dto.response.ProductResponse;

public interface ProductService {

    List<ProductResponse> readAllProducts(LocalDate localDate);
}
