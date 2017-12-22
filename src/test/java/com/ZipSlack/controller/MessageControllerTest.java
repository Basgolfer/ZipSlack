package com.ZipSlack.controller;

import com.ZipSlack.model.Message;
import com.ZipSlack.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = MessageController.class, secure = false)
public class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MessageService messageService;

    Message mockMessage = new Message("hey",1L);
    Message mockMessage1 = new Message("hey",1L);
    String example = "{\"messageID\":1,\"message\":\"hey\",\"timestamp\":\"Tue Dec 19 16:15:31 EST 2017\",\"userId\":null}";
    String example1 = "{\"messageID\":1,\"message\":\"hello\",\"timestamp\":\"Tue Dec 19 16:15:31 EST 2017\",\"userId\":null}";


    @Test
    public void create() throws Exception {
        Mockito.when(messageService.addMessage(Mockito.any(Message.class))).thenReturn(mockMessage);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/chat/messages")
                .accept(MediaType.APPLICATION_JSON)
                .content(example)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getMessage() throws Exception {
        mockMessage.setTimestamp("Tue Dec 19 16:15:31 EST 2017");
        Mockito.when(messageService.get(Mockito.anyLong())).thenReturn(mockMessage);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/chat/messages/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string(example))
                .andExpect(status().isOk());
    }

    @Test
    public void delete() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/chat/messages/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1"))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {

        Mockito.when(messageService.get(Mockito.anyLong())).thenReturn(mockMessage);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/chat/messages/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(example))
                .andExpect(status().isOk());
        //test to see if content has been updated? or just check status from controller?
    }

}