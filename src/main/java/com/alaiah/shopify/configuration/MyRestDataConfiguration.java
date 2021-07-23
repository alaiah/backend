package com.alaiah.shopify.configuration;

import com.alaiah.shopify.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;


    private EntityManager entityManager;

    @Autowired
    public MyRestDataConfiguration(EntityManager theEntityManager) {

        this.entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {



        this.disableHttpMethods(config, Product.class);
        this.disableHttpMethods(config, ProductCategory.class);
        this.disableHttpMethods(config, Country.class);
        this.disableHttpMethods(config, State.class);


        exposeIds(config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);

    }

    private void disableHttpMethods(RepositoryRestConfiguration config, Class myClass) {

        HttpMethod[] disallowedHttpMethods = {HttpMethod.DELETE, HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH};

        config.getExposureConfiguration()
                .forDomainType(myClass)
                .withItemExposure((meta, http) -> http.disable(disallowedHttpMethods))
                .withCollectionExposure((meta, http) -> http.disable(disallowedHttpMethods));

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
