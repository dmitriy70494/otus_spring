package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final String READ_EXCEPTION_MESSAGE = "There was a problem reading the question";

    private static final String COMMON_EXCEPTION_MESSAGE =
            "There was some problem, send message admin by e-mail d89086362742@yandex.ru";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        try {
            var questions = questionDao.findAll();
            printQuestions(questions);
        } catch (QuestionReadException qre) {
            ioService.printFormattedLine(READ_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            ioService.printFormattedLine(COMMON_EXCEPTION_MESSAGE);
        }
    }

    private void printQuestions(List<Question> questions) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below");
        for (int q = 1; q <= questions.size(); q++) {
            ioService.printLine("");
            var question = questions.get(q - 1);
            ioService.printFormattedLine("Question %d: %s", q, question.text());
            var answers = question.answers();
            for (int a = 1; a <= answers.size(); a++) {
                ioService.printFormattedLine("Answer %d: %s", a, answers.get(a - 1).text());
            }
        }
    }
}
