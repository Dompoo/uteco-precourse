package store.service.productService;

import java.time.LocalDate;
import java.util.List;
import store.domain.Product;
import store.dto.response.ProductResponse;
import store.infra.repository.Repository;

public class DefaultProductService implements ProductService {

    private final Repository<Product> productRepository;

    public DefaultProductService(Repository<Product> productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> readAllProducts(LocalDate localDate) {
        List<Product> products = productRepository.findAll();
        return ProductResponse.fromList(products, localDate);
    }
}
