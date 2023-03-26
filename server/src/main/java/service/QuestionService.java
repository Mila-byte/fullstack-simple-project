package service;

import model.Question;
import org.springframework.stereotype.Service;
import repository.dao.QuestionRepository;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final Map<String, List<Question>> questionsByTopic = new HashMap<>();

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getRndQuestionByTopic(String topic) {
        List<Question> questions = questionsByTopic.computeIfAbsent(topic, questionRepository::getByTopic);
        questionsByTopic.put(topic, questions);
        int randomNum = ThreadLocalRandom.current().nextInt(questions.size());
        return questions.get(randomNum);
    }


    public Question getRndQuestion() throws SQLException {
        List<Question> questions = questionRepository.getAllQuestions();
        int randomNum = ThreadLocalRandom.current().nextInt(0, questions.size());
        return questions.get(randomNum);
    }

    public void recordQuestion(Question question) {
        questionRepository.save(question);
    }

    public void deleteQuestion(int id) {
        questionRepository.delete(id);
    }
}
