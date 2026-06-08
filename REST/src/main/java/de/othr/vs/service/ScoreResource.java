package de.othr.vs.service;

import de.othr.vs.entity.ScoreResponse;
import de.othr.vs.store.QuizStore;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/score")
@Produces(MediaType.APPLICATION_JSON)
public class ScoreResource {
    private final QuizStore store = QuizStore.getInstance();

    @GET
    public ScoreResponse getScore() {
        return store.getScore();
    }
}
