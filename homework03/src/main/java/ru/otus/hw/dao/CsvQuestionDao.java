package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        var fileName = Objects.requireNonNull(fileNameProvider.getTestFileName());
        try (var inputStream = Objects.requireNonNull(getClass().getResourceAsStream(fileName));
             var inputStreamReader = new InputStreamReader(inputStream);
             var bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            return parseQuestionsFromCsv(bufferedReader);
        } catch (IOException e) {
            throw new QuestionReadException("Fail to parse question", e);
        }
    }

    private List<Question> parseQuestionsFromCsv(Reader reader) {
        var questionDtos = new CsvToBeanBuilder<QuestionDto>(reader)
                .withType(QuestionDto.class)
                .withSeparator(';')
                .withFilter(line -> !line[0].startsWith("#"))
                .build()
                .parse();
        return questionDtos.stream().map(QuestionDto::toDomainObject).toList();
    }
}
