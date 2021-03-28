package ch.hftm.ggq.enumerations;

import java.util.Map;
import java.util.function.BiConsumer;

public enum TemplateType {
    JPA_REST(
            (basePackagePath, entityTemplates) -> {
                entityTemplates.put("src/main/java/" + basePackagePath + "/domain/{EntityName}.java", "xsl/code-gen-java/JPA_REST/entity/entity.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/{EntityName}Dto.java", "xsl/code-gen-java/JPA_REST/entity/entityDto.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/{EntityName}Repository.java", "xsl/code-gen-java/JPA_REST/entity/repository.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/impl/{EntityName}RepositoryImpl.java", "xsl/code-gen-java/JPA_REST/entity/repositoryImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/{EntityName}Service.java", "xsl/code-gen-java/JPA_REST/entity/service.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/mapper/{EntityName}Mapper.java", "xsl/code-gen-java/JPA_REST/entity/entityDtoMapper.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/impl/{EntityName}ServiceImpl.java", "xsl/code-gen-java/JPA_REST/entity/serviceImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/{EntityName}Resource.java", "xsl/code-gen-java/JPA_REST/entity/restResource.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/repository/{EntityName}RepositoryTest.java", "xsl/code-gen-java/JPA_REST/entity/repositoryTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/service/{EntityName}ServiceTest.java", "xsl/code-gen-java/JPA_REST/entity/serviceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceTest.java", "xsl/code-gen-java/JPA_REST/entity/restResourceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceIT.java", "xsl/code-gen-java/JPA_REST/entity/restResourceIT.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/util/{EntityName}TestUtil.java", "xsl/code-gen-java/JPA_REST/entity/entityTestUtil.xsl");
            },
            (basePackagePath, nonEntityTemplates) -> {
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/util/RandomGenerator.java", "xsl/code-gen-java/JPA_REST/default/randomGenerator.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/exceptions/ExceptionDto.java", "xsl/code-gen-java/JPA_REST/default/exceptionDto.xsl");
                nonEntityTemplates.put("src/main/resources/application.properties", "xsl/code-gen-java/JPA_REST/default/applicationProperties.xsl");
                nonEntityTemplates.put("src/test/resources/application.properties", "xsl/code-gen-java/JPA_REST/default/applicationPropertiesTest.xsl");
                nonEntityTemplates.put("src/main/resources/META-INF/resources/index.html", "xsl/code-gen-java/JPA_REST/default/indexHtml.xsl");
                nonEntityTemplates.put("pom.xml", "xsl/code-gen-java/JPA_REST/default/pom.xsl");
                nonEntityTemplates.put("README.md", "xsl/code-gen-java/JPA_REST/default/readme.xsl");
            }),
    HIBERNATE_PANACHE_REST(
            (basePackagePath, entityTemplates) -> {
                entityTemplates.put("src/main/java/" + basePackagePath + "/domain/{EntityName}.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/entity.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/{EntityName}Dto.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/entityDto.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/impl/{EntityName}RepositoryImpl.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/repositoryImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/{EntityName}Repository.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/repository.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/{EntityName}Service.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/service.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/mapper/{EntityName}Mapper.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/entityDtoMapper.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/impl/{EntityName}ServiceImpl.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/serviceImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/{EntityName}Resource.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/restResource.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/service/{EntityName}ServiceTest.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/serviceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceTest.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/restResourceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceIT.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/restResourceIT.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/util/{EntityName}TestUtil.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/entity/entityTestUtil.xsl");
            },
            (basePackagePath, nonEntityTemplates) -> {
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/util/RandomGenerator.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/randomGenerator.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/exceptions/ExceptionHandler.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/exceptionHandler.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/dto/PageRequest.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/pageRequest.xsl");
                nonEntityTemplates.put("src/main/resources/application.properties", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/applicationProperties.xsl");
                nonEntityTemplates.put("src/test/resources/application.properties", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/applicationPropertiesTest.xsl");
                nonEntityTemplates.put("src/main/resources/META-INF/resources/index.html", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/indexHtml.xsl");
                nonEntityTemplates.put("pom.xml", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/pom.xsl");
                nonEntityTemplates.put("README.md", "xsl/code-gen-java/HIBERNATE_PABACHE_REST/default/readme.xsl");
            }),
    MONGO_PANACHE_REST(
            (basePackagePath, entityTemplates) -> {
                entityTemplates.put("src/main/java/" + basePackagePath + "/domain/{EntityName}.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/entity.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/{EntityName}Dto.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/entityDto.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/impl/{EntityName}RepositoryImpl.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/repositoryImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/{EntityName}Repository.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/repository.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/{EntityName}Service.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/service.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/mapper/{EntityName}Mapper.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/entityDtoMapper.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/impl/{EntityName}ServiceImpl.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/serviceImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/{EntityName}Resource.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/restResource.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/service/{EntityName}ServiceTest.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/serviceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceTest.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/restResourceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceIT.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/restResourceIT.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/util/{EntityName}TestUtil.java", "xsl/code-gen-java/MONGO_PANACHE_REST/entity/entityTestUtil.xsl");
            },
            (basePackagePath, nonEntityTemplates) -> {

                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/conf/MongoTestResourceLifecycleManager.java", "xsl/code-gen-java/MONGO_PANACHE_REST/default/mongoTestResourceLifecycleManager.xsl");
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/util/RandomGenerator.java", "xsl/code-gen-java/MONGO_PANACHE_REST/default/randomGenerator.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/exceptions/ExceptionHandler.java", "xsl/code-gen-java/MONGO_PANACHE_REST/default/exceptionHandler.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/dto/PageRequest.java", "xsl/code-gen-java/MONGO_PANACHE_REST/default/pageRequest.xsl");
                nonEntityTemplates.put("src/main/resources/application.properties", "xsl/code-gen-java/MONGO_PANACHE_REST/default/applicationProperties.xsl");
                nonEntityTemplates.put("src/test/resources/application.properties", "xsl/code-gen-java/MONGO_PANACHE_REST/default/applicationPropertiesTest.xsl");
                nonEntityTemplates.put("src/main/resources/META-INF/resources/index.html", "xsl/code-gen-java/MONGO_PANACHE_REST/default/indexHtml.xsl");
                nonEntityTemplates.put("pom.xml", "xsl/code-gen-java/MONGO_PANACHE_REST/default/pom.xsl");
                nonEntityTemplates.put("README.md", "xsl/code-gen-java/MONGO_PANACHE_REST/default/readme.xsl");
                nonEntityTemplates.put("src/main/docker/docker-compose.yml", "xsl/code-gen-java/MONGO_PANACHE_REST/default/docker-compose.xsl");
            }),
    MONGO_PANACHE_REST_OIDC(
            (basePackagePath, entityTemplates) -> {
                entityTemplates.put("src/main/java/" + basePackagePath + "/domain/{EntityName}.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/entity.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/{EntityName}Dto.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/entityDto.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/impl/{EntityName}RepositoryImpl.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/repositoryImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/{EntityName}Repository.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/repository.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/{EntityName}Service.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/service.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/mapper/{EntityName}Mapper.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/entityDtoMapper.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/impl/{EntityName}ServiceImpl.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/serviceImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/{EntityName}Resource.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/restResource.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/service/{EntityName}ServiceTest.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/serviceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceTest.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/restResourceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceIT.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/restResourceIT.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/util/{EntityName}TestUtil.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/entity/entityTestUtil.xsl");
            },
            (basePackagePath, nonEntityTemplates) -> {
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/util/RandomGenerator.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/randomGenerator.xsl");
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/conf/MongoTestResourceLifecycleManager.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/mongoTestResourceLifecycleManager.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/exceptions/ExceptionHandler.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/exceptionHandler.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/dto/PageRequest.java", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/pageRequest.xsl");
                nonEntityTemplates.put("src/main/resources/application.properties", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/applicationProperties.xsl");
                nonEntityTemplates.put("src/test/resources/application.properties", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/applicationPropertiesTest.xsl");
                nonEntityTemplates.put("src/main/resources/META-INF/resources/index.html", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/indexHtml.xsl");
                nonEntityTemplates.put("pom.xml", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/pom.xsl");
                nonEntityTemplates.put("README.md", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/readme.xsl");
                nonEntityTemplates.put("src/main/docker/docker-compose.yml", "xsl/code-gen-java/MONGO_PANACHE_REST_OIDC/default/docker-compose.xsl");
            }),
    HIBERNATE_PANACHE_REST_KAFKA(
            (basePackagePath, entityTemplates) -> {
                entityTemplates.put("src/main/java/" + basePackagePath + "/domain/{EntityName}.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/entity.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/{EntityName}Dto.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/entityDto.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/{EntityName}Event.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/entityEvent.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/impl/{EntityName}RepositoryImpl.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/repositoryImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/repository/{EntityName}Repository.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/repository.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/{EntityName}Service.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/service.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/publisher/{EntityName}Publisher.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/publisher.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/mapper/{EntityName}Mapper.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/entityDtoMapper.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/impl/{EntityName}ServiceImpl.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/serviceImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/publisher/impl/{EntityName}PublisherImpl.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/publisherImpl.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/{EntityName}Resource.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/restResource.xsl");
                entityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/serializers/{EntityName}EventJsonDeserializer.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/entityEventJsonDeserializer.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/service/{EntityName}ServiceTest.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/serviceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceTest.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/restResourceTest.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/web/rest/{EntityName}ResourceIT.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/restResourceIT.xsl");
                entityTemplates.put("src/test/java/" + basePackagePath + "/util/{EntityName}TestUtil.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/entity/entityTestUtil.xsl");
            },
            (basePackagePath, nonEntityTemplates) -> {
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/util/RandomGenerator.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/randomGenerator.xsl");
                nonEntityTemplates.put("src/test/java/" + basePackagePath + "/conf/KafkaTestResourceLifecycleManager.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/kafkaTestResourceLifecycleManager.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/exceptions/ExceptionHandler.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/exceptionHandler.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/web/rest/dto/PageRequest.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/pageRequest.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/enumerations/EventType.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/eventType.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/service/dto/serializers/GenericJsonSerializer.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/genericJsonSerializer.xsl");
                nonEntityTemplates.put("src/main/java/" + basePackagePath + "/service/consumer/ExampleConsumer.java", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/exampleConsumer.xsl");
                nonEntityTemplates.put("src/main/resources/application.properties", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/applicationProperties.xsl");
                nonEntityTemplates.put("src/test/resources/application.properties", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/applicationPropertiesTest.xsl");
                nonEntityTemplates.put("src/main/resources/META-INF/resources/index.html", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/indexHtml.xsl");
                nonEntityTemplates.put("pom.xml", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/pom.xsl");
                nonEntityTemplates.put("README.md", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/readme.xsl");
                nonEntityTemplates.put("src/main/docker/docker-compose.yml", "xsl/code-gen-java/HIBERNATE_PABACHE_REST_KAFKA/default/docker-compose.xsl");
            });

    private final BiConsumer<String, Map<String, String>> entityTemplateInitializer;
    private final BiConsumer<String, Map<String, String>> nonEntityTemplateInitializer;

    TemplateType(BiConsumer<String, Map<String, String>> entityTemplateInitializer, BiConsumer<String, Map<String, String>> nonEntityTemplateInitializer) {
        this.entityTemplateInitializer = entityTemplateInitializer;
        this.nonEntityTemplateInitializer = nonEntityTemplateInitializer;
    }

    public void addEntityTemplates(String basePackagePath, Map<String, String> nonEntityTemplates) {
        entityTemplateInitializer.accept(basePackagePath, nonEntityTemplates);
    }

    public void addNonEntityTemplates(String basePackagePath, Map<String, String> nonEntityTemplates) {
        nonEntityTemplateInitializer.accept(basePackagePath, nonEntityTemplates);
    }
}
