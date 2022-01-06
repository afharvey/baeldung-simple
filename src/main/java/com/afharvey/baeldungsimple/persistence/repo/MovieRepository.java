package com.afharvey.baeldungsimple.persistence.repo;

import com.afharvey.baeldungsimple.persistence.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findByTitle(String title);
}
