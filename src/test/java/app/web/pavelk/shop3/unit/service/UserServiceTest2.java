package app.web.pavelk.shop3.unit.service;


import app.web.pavelk.shop3.entity.user.User;
import app.web.pavelk.shop3.repo.UsersRepository;
import app.web.pavelk.shop3.service.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class UserServiceTest2 {
    @Autowired
    private UsersService usersService;//тестируеммый

    @MockBean
    private UsersRepository usersRepository;//подмена без логики

    @Test
    public void findOneUserTest() {
        User userFromDB = new User();//объект для теста
        userFromDB.setPhone("1");
        userFromDB.setEmail("john@mail.ru");

        Mockito.doReturn(userFromDB)//добавить логику вернуть юзера
                .when(usersRepository)//для
                .findByPhone("1", User.class);//метод

        User userJohn = usersService.findByPhone("1");
        Assertions.assertNotNull(userJohn);
        Mockito.verify(usersRepository, Mockito.times(1))
                .findByPhone(ArgumentMatchers.eq("1"), ArgumentMatchers.eq(User.class));

    }
}
