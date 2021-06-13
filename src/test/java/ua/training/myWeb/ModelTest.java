package ua.training.myWeb;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.training.myWeb.model.dao.*;
import ua.training.myWeb.model.entity.Edition;
import ua.training.myWeb.model.entity.Subscription;
import ua.training.myWeb.model.entity.Theme;
import ua.training.myWeb.model.entity.User;
import ua.training.myWeb.model.entity.enums.EditionStatus;
import ua.training.myWeb.model.entity.enums.Role;
import ua.training.myWeb.model.entity.enums.SubscriptionStatus;
import ua.training.myWeb.model.entity.enums.UserStatus;

import java.sql.Date;
import java.util.List;

public class ModelTest {

    @Test
    public void testEditionDao() {
        Edition edition = new Edition();
        edition.setName("Test edition");
        edition.setPrice(1.4);
        edition.setStatus(EditionStatus.ACTIVE);
        edition.setTheme(new Theme());
        edition.getTheme().setId(1l);
        try (EditionDao editionDao = DaoFactory.getInstance().createEditionDao()) {
            editionDao.create(edition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(edition.getId());
        List<Edition> editionList = null;
        Edition edition1 = null;
        long count = 0;
        try (EditionDao editionDao = DaoFactory.getInstance().createEditionDao()) {
            edition1 = editionDao.findById(edition.getId());
            editionList = editionDao.findAll();
            count = editionDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkEdition(edition1);
        Assertions.assertNotNull(editionList);
        for (Edition ed : editionList) {
            checkEdition(ed);
        }
        Assertions.assertEquals(editionList.size(), count);
        Assertions.assertTrue(editionList.contains(edition1));
        try (EditionDao editionDao = DaoFactory.getInstance().createEditionDao()) {
            editionDao.delete(edition1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        long countWithoutOne = 0;
        try (EditionDao editionDao = DaoFactory.getInstance().createEditionDao()) {
            Assertions.assertNull(editionDao.findById(edition.getId()));
            editionList = editionDao.findAll();
            countWithoutOne = editionDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, count - countWithoutOne);
        Assertions.assertFalse(editionList.contains(edition1));
    }

    private void checkEdition(Edition edition) {
        Assertions.assertNotNull(edition);
        Assertions.assertNotNull(edition.getId());
        Assertions.assertNotNull(edition.getStatus());
        Assertions.assertNotNull(edition.getTheme());
        Assertions.assertNotNull(edition.getName());
        Assertions.assertNotNull(edition.getPrice());
    }

    @Test
    public void testUserDao() {
        User user = new User();
        user.setLogin("LoginTest");
        user.setAccount(1.4);
        user.setStatus(UserStatus.ACTIVE);
        user.setRole(Role.USER);
        user.setPassword("passwordTest");
        try (UserDao userDao = DaoFactory.getInstance().createUserDao()) {
            userDao.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(user.getId());
        List<User> userList = null;
        User user1 = null;
        long count = 0;
        try (UserDao userDao = DaoFactory.getInstance().createUserDao()) {
            user1 = userDao.findById(user.getId());
            userList = userDao.findAll();
            count = userDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkUser(user1);
        Assertions.assertNotNull(userList);
        for (User u : userList) {
            checkUser(u);
        }
        Assertions.assertEquals(userList.size(), count);
        Assertions.assertTrue(userList.contains(user1));
        try (UserDao userDao = DaoFactory.getInstance().createUserDao()) {
            userDao.delete(user1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        long countWithoutOne = 0;
        try (UserDao userDao = DaoFactory.getInstance().createUserDao()) {
            Assertions.assertNull(userDao.findById(user.getId()));
            userList = userDao.findAll();
            countWithoutOne = userDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, count - countWithoutOne);
        Assertions.assertFalse(userList.contains(user1));
    }

    private void checkUser(User user) {
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getStatus());
        Assertions.assertNotNull(user.getAccount());
        Assertions.assertNotNull(user.getLogin());
        Assertions.assertNotNull(user.getPassword());
        Assertions.assertNotNull(user.getRole());
    }

    @Test
    public void testThemeDao() {
        Theme theme = new Theme();
        theme.setName("testTheme");
        try (ThemeDao themeDao = DaoFactory.getInstance().createThemeDao()) {
            themeDao.create(theme);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(theme.getId());
        List<Theme> themeList = null;
        Theme theme1 = null;
        long count = 0;
        try (ThemeDao themeDao = DaoFactory.getInstance().createThemeDao()) {
            theme1 = themeDao.findById(theme.getId());
            themeList = themeDao.findAll();
            count = themeDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkTheme(theme1);
        Assertions.assertNotNull(themeList);
        for (Theme t : themeList) {
            checkTheme(t);
        }
        Assertions.assertEquals(themeList.size(), count);
        Assertions.assertTrue(themeList.contains(theme1));
        try (ThemeDao themeDao = DaoFactory.getInstance().createThemeDao()) {
            themeDao.delete(theme1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        long countWithoutOne = 0;
        try (ThemeDao themeDao = DaoFactory.getInstance().createThemeDao()) {
            Assertions.assertNull(themeDao.findById(theme.getId()));
            themeList = themeDao.findAll();
            countWithoutOne = themeDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, count - countWithoutOne);
        Assertions.assertFalse(themeList.contains(theme1));
    }

    private void checkTheme(Theme theme) {
        Assertions.assertNotNull(theme);
        Assertions.assertNotNull(theme.getId());
        Assertions.assertNotNull(theme.getName());
    }

    @Test
    public void testSubscription() {
        Subscription subscription = new Subscription();
        subscription.setEdition(new Edition());
        subscription.getEdition().setId(1l);
        subscription.setUser(new User());
        subscription.getUser().setId(1l);
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setNextPayDay(Date.valueOf("2020-12-12"));
        try (SubscriptionDao subscriptionDao = DaoFactory.getInstance().createSubscriptionDao()) {
            subscriptionDao.create(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertNotNull(subscription.getId());
        List<Subscription> subscriptionList = null;
        Subscription subscription1 = null;
        long count = 0;
        try (SubscriptionDao subscriptionDao = DaoFactory.getInstance().createSubscriptionDao()) {
            subscription1 = subscriptionDao.findById(subscription.getId());
            subscriptionList = subscriptionDao.findAll();
            count = subscriptionDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        checkSubscription(subscription1);
        Assertions.assertNotNull(subscriptionList);
        for (Subscription t : subscriptionList) {
            checkSubscription(t);
        }
        Assertions.assertEquals(subscriptionList.size(), count);
        Assertions.assertTrue(subscriptionList.contains(subscription1));
        try (SubscriptionDao subscriptionDao = DaoFactory.getInstance().createSubscriptionDao()) {
            subscriptionDao.delete(subscription1.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        long countWithoutOne = 0;
        try (SubscriptionDao subscriptionDao = DaoFactory.getInstance().createSubscriptionDao()) {
            Assertions.assertNull(subscriptionDao.findById(subscription.getId()));
            subscriptionList = subscriptionDao.findAll();
            countWithoutOne = subscriptionDao.count();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, count - countWithoutOne);
        Assertions.assertFalse(subscriptionList.contains(subscription1));
    }

    private void checkSubscription(Subscription subscription) {
        Assertions.assertNotNull(subscription);
        Assertions.assertNotNull(subscription.getId());
        Assertions.assertNotNull(subscription.getEdition());
        Assertions.assertNotNull(subscription.getStatus());
        Assertions.assertNotNull(subscription.getNextPayDay());
        Assertions.assertNotNull(subscription.getUser());
    }
}
