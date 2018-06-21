package com.fullstack.test.dao;

import com.fullstack.test.domain.App;
import com.fullstack.test.domain.AppType;
import com.fullstack.test.domain.ContentType;
import com.fullstack.test.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class AppRepositoryTest {
    @Autowired
    private AppRepository appRepository;

    @Test
    public void findsAllApps() {
        Assert.assertEquals("Finds expected amount of apps ", 3, appRepository.findAllByUserId(3L).size());
        Assert.assertEquals("Returns empty list of apps ", 0, appRepository.findAllByUserId(1L).size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deletesApp() {
        List<App> apps = appRepository.findAllByUserId(3L);

        appRepository.delete(apps.get(0));

        Assert.assertEquals("Deletes an app ", 2, appRepository.findAllByUserId(3L).size());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void createsApp() {
        List<App> apps = appRepository.findAllByUserId(3L);
        User user = apps.get(0).getUser();

        App app = new App();
        app.setName("App4");
        app.setType(AppType.ANDROID);
        Set<ContentType> contentTypes = new HashSet<>();
        contentTypes.add(ContentType.HTML);
        app.setContentTypes(contentTypes);
        app.setUser(user);

        appRepository.save(app);

        Assert.assertEquals("An app is added ", 4, appRepository.findAllByUserId(3L).size());
    }

    @Test
    public void updatesApp() {
        List<App> apps = appRepository.findAllByUserId(3L);
        App app = apps.get(0);
        app.setName("UpdatedApp");

        appRepository.save(app);

        apps = appRepository.findAllByUserId(3L);
        Assert.assertEquals("An app is not added ", 3, apps.size());
        Assert.assertEquals("An app is updated ", "UpdatedApp", apps.get(0).getName());
    }
}
