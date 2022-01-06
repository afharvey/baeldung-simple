package com.afharvey.baeldungsimple.web;
import com.afharvey.baeldungsimple.web.excpetion.MovieNotFoundException;
import com.afharvey.baeldungsimple.web.excpetion.MovieIdMismatchException;
import com.afharvey.baeldungsimple.persistence.model.Movie;
import com.afharvey.baeldungsimple.persistence.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public Iterable findAll() {
        return movieRepository.findAll();
    }

    @GetMapping("/title/{movieTitle}")
    public List findByTitle(@PathVariable String movieTitle) {
        return movieRepository.findByTitle(movieTitle);
    }

    @GetMapping("/{id}")
    public Movie findOne(@PathVariable Long id) {
        return movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
        movieRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@RequestBody Movie movie, @PathVariable Long id) {
        if (movie.getId() != id) {
            throw new MovieIdMismatchException();
        }
        movieRepository.findById(id)
                .orElseThrow(MovieNotFoundException::new);
        return movieRepository.save(movie);
    }
}
