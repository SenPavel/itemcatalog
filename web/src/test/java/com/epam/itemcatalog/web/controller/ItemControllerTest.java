package com.epam.itemcatalog.web.controller;

import com.epam.itemcatalog.model.dto.ItemDto;
import com.epam.itemcatalog.service.CurrencyService;
import com.epam.itemcatalog.service.ItemService;
import com.epam.itemcatalog.web.config.SpringWebConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = SpringWebConfig.class)
@WebAppConfiguration
public class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private CurrencyService currencyService;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private ItemDto firstItemDto;

    private ItemDto secondItemDto;

    private List<ItemDto> testItemDtoList;

    @InjectMocks
    private ItemController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlingController())
                .build();

        firstItemDto = new ItemDto();
        firstItemDto.setId(1L);
        firstItemDto.setName("first");
        firstItemDto.setPrice(new BigDecimal(1.0));

        secondItemDto = new ItemDto();
        secondItemDto.setId(2L);
        secondItemDto.setName("second");
        secondItemDto.setPrice(new BigDecimal(2.0));

        testItemDtoList = new ArrayList<>(Arrays.asList(firstItemDto, secondItemDto));
    }

    @Test
    public void shouldShowAllItems() throws Exception {
        when(itemService.findAllWithSorting(anyString(), anyString())).thenReturn(testItemDtoList);
        mockMvc.perform(get("/v1/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(firstItemDto.getName())))
                .andExpect(jsonPath("$[0].price").value(firstItemDto.getPrice()))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is(secondItemDto.getName())))
                .andExpect(jsonPath("$[1].price").value(secondItemDto.getPrice()));
    }

    @Test
    public void shouldFindById() throws Exception {
        when(itemService.findById(anyLong())).thenReturn(firstItemDto);

        mockMvc.perform(get("/v1/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(firstItemDto.getName())))
                .andExpect(jsonPath("$.price").value(firstItemDto.getPrice()));
    }

    @Test
    public void shouldFindByName() throws Exception {
        when(itemService.findByName(anyString())).thenReturn(firstItemDto);

        mockMvc.perform(get("/v1/items?name=first"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(firstItemDto.getName())))
                .andExpect(jsonPath("$.price").value(firstItemDto.getPrice()));
    }

    @Test
    public void shouldSaveItem() throws Exception {
        when(itemService.save(firstItemDto)).thenReturn(firstItemDto);

        mockMvc.perform(post("/v1/items")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(toJson(firstItemDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is(firstItemDto.getName())))
                .andExpect(jsonPath("$.price").value(firstItemDto.getPrice()));
    }

    @Test
    public void shouldUpdateItem() throws Exception {
        mockMvc.perform(put("/v1/items/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("id", firstItemDto.getId().toString())
                .content(toJson(firstItemDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteItem() throws Exception {
        mockMvc.perform(delete("/v1/items/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("id", firstItemDto.getId().toString()))
                .andExpect(status().isNoContent());
    }

    private  String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}