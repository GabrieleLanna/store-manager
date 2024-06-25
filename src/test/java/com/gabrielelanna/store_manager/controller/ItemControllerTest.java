package com.gabrielelanna.store_manager.controller;

import com.gabrielelanna.store_manager.model.dto.ItemDTO;
import com.gabrielelanna.store_manager.model.entity.Item;
import com.gabrielelanna.store_manager.model.mapper.ItemMapper;
import com.gabrielelanna.store_manager.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    @Mock
    private ItemMapper itemMapper;

    private Item item1;
    private Item item2;
    private ItemDTO itemDTO1;
    private ItemDTO itemDTO2;

    @BeforeEach
    void setUp() {

        item1 = new Item();
        item1.setId(1L);
        item1.setDescription("A nice item");
        item1.setPrice(10.0);
        item1.setAvailable(true);

        item2 = new Item();
        item2.setId(2L);
        item2.setDescription("Another nice item");
        item2.setPrice(15.0);
        item2.setAvailable(true);

        itemDTO1 = new ItemDTO();
        itemDTO1.setId(1L);
        itemDTO1.setDescription("A nice item");
        itemDTO1.setPrice(10.0);
        itemDTO1.setAvailable(true);

        itemDTO2 = new ItemDTO();
        itemDTO2.setId(2L);
        itemDTO2.setDescription("Another nice item");
        itemDTO2.setPrice(15.0);
        itemDTO2.setAvailable(true);
    }

    @Test
    void testGetAllItems() {
        when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));
        when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        when(itemMapper.toDTO(item2)).thenReturn(itemDTO2);

        ResponseEntity<List<ItemDTO>> response = itemController.getAllItems();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).containsExactlyInAnyOrder(itemDTO1, itemDTO2);
    }

    @Test
    void testGetItemById() {
        when(itemService.getItemById(1L)).thenReturn(Optional.of(item1));
        when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);

        ResponseEntity<ItemDTO> response = itemController.getItemById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(itemDTO1);
        verify(itemService).getItemById(1L);
    }

    @Test
    void testCreateItem() {
        when(itemMapper.toEntity(any(ItemDTO.class))).thenReturn(item1);
        when(itemService.saveItem(any(Item.class))).thenReturn(item1);
        when(itemMapper.toDTO(any(Item.class))).thenReturn(itemDTO1);

        ResponseEntity<ItemDTO> response = itemController.createItem(itemDTO1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(itemDTO1);
        verify(itemService).saveItem(any(Item.class));
    }

    @Test
    void testUpdateItem() {
        when(itemService.getItemById(any(Long.class))).thenReturn(Optional.of(item1));
        when(itemMapper.toEntity(any(ItemDTO.class))).thenReturn(item1);
        when(itemService.saveItem(any(Item.class))).thenReturn(item1);
        when(itemMapper.toDTO(any(Item.class))).thenReturn(itemDTO1);

        ResponseEntity<ItemDTO> response = itemController.updateItem(1L, itemDTO1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(itemDTO1);
        verify(itemService).saveItem(any(Item.class));
    }

    @Test
    void testDeleteItem() {
        when(itemService.getItemById(any(Long.class))).thenReturn(Optional.of(item1));

        ResponseEntity<Object> response = itemController.deleteItem(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(itemService).deleteItem(1L);
    }

    @Test
    void testSearchItemsByDescription() {
        when(itemService.findItemsByDescription("nice")).thenReturn(Arrays.asList(item1, item2));
        when(itemMapper.toDTO(item1)).thenReturn(itemDTO1);
        when(itemMapper.toDTO(item2)).thenReturn(itemDTO2);

        ResponseEntity<List<ItemDTO>> response = itemController.searchItemsByDescription("nice");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).containsExactlyInAnyOrder(itemDTO1, itemDTO2);
        verify(itemService).findItemsByDescription("nice");
    }
}
