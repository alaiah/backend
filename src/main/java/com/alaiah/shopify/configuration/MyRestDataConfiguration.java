package com.alaiah.shopify.configuration;

import com.alaiah.shopify.entity.Product;
import com.alaiah.shopify.entity.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

@Configuration
public class MyRestDataConfiguration implements RepositoryRestConfigurer {


    private EntityManager entityManager;

    @Autowired
    public MyRestDataConfiguration(EntityManager theEntityManager) {
        this.entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] disallowedHttpMethods = {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH};

        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((meta, http) -> http.disable(disallowedHttpMethods))
                .withCollectionExposure((meta, http) -> http.disable(disallowedHttpMethods));


        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((meta, http) -> http.disable(disallowedHttpMethods))
                .withCollectionExposure((meta, http) -> http.disable(disallowedHttpMethods));


        exposeIds(config);

    }

    private void exposeIds(RepositoryRestConfiguration config) {

        Set<EntityType<?>> entities = this.entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();
        for (EntityType tempEntityType: entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);

    }
}
