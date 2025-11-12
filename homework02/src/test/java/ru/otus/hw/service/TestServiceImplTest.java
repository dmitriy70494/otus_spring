package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TestServiceImplTest {
    private final IOService ioService = mock(IOService.class);
    private final QuestionDao questionDao = mock(QuestionDao.class);
    private final TestService testService = new TestServiceImpl(ioService, questionDao);

    @Test
    public void testCorrectAnswers() {
        var student = new Student("Ivanov", "Ivan");
        var questions = generateQuestions();
        when(questionDao.findAll()).thenReturn(questions);
        var checkIoService = ioService.readIntForRangeWithPrompt(
                eq(0),
                eq(1),
                contains("Question: "),
                contains("You type incorrect answer. Type number from 0 to"));
        when(checkIoService)
                .thenReturn(0)
                .thenReturn(1);
        TestResult result = testService.executeTestFor(student);
        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(questions.size(), result.getRightAnswersCount());
    }

    @Test
    public void testEmptyQuestions() {
        List<Question> questions = List.of();
        var student = new Student("Ivanov", "Ivan");
        when(questionDao.findAll()).thenReturn(questions);
        var result = testService.executeTestFor(student);
        assertNotNull(result);
        assertEquals(0, result.getRightAnswersCount());
        assertEquals(questions, result.getAnsweredQuestions());
    }

    private List<Question> generateQuestions() {
        var answerOne = new Answer("answer1", true);
        var answerTwo = new Answer("answer2", false);
        var answersOne = List.of(answerOne, answerTwo);
        var questionOne = new Question("question1", answersOne);
        var answerThree = new Answer("answer1", false);
        var answerFour = new Answer("answer2", true);
        var answersTwo = List.of(answerThree, answerFour);
        var questionTwo = new Question("question2", answersTwo);
        return List.of(questionOne, questionTwo);
    }
}