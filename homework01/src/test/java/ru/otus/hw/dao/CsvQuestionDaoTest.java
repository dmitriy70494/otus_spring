package ru.otus.hw.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CsvQuestionDaoTest {

    private final TestFileNameProvider testFileNameProvider = mock(AppProperties.class);

    private final CsvQuestionDao csvQuestionDao = new CsvQuestionDao(testFileNameProvider);

    @Test
    void findAllOk() {
        when(testFileNameProvider.getTestFileName()).thenReturn("questionsOk.csv");
        var expectedAnswers =
                List.of(
                        new Question(
                                "question",
                                List.of(
                                        new Answer("answer1", true),
                                        new Answer("answer2", false)
                                )
                        )
                );
        Assertions.assertEquals(expectedAnswers, csvQuestionDao.findAll());
    }

    @Test
    void findAllFileNullNpeError() {
        Assertions.assertThrows(NullPointerException.class, csvQuestionDao::findAll);
    }
}