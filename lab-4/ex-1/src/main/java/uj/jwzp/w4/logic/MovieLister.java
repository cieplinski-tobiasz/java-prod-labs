package uj.jwzp.w4.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uj.jwzp.w4.model.Movie;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MovieLister {
    private final MovieFinder finder;

    public List<Movie> moviesDirectedBy(String name) {
        List<Movie> allMovies = finder.findAll();
        return allMovies.stream()
                .filter(m -> m.getDirector().equals(name))
                .collect(Collectors.toList());
    }
}
