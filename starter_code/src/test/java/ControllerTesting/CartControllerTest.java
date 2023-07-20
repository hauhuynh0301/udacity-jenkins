package ControllerTesting;

import com.example.demo.UtilTesting;
import com.example.demo.controllers.CartController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();
        UtilTesting.injectObjects(cartController, "userRepository", userRepo);
        UtilTesting.injectObjects(cartController, "cartRepository", cartRepo);
        UtilTesting.injectObjects(cartController, "itemRepository", itemRepo);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("lala");
        user.setPassword("lalaPassword");
        user.setCart(cart);
        when(userRepo.findByUsername("test")).thenReturn(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        BigDecimal price = BigDecimal.valueOf(2.99);
        item.setPrice(price);
        item.setDescription("A widget that is round");
        when(itemRepo.findById(1L)).thenReturn(java.util.Optional.of(item));

    }

    @Test
    public void add_to_cart_invalid_user() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setItemId(1L);
        r.setQuantity(1);
        r.setUsername("afaaf");
        ResponseEntity<Cart> response = cartController.addTocart(r);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void add_to_cart_invalid_item() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setItemId(2L);
        r.setQuantity(1);
        r.setUsername("lala");
        ResponseEntity<Cart> response = cartController.addTocart(r);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    public void remove_from_cart_invalid_user() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setItemId(1L);
        r.setQuantity(1);
        r.setUsername("123da");
        ResponseEntity<Cart> response = cartController.removeFromcart(r);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_invalid_item() {
        ModifyCartRequest r = new ModifyCartRequest();
        r.setItemId(2L);
        r.setQuantity(1);
        r.setUsername("test1231");
        ResponseEntity<Cart> response = cartController.removeFromcart(r);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

}
