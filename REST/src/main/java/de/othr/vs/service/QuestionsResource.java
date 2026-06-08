package de.othr.vs.service;

import de.othr.vs.entity.QuestionView;
import de.othr.vs.store.QuizStore;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/questions")
@Produces(MediaType.APPLICATION_JSON)
public class QuestionsResource {
    private final QuizStore store = QuizStore.getInstance();

    @GET
    public List<QuestionView> getQuestions() {
        return store.getAllQuestions();
    }

    @GET
    @Path("/{id}")
    public QuestionView getQuestionById(@PathParam("id") int id) {
        QuestionView question = store.getQuestionById(id);
        if (question == null) {
            throw new NotFoundException("Question with id " + id + " not found.");
        }
        return question;
    }

    @GET
    @Path("/random")
    public QuestionView getRandomQuestion() {
        QuestionView question = store.getRandomQuestion();
        if (question == null) {
            throw new NotFoundException("No questions available.");
        }
        return question;
    }
}
