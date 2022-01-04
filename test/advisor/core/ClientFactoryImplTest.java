package advisor.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;

import static org.assertj.core.api.Assertions.*;

class ClientFactoryImplTest {

    private ClientFactory underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClientFactoryImpl();
    }

    @Test
    void itShouldCreateClient() {
        // given
        // when
        HttpClient client = underTest.createClient();

        // then
        assertThat(client)
                .isInstanceOf(HttpClient.class);
    }
}