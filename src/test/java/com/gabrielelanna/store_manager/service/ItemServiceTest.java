package com.gabrielelanna.store_manager.service;

import com.gabrielelanna.store_manager.model.entity.Item;
import com.gabrielelanna.store_manager.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    private Item item1;
    private Item item2;

    @BeforeEach
    public void setUp() {

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
    }

    @Test
    public void testGetAllItems() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> items = itemService.getAllItems();

        assertThat(items).hasSize(2);
        assertThat(items).containsExactlyInAnyOrder(item1, item2);
        verify(itemRepository).findAll();
    }

    @Test
    public void testGetItemById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));

        Optional<Item> item = itemService.getItemById(1L);

        assertThat(item).isPresent();
        assertThat(item.get()).isEqualTo(item1);
        verify(itemRepository).findById(1L);
    }

    @Test
    public void testCreateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item1);

        Item createdItem = itemService.saveItem(item1);

        assertThat(createdItem).isEqualTo(item1);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    public void testUpdateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item1);

        Item updatedItem = itemService.saveItem(item1);

        assertThat(updatedItem).isEqualTo(item1);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    public void testDeleteItem() {
        itemService.deleteItem(1L);

        verify(itemRepository).deleteById(1L);
    }

    @Test
    public void testFindItemsByDescription() {
        when(itemRepository.findByDescriptionLike("nice")).thenReturn(Arrays.asList(item1, item2));

        List<Item> items = itemService.findItemsByDescription("nice");

        assertThat(items).hasSize(2);
        assertThat(items).containsExactlyInAnyOrder(item1, item2);
        verify(itemRepository).findByDescriptionLike("nice");
    }
}