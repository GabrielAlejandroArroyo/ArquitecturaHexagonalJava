package com.tuempresa.proyecto.application.mapper;

import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.domain.model.Product;
import com.tuempresa.proyecto.domain.valueobject.Money;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-08T18:10:47-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setPrice( productPriceAmount( product ) );
        productResponse.setCurrency( productPriceCurrencyCode( product ) );
        productResponse.setId( product.getId() );
        productResponse.setName( product.getName() );
        productResponse.setDescription( product.getDescription() );
        productResponse.setStock( product.getStock() );
        productResponse.setCategory( product.getCategory() );
        productResponse.setCreatedAt( product.getCreatedAt() );
        productResponse.setUpdatedAt( product.getUpdatedAt() );
        productResponse.setActive( product.isActive() );

        return productResponse;
    }

    @Override
    public List<ProductResponse> toResponseList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( products.size() );
        for ( Product product : products ) {
            list.add( toResponse( product ) );
        }

        return list;
    }

    private BigDecimal productPriceAmount(Product product) {
        if ( product == null ) {
            return null;
        }
        Money price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        BigDecimal amount = price.getAmount();
        if ( amount == null ) {
            return null;
        }
        return amount;
    }

    private String productPriceCurrencyCode(Product product) {
        if ( product == null ) {
            return null;
        }
        Money price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        String currencyCode = price.getCurrencyCode();
        if ( currencyCode == null ) {
            return null;
        }
        return currencyCode;
    }
}
