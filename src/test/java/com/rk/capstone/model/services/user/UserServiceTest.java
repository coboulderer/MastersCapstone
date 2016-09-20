package com.rk.capstone.model.services.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rk.capstone.model.dao.UserDao;
import com.rk.capstone.model.domain.User;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

/**
 * Test Class for User Service
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    private UserDao userDao = mock(UserDao.class);
    private IUserService userService;
    private User user;

    @Before
    public void setup() {
        user = new User("firstname", "lastname", "email", "username", "abc123", null);
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void testSaveUser() {
        given(this.userDao.save(any(User.class))).willReturn(user);
        User savedUser = userService.saveUser(user);

        Assert.assertEquals(user, savedUser);
    }

    @Test
    public void testFindByUserName() {
        given(this.userDao.findByUserName(user.getUserName())).willReturn(user);
        User foundUser = userService.findByUserName(user.getUserName());

        Assert.assertEquals(user, foundUser);
    }

    @Test
    public void testFindByUserNameNotFound() {
        given(this.userDao.findByUserName(user.getUserName())).willReturn(null);
        User foundUser = userService.findByUserName(user.getUserName());

        Assert.assertNull(foundUser);
    }
}
