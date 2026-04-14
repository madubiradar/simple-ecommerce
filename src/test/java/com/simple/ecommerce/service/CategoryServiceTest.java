package com.simple.ecommerce.service;

import com.simple.ecommerce.dto.CreateCategoryRequestDto;
import com.simple.ecommerce.exceptions.ResourceNotFoundException;
import com.simple.ecommerce.repositories.CategoryRepository;
import com.simple.ecommerce.schema.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createCategoryTest_savesAndReturnCategory() {
        //Arrange
        CreateCategoryRequestDto createCategoryRequestDto = CreateCategoryRequestDto.builder()
                .name("test category")
                .build();
        Category category = Category.builder()
                        .name("test category")
                        .build();
        category.setId(1L);

        Mockito.when(categoryRepository.save(any(Category.class))).thenReturn(category);

        //Act
        Category result = categoryService.createCategory(createCategoryRequestDto);

        //assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(category.getName(), result.getName());
        Assertions.assertEquals(category.getId(), result.getId());


    }

    @Test
    void findCategoryByIdTest_whenFoundReturnCategory() {

        Category testCategory = Category.builder()
                .name("test")
                .build();
        testCategory.setId(1L);
        Mockito.when(categoryRepository.findById(testCategory.getId())).thenReturn(Optional.of(testCategory));


        //Act
        Category result = categoryService.getCategoryById(testCategory.getId());
        Assertions.assertNotNull(testCategory);

        //Assert
        Assertions.assertEquals(testCategory.getId(), result.getId());
        Assertions.assertEquals(testCategory.getName(), result.getName());
    }

    @Test
    void findCategoryByIdTest_whenNotFoundThrowsException() {
        //Arrange
        Mockito.when(categoryRepository.findById(2L)).thenReturn(Optional.empty());
        //Act
        Assertions.assertThrows(ResourceNotFoundException.class,
                ()-> categoryService.getCategoryById(2L));
        verify(categoryRepository, times(1)).findById(2L);
    }

    @Test
    void deleteCategoryById_whenSuccessfullyDeletesCategory() {
        Mockito.doNothing().when(categoryRepository).deleteById(1L);
        //Act
         categoryService.deleteCategoryById(1L);

         verify(categoryRepository, Mockito.times(1)).deleteById(1L);

    }

}
