package application.config;

import lombok.Getter;
import org.apache.logging.log4j.core.util.JsonUtils;
import presentation.resolvers.CommandResolver;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.cdimascio.dotenv.Dotenv;
import presentation.resolvers.impl.*;

@Getter
public class Configuration {
    
    private final static Dotenv dotenv = Dotenv.load();
    
    public static Map<String, CommandResolver> resolvers = new HashMap<>();
    private static final String packageName = dotenv.get("resolvers-package");
    // System.getProperty("resolvers-package", "presentation.resolvers.impl");

    static {
        System.out.println(packageName);
        resolvers.put("/add", new AddMovieCommandResolver());
        resolvers.put("/cancel", new CancelOperationCommandResolver());
        resolvers.put("/delete", new ClearAllAddedMoviesCommandResolver());
        resolvers.put("/findbytitle", new FindMovieByTitleCommandResolver());
        resolvers.put("/history", new GetHistoryCommandResolver());
        resolvers.put("/showall", new ShowAllAddedMoviesCommandResolver());
        resolvers.put("/start", new StartCommandResolver());
        // ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // InputStream resourceAsStream = systemClassLoader.getResourceAsStream(packageName.replaceAll("[.]", "/"));
        // if(resourceAsStream == null) {
        //     System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
        // }
        // BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(resourceAsStream));
        // Stream<String> lines = bufferedInputStream.lines();
        // List<? extends Class<?>> allClasses = lines.filter(line -> line.endsWith(".class"))
        //         .map(line -> {
        //             try {
        //                 return Class.forName(
        //                         packageName + "." + line.substring(0, line.lastIndexOf('.')));
        //             } catch (ClassNotFoundException e) {
        //                 e.printStackTrace();
        //             }
        //             return null;
        //         }).collect(Collectors.toList());
        //
        // List<? extends Class<?>> filteredClasses = allClasses.stream()
        //         .filter(elem -> Arrays.stream(elem.getInterfaces()).parallel().filter(iface -> Objects.equals(iface, CommandResolver.class)).count() > 0)
        //         .collect(Collectors.toList());
        //
        // filteredClasses.forEach(elem -> {
        //
        //     try {
        //         CommandResolver resolver = (CommandResolver) elem.getConstructor().newInstance();
        //         resolvers.put(resolver.getCommandName(), resolver);
        //     } catch (InstantiationException e) {
        //         e.printStackTrace();
        //     } catch (IllegalAccessException e) {
        //         e.printStackTrace();
        //     } catch (InvocationTargetException e) {
        //         e.printStackTrace();
        //     } catch (NoSuchMethodException e) {
        //         e.printStackTrace();
        //     }
        // });
        
        
    }
}






