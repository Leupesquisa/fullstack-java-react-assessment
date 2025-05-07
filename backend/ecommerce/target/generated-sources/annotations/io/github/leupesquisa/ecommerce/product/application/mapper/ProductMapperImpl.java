package io.github.leupesquisa.ecommerce.product.application.mapper;

import io.github.leupesquisa.ecommerce.product.application.dto.ProductRequestDto;
import io.github.leupesquisa.ecommerce.product.application.dto.ProductResponseDto;
import io.github.leupesquisa.ecommerce.product.domain.Product;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-07T21:05:53+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDto toResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        UUID id = null;
        String sku = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        Integer stock = null;
        String category = null;
        String imageUrl = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = product.getId();
        sku = product.getSku();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        stock = product.getStock();
        category = product.getCategory();
        imageUrl = product.getImageUrl();
        createdAt = product.getCreatedAt();
        updatedAt = product.getUpdatedAt();

        ProductResponseDto productResponseDto = new ProductResponseDto( id, sku, name, description, price, stock, category, imageUrl, createdAt, updatedAt );

        return productResponseDto;
    }

    @Override
    public Product toEntity(ProductRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setSku( requestDto.sku() );
        product.setName( requestDto.name() );
        product.setDescription( requestDto.description() );
        product.setPrice( requestDto.price() );
        product.setStock( requestDto.stock() );
        product.setCategory( requestDto.category() );
        product.setImageUrl( requestDto.imageUrl() );

        return product;
    }

    @Override
    public void updateEntityFromDto(ProductRequestDto requestDto, Product product) {
        if ( requestDto == null ) {
            return;
        }

        product.setSku( requestDto.sku() );
        product.setName( requestDto.name() );
        product.setDescription( requestDto.description() );
        product.setPrice( requestDto.price() );
        product.setStock( requestDto.stock() );
        product.setCategory( requestDto.category() );
        product.setImageUrl( requestDto.imageUrl() );
    }
}
