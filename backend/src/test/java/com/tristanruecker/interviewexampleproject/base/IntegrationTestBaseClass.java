package com.tristanruecker.interviewexampleproject.base;

import com.tristanruecker.interviewexampleproject.models.objects.User;
import com.tristanruecker.interviewexampleproject.models.objects.UserRole;
import com.tristanruecker.interviewexampleproject.models.objects.types.Gender;
import com.tristanruecker.interviewexampleproject.models.objects.types.Roles;
import com.tristanruecker.interviewexampleproject.models.repositores.UserRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class IntegrationTestBaseClass {

    @LocalServerPort
    protected Integer randomServerPort;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Flyway flyway;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Cleanup database before every test
     */
    @BeforeEach
    public void clearDatabaseBeforeEachTest() {
        cleanUpDatabase();
        createTestData();
    }

    private void cleanUpDatabase() {
        jdbcTemplate.execute("DROP SCHEMA public CASCADE");
        jdbcTemplate.execute("CREATE SCHEMA public");
        jdbcTemplate.execute("GRANT ALL ON SCHEMA public TO postgres");
        jdbcTemplate.execute("GRANT ALL ON SCHEMA public TO public");

        flyway.migrate();
    }

    private void createTestData() {
        User user = new User();
        user.setEmail("test.registration@gmail.com");
        user.setPassword(bCryptPasswordEncoder.encode("test1234"));
        user.setName("test_register_223");
        user.setAge(27);
        user.setGender(Gender.FEMALE);

        UserRole userRole = new UserRole();
        userRole.setRoleName(Roles.SUPERADMIN);
        userRole.setUser(user);

        user.setUserRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

    protected <T> T createRetrofitClient(Class<T> className) {
        Retrofit retrofit = createRetrofitClient(new OkHttpClient.Builder());
        return retrofit.create(className);
    }


    protected <T> T createRetrofitClient(Class<T> className, String token) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Request.Builder newRequest = request
                            .newBuilder()
                            .header("Authorization",
                                    "Bearer " + token);
                    return chain.proceed(newRequest.build());
                });

        Retrofit retrofit = createRetrofitClient(okHttpClientBuilder);
        return retrofit.create(className);
    }


    private Retrofit createRetrofitClient(OkHttpClient.Builder okHttpClientBuilder) {
        return new Retrofit.Builder()
                .baseUrl("http://localhost:" + randomServerPort + "/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClientBuilder.build())
                .build();
    }

}
