package repository;

import exceptions.SqlUpdateException;
import model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.dao.QuestionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

    private Connection connection;
    private String findByTopic =
            """
                       select * from question where topic = ?
                    """;

    private String findById =
            """
                       select * from question where id = ?
                    """;

    private String deleteById =
            """
                       delete from question where id = ?
                    """;

    private String updateById =
            """
                       update question set text = ?, topic = ? where id = ?
                    """;

    private String saveByParameters =
            """
                       insert into question (text, topic) values (?, ?)
                    """;
    private String getAllQuestions =
            """
                      select * from question
                    """;

    private String getRndQuestion =
            """
                    select * from question order by random() limit 1
                    """;

    public QuestionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Question get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findById);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            return Question.builder()
                    .id(resultSet.getInt("id"))
                    .text(resultSet.getString("text"))
                    .topic(resultSet.getString("topic"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getByTopic(String topic) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(findByTopic);
            preparedStatement.setString(1, topic);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            List<Question> questions = new ArrayList<>();
            while (resultSet.next()) {
                questions.add(Question.builder()
                        .id(resultSet.getInt("id"))
                        .text(resultSet.getString("text"))
                        .topic(resultSet.getString("topic"))
                        .build());
            }
            return questions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getAllQuestions() throws SQLException {
        List<Question> allQuestions = new ArrayList<>();
        PreparedStatement preparedStatement = this.connection.prepareStatement(getAllQuestions);
        ResultSet question = preparedStatement.executeQuery();
        while (question.next()) {
            allQuestions.add(Question.builder()
                    .id(question.getInt("id"))
                    .text(question.getString("text"))
                    .topic(question.getString("topic"))
                    .build());
        }
        return allQuestions;
    }

    @Override
    public Question getRndQuestion() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(getRndQuestion);
        ResultSet question = preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        return Question.builder()
                .id(question.getInt("id"))
                .text(question.getString("text"))
                .topic(question.getString("topic"))
                .build();
    }

    @Override
    public void save(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(saveByParameters);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setString(2, question.getTopic());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(updateById);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setString(2, question.getTopic());
            preparedStatement.setInt(3, question.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new SqlUpdateException(e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteById);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
