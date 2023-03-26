package repository.dao;

import model.Question;

import java.sql.SQLException;
import java.util.List;

public interface QuestionRepository {
    Question get(int id);

    void save(Question question);

    void update(Question question);

    void delete(int id);

    List<Question> getByTopic(String topic);

    List<Question> getAllQuestions() throws SQLException;

    Question getRndQuestion() throws SQLException;
}
