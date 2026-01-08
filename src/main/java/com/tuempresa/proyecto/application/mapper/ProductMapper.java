package com.tuempresa.proyecto.application.mapper;

import com.tuempresa.proyecto.application.dto.response.ProductResponse;
import com.tuempresa.proyecto.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "price.currencyCode", target = "currency")
    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);
}
