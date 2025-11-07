package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {


    private static final String ERROR_MESSAGE = "You type incorrect answer. Type number from 0 to %s";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var max = question.answers().size() - 1;
            var prompt = getPromptQuestion(question);
            var errorMessage = String.format(ERROR_MESSAGE, max);
            var answer = ioService.readIntForRangeWithPrompt(0, max, prompt, errorMessage);
            testResult.applyAnswer(question, question.answers().get(answer).isCorrect());
        }
        return testResult;
    }

    private String getPromptQuestion(Question question) {
        var result = new StringBuilder()
                .append("Question: ")
                .append(question.text())
                .append(System.lineSeparator());
        for (int i = 0; i < question.answers().size(); i++) {
            result.append("Answer ")
                    .append(i)
                    .append(": ")
                    .append(question.answers().get(i).text())
                    .append(System.lineSeparator());
        }
        return result.toString();
    }
}
