package de.othr.vs.service;

import de.othr.vs.entity.SubmitAnswerRequest;
import de.othr.vs.entity.SubmitAnswerResponse;
import de.othr.vs.store.QuizStore;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/answers")
@Produces(MediaType.APPLICATION_JSON)
public class AnswersResource {
    private final QuizStore store = QuizStore.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public SubmitAnswerResponse submitAnswer(SubmitAnswerRequest request) {
        if (request == null || request.getSelectedAnswer() == null || request.getSelectedAnswer().isBlank()) {
            throw new WebApplicationException("selectedAnswer is required.", Response.Status.BAD_REQUEST);
        }

        SubmitAnswerResponse response = store.submitAnswer(request.getQuestionId(), request.getSelectedAnswer());
        if (response == null) {
            throw new NotFoundException("Question with id " + request.getQuestionId() + " not found.");
        }

        return response;
    }

    @DELETE
    public Response resetAnswers() {
        store.resetAnswers();
        return Response.noContent().build();
    }
}
