package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;
private User testUser;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
        testUser = new User(1001,"bob","password","USER");

    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void find_all_users(){
        List<User> users = sut.findAll();
        Assert.assertEquals(2,users.size());


    }
    @Test
    public void get_user_by_username(){
        User user = sut.findByUsername("bob");
        Assert.assertEquals(1001, testUser.getId());
    }

    @Test
    public void find_id_by_username(){
        int userId = sut.findIdByUsername("bob");
        Assert.assertEquals(userId,testUser.getId());
    }

    private void assertUsersMatch(User expected, User actual) {
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getAuthorities(),actual.getAuthorities());
    }

}
