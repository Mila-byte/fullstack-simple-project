package controller;

import model.Question;
import repository.dao.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/api")
@RestController
public class QuestionController implements ErrorController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/questions/random")
    @CrossOrigin()
    public ResponseEntity<Question> getRandomQuestion() throws SQLException {
        Question question = questionRepository.getRndQuestion();
        return ResponseEntity.ok(question);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while processing the request: " + ex.getMessage());
    }
}

