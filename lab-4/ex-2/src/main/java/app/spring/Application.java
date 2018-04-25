package app.spring;

import app.Generator;
import joptsimple.OptionParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.JOptCommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.Arrays;

@Slf4j
public final class Application {
    public static void main(String[] args) {
        Generator generator = getApplicationContext(args).getBean(Generator.class);
        generator.run();
    }

    private static ApplicationContext getApplicationContext(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        setContextPropertySource(ctx, args);
        ctx.scan("app", "generators", "input", "output", "serializers");
        ctx.refresh();
        return ctx;
    }

    private static void setContextPropertySource(AnnotationConfigApplicationContext ctx, String[] args) {
        JOptCommandLinePropertySource propertySource = new JOptCommandLinePropertySource(get().parse(args));
        log.info("Command line property names: {}", Arrays.toString(propertySource.getPropertyNames()));
        ctx.getEnvironment().getPropertySources().addFirst(propertySource);
    }

    private static OptionParser get() {
        OptionParser parser = new OptionParser();
        addRequiredArguments(parser);

        return parser;
    }

    private static void addRequiredArguments(OptionParser parser) {
        parser.accepts("format").withRequiredArg();
        parser.accepts("customerIds").withRequiredArg();
        parser.accepts("dateRange").withRequiredArg();
        parser.accepts("itemsFile").withRequiredArg();
        parser.accepts("itemsCount").withRequiredArg();
        parser.accepts("itemsQuantity").withRequiredArg();
        parser.accepts("eventsCount").withRequiredArg();
        parser.accepts("outDir").withRequiredArg();
    }
}
