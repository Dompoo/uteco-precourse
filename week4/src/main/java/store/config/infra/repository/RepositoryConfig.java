package store.config.infra.repository;

import java.util.ArrayList;
import java.util.List;
import store.infra.repository.Repository;

public class RepositoryConfig {

    private final List<Repository<?>> repositories = new ArrayList<>();

    public RepositoryConfig(ProductRepositoryConfig productRepositoryConfig) {
        repositories.add(productRepositoryConfig.getProductRepository());
    }

    public List<Repository<?>> getRepositories() {
        return this.repositories;
    }
}
