package com.app.school.school_app.controller;

import com.app.school.school_app.domain.ClassEntity;
import com.app.school.school_app.service.ClassService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ClassController.class)
public class ClassControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Mock
    private ClassService classService;

    @Test
    public void getAllClasses_thenReturnJsonArray() throws Exception{
        ClassEntity entity = new ClassEntity();
        entity.setTitle("11-A");

        List<ClassEntity> allClasses = Arrays.asList(entity);

        given(classService.findAll()).willReturn(allClasses);

        mvc.perform(get("/class/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect((ResultMatcher) jsonPath("$[0].name", is(entity.getTitle())));
    }
}
