package com.afharvey.baeldungsimple;

import com.afharvey.baeldungsimple.persistence.model.Movie;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class SpringBootBootstrapLiveTest {

    private static final String API_ROOT = "http://localhost:8081/api/movies";

    @Test
    public void whenGetAllMovies_thenOK() {
        final Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetMoviesByTitle_thenOK() {
        final Movie Movie = createRandomMovie();
        createMovieAsUri(Movie);

        final Response response = RestAssured.get(API_ROOT + "/title/" + Movie.getTitle());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }

    @Test
    public void whenGetCreatedMovieById_thenOK() {
        final Movie Movie = createRandomMovie();
        final String location = createMovieAsUri(Movie);

        final Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(Movie.getTitle(), response.jsonPath()
                .get("title"));
    }

    @Test
    public void whenGetNotExistMovieById_thenNotFound() {
        final Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewMovie_thenCreated() {
        final Movie Movie = createRandomMovie();

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Movie)
                .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidMovie_thenError() {
        final Movie Movie = createRandomMovie();
        Movie.setDirector(null);

        final Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Movie)
                .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedMovie_thenUpdated() {
        final Movie Movie = createRandomMovie();
        final String location = createMovieAsUri(Movie);

        Movie.setId(Long.parseLong(location.split("api/movies/")[1]));
        Movie.setDirector("newDirector");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(Movie)
                .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newDirector", response.jsonPath()
                .get("director"));

    }

    @Test
    public void whenDeleteCreatedMovie_thenOk() {
        final Movie Movie = createRandomMovie();
        final String location = createMovieAsUri(Movie);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    private Movie createRandomMovie() {
        Movie movie = new Movie();
        movie.setTitle(randomAlphabetic(10));
        movie.setDirector(randomAlphabetic(15));
        return movie;
    }

    private String createMovieAsUri(Movie movie) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(movie)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("id");
    }
}
