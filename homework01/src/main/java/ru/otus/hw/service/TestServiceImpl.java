package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below");
        var questions = questionDao.findAll();
        printQuestions(questions);
    }

    private void printQuestions(List<Question> questions) {
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
