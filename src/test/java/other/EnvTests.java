package other;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnvTests {
    @Test void whatever () {
        Dotenv dotenv = Dotenv.load();
        // System.out.println(dotenv.get("resolvers-package"));
        // for(var i : dotenv.entries()) {
        //     System.out.println(i);
        // }
        assertTrue(dotenv.get("resolvers-package").equals("presentation.resolvers.impl"));
    }
}
