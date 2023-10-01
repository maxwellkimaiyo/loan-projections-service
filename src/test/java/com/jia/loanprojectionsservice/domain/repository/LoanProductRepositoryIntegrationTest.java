package com.jia.loanprojectionsservice.domain.repository;

import com.jia.loanprojectionsservice.util.CommonPostgresqlContainer;
import com.jia.loanprojectionsservice.domain.entities.LoanProductEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static com.jia.loanprojectionsservice.domain.entities.enums.LoanTypes.MONTHLY;
import static com.jia.loanprojectionsservice.domain.entities.enums.LoanTypes.WEEKLY;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * JPA tests with datasource configured via standard properties files
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanProductRepositoryIntegrationTest {


    @Autowired
    private LoanProductRepository loanProductRepository;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Container
    public static PostgreSQLContainer postgreSQLContainer = CommonPostgresqlContainer.getInstance();


    @Test
    @DisplayName("Injected components are not null")
    void injectedComponentsAreNotNull(){
        assertThat(loanProductRepository).isNotNull();
    }
    @Test
    @Transactional
    @DisplayName("When find by weekly type then return weekly LoanProductEntity")
    void findByWeeklyTypeTest() {
        // given
        String loanTypes = WEEKLY.name();
        // when
        Optional<LoanProductEntity> found = loanProductRepository.findByLoanType(loanTypes);
        // then
        Assertions.assertTrue(found.isPresent());
        LoanProductEntity entity = found.get();
        assertThat(entity.getType()).isEqualTo(loanTypes);
        Assertions.assertEquals(50.0, entity.getLoanFee().getServiceFeeCap());
    }
    @Test
    @Transactional
    @DisplayName("When find by monthly type then return monthly LoanProductEntity")
    void findByMonthlyTypeTest() {
        // given
        String loanTypes = MONTHLY.name();
        // when
        Optional<LoanProductEntity> found = loanProductRepository.findByLoanType(loanTypes);
        // then
        Assertions.assertTrue(found.isPresent());
        LoanProductEntity entity = found.get();
        assertThat(entity.getType()).isEqualTo(loanTypes);
        Assertions.assertEquals(100.0, entity.getLoanFee().getServiceFeeCap());
    }


}