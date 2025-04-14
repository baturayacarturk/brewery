package com.brewery.web.controller;

import com.brewery.services.BeerService;
import com.brewery.web.controller.util.ConstrainedFields;
import com.brewery.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.brewery.com", uriPort = 80)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.brewery.web.mappers")
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;
    
    private ConstrainedFields fields;
    
    @BeforeEach
    void setUp() {
        fields = new ConstrainedFields(BeerDto.class);
    }

    @Test
    void getBeerById() throws Exception {
        given(beerService.getBeerById(any())).willReturn(BeerDto.builder().build());

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of the beer to retrieve")
                        ),
                        responseFields(
                            fieldWithPath("id").description("ID of the beer").optional(),
                            fieldWithPath("beerName").description("Name of the beer").optional(),
                            fieldWithPath("beerStyle").description("Style of the beer").optional(),
                            fieldWithPath("upc").description("Universal Product Code").optional(),
                            fieldWithPath("createDate").description("Date when beer was created").optional(),
                            fieldWithPath("lastUpdatedDate").description("Date when beer was last updated").optional()
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        
        BeerDto savedDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName(beerDto.getBeerName())
                .beerStyle(beerDto.getBeerStyle())
                .upc(beerDto.getUpc())
                .build();
                
        given(beerService.save(any(BeerDto.class))).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-post",
                        requestFields(
                            fields.withPath("id").description("ID of the beer - will be ignored").optional(),
                            fields.withPath("beerName").description("Name of the beer"),
                            fields.withPath("beerStyle").description("Style of the beer"),
                            fields.withPath("upc").description("Universal Product Code"),
                            fields.withPath("createDate").description("Date when beer was created - will be ignored").optional(),
                            fields.withPath("lastUpdatedDate").description("Date when beer was last updated - will be ignored").optional()
                        )
                ));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent())
                .andDo(document("v1/beer-update",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of the beer to update")
                        ),
                        requestFields(
                            fields.withPath("id").description("ID of the beer - will be ignored").optional(),
                            fields.withPath("beerName").description("Name of the beer"),
                            fields.withPath("beerStyle").description("Style of the beer"),
                            fields.withPath("upc").description("Universal Product Code"),
                            fields.withPath("createDate").description("Date when beer was created - will be ignored").optional(),
                            fields.withPath("lastUpdatedDate").description("Date when beer was last updated - will be ignored").optional()
                        )
                ));
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("Nice Ale")
                .beerStyle("ALE")
                .upc(123123123123L)
                .build();
    }
}