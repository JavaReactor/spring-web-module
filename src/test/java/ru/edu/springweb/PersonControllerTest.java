package ru.edu.springweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.view.InternalResourceView;
import ru.edu.springweb.config.WebConfig;
import ru.edu.springweb.controller.PersonController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig(WebConfig.class)
public class PersonControllerTest  {

    private MockMvc mockMvc;
    private PersonController personController;

    @BeforeEach
    void setUp() {
        personController = new PersonController();
        mockMvc = MockMvcBuilders
                .standaloneSetup(personController)
                .setViewResolvers((viewName, locale) -> {
                    InternalResourceView view = new InternalResourceView();
                    view.setUrl("/WEB-INF/views/" + viewName + ".html");
                    return view;
                })
                .build();
    }

    @Test
    void testAuthorPageWithMockMvc() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("author"))
                .andExpect(model().attribute("author", "Виталий Горяев"));
    }

    @Test
    void testHobbyPageWithMockMvc() throws Exception {
        mockMvc.perform(get("/hobby"))
                .andExpect(status().isOk())
                .andExpect(view().name("hobby"))
                .andExpect(model().attribute("hobby", "путешествия, плавание, фотография"));
    }

    @Test
    void testAuthorPageDirectMethodCall() {
        Model model = new BindingAwareModelMap();
        String viewName = personController.authorPage(model);

        assertEquals(viewName, "author");
        assertEquals(model.asMap().get("author"), "Виталий Горяев");
    }

    @Test
    void testHobbyPageDirectMethodCall() {
        Model model = new BindingAwareModelMap();
        String viewName = personController.hobbyPage(model);

        assertEquals(viewName, "hobby");
        assertEquals(model.asMap().get("hobby"), "путешествия, плавание, фотография");
    }
}
