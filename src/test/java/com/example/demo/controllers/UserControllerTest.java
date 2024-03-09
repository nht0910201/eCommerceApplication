package com.example.demo.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserControllerTest extends TestCase {
    public static final String USER_NAME = "test";

    private UserController userController;
    private UserRepository userRepo = mock(UserRepository.class);
    private CartRepository cartRepo = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo);
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    public void testFindById() {
        when(encoder.encode("somepassword")).thenReturn("encodePassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername(USER_NAME);
        r.setPassword("somepassword");
        r.setConfirmPassword("somepassword");
        final ResponseEntity<User> response = userController.createUser(r);
        User user = response.getBody();
        when(userRepo.findById(0L)).thenReturn(java.util.Optional.ofNullable(user));

        final ResponseEntity<User> userResponseEntity = userController.findById(0L);

        User u = userResponseEntity.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals(USER_NAME, u.getUsername());
        assertEquals("encodePassword", u.getPassword());
    }

    public void testFindByUserName() {
        when(encoder.encode("somepassword")).thenReturn("encodePassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername(USER_NAME);
        r.setPassword("somepassword");
        r.setConfirmPassword("somepassword");
        final ResponseEntity<User> response = userController.createUser(r);
        User user = response.getBody();
        when(userRepo.findByUsername(USER_NAME)).thenReturn(user);

        final ResponseEntity<User> userResponseEntity = userController.findByUserName(USER_NAME);

        User u = userResponseEntity.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals(USER_NAME, u.getUsername());
        assertEquals("encodePassword", u.getPassword());
      }

    public void testCreateUser() {
        when(encoder.encode("somepassword")).thenReturn("encodePassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername(USER_NAME);
        r.setPassword("somepassword");
        r.setConfirmPassword("somepassword");
        final ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals(USER_NAME, u.getUsername());
        assertEquals("encodePassword", u.getPassword());
      }
}