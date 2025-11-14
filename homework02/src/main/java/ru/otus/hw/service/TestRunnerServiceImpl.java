package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private static final String READ_EXCEPTION_MESSAGE = "There was a problem reading the question";

    private static final String COMMON_EXCEPTION_MESSAGE =
            "There was some problem, send message admin by e-mail d89086362742@yandex.ru";

    private final IOService ioService;

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    @Override
    public void run() {
        try {
            var student = studentService.determineCurrentStudent();
            var testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (QuestionReadException qre) {
            ioService.printFormattedLine(READ_EXCEPTION_MESSAGE);
        } catch (Exception e) {
            ioService.printFormattedLine(COMMON_EXCEPTION_MESSAGE);
        }
    }
}
