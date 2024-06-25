package com.gabrielelanna.store_manager.service;

import com.gabrielelanna.store_manager.model.entity.Item;
import com.gabrielelanna.store_manager.model.entity.Sold;
import com.gabrielelanna.store_manager.repository.SoldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SoldServiceTest {

    @InjectMocks
    private SoldService soldService;

    @Mock
    private SoldRepository soldRepository;

    private Sold sold1;
    private Sold sold2;

    @BeforeEach
    public void setUp() {

        Item item = Item.builder()
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
    }

    @Test
    public void testGetAllSoldItems() {
        when(soldRepository.findAll()).thenReturn(Arrays.asList(sold1, sold2));

        List<Sold> soldItems = soldService.getAllSoldItems();

        assertThat(soldItems).hasSize(2);
        assertThat(soldItems).containsExactlyInAnyOrder(sold1, sold2);
        verify(soldRepository).findAll();
    }

    @Test
    public void testGetSoldItemById() {
        when(soldRepository.findById(1L)).thenReturn(Optional.of(sold1));

        Optional<Sold> soldItem = soldService.getSoldById(1L);

        assertThat(soldItem).isPresent();
        assertThat(soldItem.get()).isEqualTo(sold1);
        verify(soldRepository).findById(1L);
    }

    @Test
    public void testCreateSoldItem() {
        when(soldRepository.save(any(Sold.class))).thenReturn(sold1);

        Sold createdSoldItem = soldService.saveSold(sold1);

        assertThat(createdSoldItem).isEqualTo(sold1);
        verify(soldRepository).save(any(Sold.class));
    }

    @Test
    public void testUpdateSoldItem() {
        when(soldRepository.save(any(Sold.class))).thenReturn(sold1);

        Sold updatedSoldItem = soldService.saveSold(sold1);

        assertThat(updatedSoldItem).isEqualTo(sold1);
        verify(soldRepository).save(any(Sold.class));
    }

    @Test
    public void testDeleteSoldItem() {
        soldService.deleteSold(1L);

        verify(soldRepository).deleteById(1L);
    }
}