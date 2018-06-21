package com.fullstack.test.dao;

import com.fullstack.test.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findsExistingUser() {
        Assert.assertNotNull(userRepository.findByEmail("a@a.com"));
    }

    @Test
    public void returnsNullForNotExistingUser() {
        Assert.assertNull(userRepository.findByEmail("a@b.com"));
    }

    @Test
    public void deletesUser() {
        userRepository.delete(userRepository.findByEmail("a@a.com"));

        Assert.assertNull("A user is deleted ", userRepository.findByEmail("a@a.com"));
    }

    @Test
    public void createsUser() {
        User user = new User();
        user.setName("New");
        user.setEmail("d@d.com");
        user.setPassword(new BCryptPasswordEncoder().encode("pass"));
        user.setRole(UserRole.PUBLISHER);

        userRepository.save(user);

        Assert.assertEquals("A user is added ", 5, userRepository.findAll().size());

        User persistedUser = userRepository.findByEmail("d@d.com");
        Assert.assertEquals(persistedUser, user);
    }

    @Test
    public void updatesUser() {
        User user = userRepository.findByEmail("a@a.com");
        user.setName("UpdatedUser");

        userRepository.save(user);

        Assert.assertEquals("A user is not added ", 4, userRepository.findAll().size());
        Assert.assertEquals("A user is updated ", "UpdatedUser", userRepository.findByEmail("a@a.com").getName());
    }
}
