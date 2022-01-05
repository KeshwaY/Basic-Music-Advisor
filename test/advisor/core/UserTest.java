package advisor.core;

import advisor.core.abstraction.User;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserTest {

    private User underTest;

    @Test
    void itShouldCreateUserWithRandomUUID() {
        // given
        // when
        User user = new AdvisorUser();

        // then
        assertThat(user.getUuid())
                .isExactlyInstanceOf(UUID.class);
    }
}