package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.BasePersistenceTest;

@AutoConfigureMockMvc
class IndexControllerTest extends BasePersistenceTest {

    @Autowired
    private MockMvc mockMvc;

    //    @Test
    //    void jdbcDemo() throws Exception {
    //        // given
    //
    //        // when
    //        var result = mockMvc.perform(get("/jdbcDemo")).andReturn().getResponse();
    //
    //        // then
    //        assertThat(result.getStatus()).isEqualTo(SC_OK);
    //        assertThat(result.getContentAsString()).isEqualTo("1");
    //    }
}
