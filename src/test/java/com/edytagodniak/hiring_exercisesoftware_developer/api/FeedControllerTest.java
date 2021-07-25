package com.edytagodniak.hiring_exercisesoftware_developer.api;

import com.edytagodniak.hiring_exercisesoftware_developer.analysis.AnalysisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FeedController.class)
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnalysisService analysisService;

    @Test
    public void analyseThrowsConstraintViolationExceptionIfNotAtLeastTwoUrls() throws Exception {
        //given
        FeedUrl properUrl = new FeedUrl("https://news.egodniak.com/");

        //when
        this.mockMvc.perform(
                post("/analyse/new")
                        .content(asJsonString(List.of(properUrl)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void analyseThrowsConstraintViolationExceptionIfWrongUrl() throws Exception {
        //given
        FeedUrl properUrl = new FeedUrl("https://news.egodniak.com/");
        FeedUrl wrongUrl = new FeedUrl("sss");

        //when
        this.mockMvc.perform(
                post("/analyse/new")
                        .content(asJsonString(Lists.list(properUrl, wrongUrl)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void analyseShouldReturnOkIfBodyIsValid() throws Exception {
        //given
        FeedUrl properUrl = new FeedUrl("https://news.egodniak.com/");
        FeedUrl properUrl2 = new FeedUrl("https://news.egodniak2.com/");
        Mockito.when(analysisService.analyseFeeds(List.of(properUrl, properUrl2))).thenReturn(UUID.randomUUID());
        List<FeedUrl> list = List.of(properUrl, properUrl2);

        this.mockMvc.perform(
                post("/analyse/new")
                        .content(new ObjectMapper().writeValueAsString(list))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}