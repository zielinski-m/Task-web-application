package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrelloMapperTestSuite {

    TrelloMapper trelloMapper;

    @BeforeEach
    void setUp() {trelloMapper = new TrelloMapper();}

    @Test
    void testMapToBoardAndList() {

        //Given
        List<TrelloListDto> trelloListDtos = Arrays.asList(
                new TrelloListDto("1", "List 1", false),
                new TrelloListDto("2", "List 2", true)
        );
        List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(
                new TrelloBoardDto("1", "Board 1", trelloListDtos),
                new TrelloBoardDto("2", "Board 2", trelloListDtos)
        );

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtos);

        //Then
        assertEquals(2, trelloBoards.size());
        assertEquals(2, trelloLists.size());
        assertEquals(trelloListDtos.size(), trelloLists.size());
        assertEquals("List 1", trelloLists.get(0).getName());
        assertEquals("Board 2", trelloBoards.get(1).getName());
    }

    @Test
    void testMapToBoardDtoAndListDto() {

        //Given
        List<TrelloList> trelloLists = Arrays.asList(
                new TrelloList("1", "List 1", false),
                new TrelloList("2", "List 2", true)
        );
        List<TrelloBoard> trelloBoards = Arrays.asList(
                new TrelloBoard("1", "Board 1", trelloLists),
                new TrelloBoard("2", "Board 2", trelloLists),
                new TrelloBoard("3", "Board 3", trelloLists)
        );

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        assertEquals(3, trelloBoardDtos.size());
        assertEquals(2, trelloListDtos.size());
        assertEquals("3", trelloBoardDtos.get(2).getId());
        assertTrue(trelloListDtos.get(1).isClosed());
    }

    @Test
    void mapToCardAndCardDto() {

        //Given
        TrelloCard trelloCard = new TrelloCard("Card 1", "Desc 1", "p1", "1");
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 2", "Desc 2", "p2", "2");

        //When
        TrelloCardDto mappedTrelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard mappedTrelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("Card 1", trelloCard.getName());
        assertEquals("Card 2", trelloCardDto.getName());
        assertEquals("p2", trelloCardDto.getPos());
        assertEquals("1", trelloCard.getListId());

    }
}
