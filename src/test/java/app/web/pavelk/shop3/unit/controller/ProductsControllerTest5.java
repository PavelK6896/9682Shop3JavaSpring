package app.web.pavelk.shop3.unit.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductsControllerTest5 {//тест контролера

    @Autowired
    private MockMvc mvc;//все окружение

    @Test
    public void tryToStart() throws Exception {
        mvc.perform(get("/"))//обращение к корню
                .andDo(print())//печать
                .andExpect(status().isOk())
                .andExpect(content()
                        //гдето на странице есть надпись
                        .string(org.hamcrest.core.StringContains.containsString("Shop")));
    }


    @Test
    public void productsToStart() throws Exception {
        mvc.perform(get("/products"))
                .andDo(print())//печать
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(org.hamcrest.core.StringContains.containsString("July")));
    }
}
