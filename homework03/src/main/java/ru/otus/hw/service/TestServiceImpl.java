package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            var answer = askQuestion(question);
            testResult.applyAnswer(question, question.answers().get(answer).isCorrect());
        }
        return testResult;
    }

    private int askQuestion(Question question) {
        printQuestion(question);
        var max = question.answers().size() - 1;
        var prompt = "TestService.question.message";
        var errorMessage = "TestService.error.message";
        return ioService.readIntForRangeWithPromptLocalized(0, max, prompt, errorMessage);
    }

    private void printQuestion(Question question) {
        var result = new StringBuilder()
                .append(question.text())
                .append(System.lineSeparator());
        for (int i = 0; i < question.answers().size(); i++) {
            result.append(i)
                    .append(": ")
                    .append(question.answers().get(i).text())
                    .append(System.lineSeparator());
        }
        ioService.printFormattedLine(result.toString());
    }
}
