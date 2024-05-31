import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PersonAppApplicationTests {

    @Autowired
    private YourBean yourBean; // Replace YourBean with the actual bean you want to test

    @Test
    void contextLoads() {
        assertThat(yourBean).isNotNull(); // Assert that the bean is not null
    }
}
