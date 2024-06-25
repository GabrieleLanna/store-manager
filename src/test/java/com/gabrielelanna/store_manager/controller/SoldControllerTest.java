package com.gabrielelanna.store_manager.controller;

import com.gabrielelanna.store_manager.model.dto.ItemDTO;
import com.gabrielelanna.store_manager.model.dto.SoldDTO;
import com.gabrielelanna.store_manager.model.entity.Item;
import com.gabrielelanna.store_manager.model.entity.Sold;
import com.gabrielelanna.store_manager.model.mapper.SoldMapper;
import com.gabrielelanna.store_manager.service.SoldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SoldControllerTest {

    @InjectMocks
    private SoldController soldController;

    @Mock
    private SoldService soldService;

    @Mock
    private SoldMapper soldMapper;

    private Sold sold1;
    private Sold sold2;
    private SoldDTO soldDTO1;
    private SoldDTO soldDTO2;

    @BeforeEach
    public void setUp() {

        Item item = Item.builder()
                .id(1L)
                .description("A nice item")
                .price(10.0)
                .available(true)
                .build();

        ItemDTO itemDTO = ItemDTO.builder()
            .id(1L)
            .description("A nice item")
            .price(10.0)
            .available(true)
            .build();

        sold1 = Sold.builder()
            .id(1L)
            .item(item)
            .saleDate(LocalDateTime.now())
            .build();

        sold2 = Sold.builder()
                .id(2L)
                .item(item)
                .saleDate(LocalDateTime.now().minusDays(1))
                .build();

        soldDTO1 = new SoldDTO();
        soldDTO1.setId(1L);
        soldDTO1.setItem(itemDTO);
        soldDTO1.setSaleDate(LocalDateTime.now());

        soldDTO2 = new SoldDTO();
        soldDTO2.setId(2L);
        soldDTO2.setItem(itemDTO);
        soldDTO2.setSaleDate(LocalDateTime.now().minusDays(1));
    }

    @Test
    public void testGetAllSoldItems() {
        when(soldService.getAllSoldItems()).thenReturn(Arrays.asList(sold1, sold2));
        when(soldMapper.toDTO(sold1)).thenReturn(soldDTO1);
        when(soldMapper.toDTO(sold2)).thenReturn(soldDTO2);

        ResponseEntity<List<SoldDTO>> response = soldController.getAllSoldItems();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()).containsExactlyInAnyOrder(soldDTO1, soldDTO2);
        verify(soldService).getAllSoldItems();
    }

    @Test
    public void testGetSoldItemById() {
        when(soldService.getSoldById(1L)).thenReturn(Optional.of(sold1));
        when(soldMapper.toDTO(sold1)).thenReturn(soldDTO1);

        ResponseEntity<SoldDTO> response = soldController.getSoldItemById(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(soldDTO1);
        verify(soldService).getSoldById(1L);
    }

    @Test
    public void testCreateSoldItem() {
        when(soldMapper.toEntity(any(SoldDTO.class))).thenReturn(sold1);
        when(soldService.saveSold(any(Sold.class))).thenReturn(sold1);
        when(soldMapper.toDTO(any(Sold.class))).thenReturn(soldDTO1);

        ResponseEntity<SoldDTO> response = soldController.createSoldItem(soldDTO1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(soldDTO1);
        verify(soldService).saveSold(any(Sold.class));
    }

    @Test
    public void testUpdateSoldItem() {
        when(soldService.getSoldById(1L)).thenReturn(Optional.of(sold1));
        when(soldMapper.toEntity(any(SoldDTO.class))).thenReturn(sold1);
        when(soldService.saveSold(any(Sold.class))).thenReturn(sold1);
        when(soldMapper.toDTO(any(Sold.class))).thenReturn(soldDTO1);

        ResponseEntity<SoldDTO> response = soldController.updateSoldItem(1L, soldDTO1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(soldDTO1);
        verify(soldService).saveSold(any(Sold.class));
    }

    @Test
    public void testDeleteSoldItem() {
        when(soldService.getSoldById(1L)).thenReturn(Optional.of(sold1));

        ResponseEntity<Object> response = soldController.deleteSoldItem(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(soldService).deleteSold(1L);
    }
}