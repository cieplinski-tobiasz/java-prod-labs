package uj.jwzp.w4.launchers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import uj.jwzp.w4.logic.MovieLister;
import uj.jwzp.w4.model.Movie;

@Slf4j
public class SpringMain {
    public static void main(String[] args) {
        ApplicationContext ctx = getContextWithPropertySource(args);
        MovieLister lister = (MovieLister) ctx.getBean("movieLister");

        lister.moviesDirectedBy("Hoffman").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }

    private static ApplicationContext getContextWithPropertySource(String[] args) {
        SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource(args);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.scan("uj.jwzp.w4.logic");
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
        ctx.refresh();

        return ctx;
    }
}
