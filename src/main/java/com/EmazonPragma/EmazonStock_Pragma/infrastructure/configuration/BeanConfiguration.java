package com.EmazonPragma.EmazonStock_Pragma.infrastructure.configuration;

import com.EmazonPragma.EmazonStock_Pragma.domain.api.IBrandServicePort;
import com.EmazonPragma.EmazonStock_Pragma.domain.api.ICategoryServicePort;
import com.EmazonPragma.EmazonStock_Pragma.domain.api.IItemServicePort;
import com.EmazonPragma.EmazonStock_Pragma.domain.spi.IBrandPersistencePort;
import com.EmazonPragma.EmazonStock_Pragma.domain.spi.ICategoryPersistencePort;
import com.EmazonPragma.EmazonStock_Pragma.domain.spi.IItemPersistencePort;
import com.EmazonPragma.EmazonStock_Pragma.domain.usecase.BrandUseCase;
import com.EmazonPragma.EmazonStock_Pragma.domain.usecase.CategoryUseCase;
import com.EmazonPragma.EmazonStock_Pragma.domain.usecase.ItemUseCase;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.adapter.BrandJpaAdapter;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.adapter.ItemJpaAdapter;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.mapper.CategoryEntityMapper;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.mapper.ItemEntityMapper;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.repository.IBrandRepository;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.repository.ICategoryRepository;
import com.EmazonPragma.EmazonStock_Pragma.infrastructure.output.jpa.repository.IItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final IBrandRepository brandRepository;
    private final CategoryEntityMapper categoryEntityMapper;
    private final BrandEntityMapper brandEntityMapper;
    private final IItemRepository itemRepository;
    private final ItemEntityMapper itemEntityMapper;
    private final UserDetailsService userDetailsService;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IBrandPersistencePort brandPersistencePort() {
        return new BrandJpaAdapter(brandRepository, brandEntityMapper);
    }

    @Bean
    public IBrandServicePort brandServicePort() {
        return new BrandUseCase(brandPersistencePort());
    }

    @Bean
    public IItemPersistencePort itemPersistencePort() {
        return new ItemJpaAdapter(itemRepository, itemEntityMapper, categoryRepository, categoryEntityMapper);
    }

    @Bean
    public IItemServicePort itemServicePort() {
        return new ItemUseCase(itemPersistencePort());
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}
