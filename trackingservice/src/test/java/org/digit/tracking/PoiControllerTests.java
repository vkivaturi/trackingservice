package org.digit.tracking;

import org.digit.tracking.controller.PoiController;
import org.digit.tracking.service.POIService;
import org.junit.Test;
import org.openapitools.model.POI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PoiController.class)
//@ContextConfiguration(classes = PoiController.class)
public class PoiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    POIService poiService;
    @Test
    public void getPoiByIdReturnPoiFromService() throws Exception {
        List<POI> poiList = new ArrayList<>();
        when(poiService.getPOIsById("uuid-1")).thenReturn(poiList);
        this.mockMvc.perform(get("//poi/uuid-1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, Mock")));

    }
}
