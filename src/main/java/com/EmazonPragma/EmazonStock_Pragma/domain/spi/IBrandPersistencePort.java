package com.EmazonPragma.EmazonStock_Pragma.domain.spi;

import com.EmazonPragma.EmazonStock_Pragma.domain.model.Brand;

import java.util.List;

public interface IBrandPersistencePort {
    Brand saveBrand(Brand brand);
    List<Brand> listBrands(String sortBy, boolean ascending, int page, int size);
    Brand getBrand(Long id);
}
