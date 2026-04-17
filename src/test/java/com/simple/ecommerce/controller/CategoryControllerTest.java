package com.simple.ecommerce.controller;

import com.simple.ecommerce.dto.CreateCategoryRequestDto;
import com.simple.ecommerce.schema.Category;
import com.simple.ecommerce.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @InjectMocks
    CategoryController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void createCategory_Returns201() throws Exception {
        //Arrange
        Category testCategory = Category.builder()
                .name("electronics").build();
        testCategory.setId(1L);
        Mockito.when(categoryService.createCategory(any(CreateCategoryRequestDto.class))).thenReturn(testCategory);

        //Act & assert
        mockMvc.perform(post(
                                "/api/v1/categories"
                        ).accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"name\":\"electronics\"}")
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("electronics"));

    }
}
