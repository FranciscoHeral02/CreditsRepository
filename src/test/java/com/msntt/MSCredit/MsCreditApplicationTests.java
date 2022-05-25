package com.msntt.MSCredit;

import com.msntt.MSCredit.domain.repository.CreditRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@RunWith(SpringRunner.class)
@DataMongoTest
public class MsCreditApplicationTests {

	@Autowired
	CreditRepository repository;
	@Test
	public void shouldBeNotEmpty() {
		assertThat(repository.countBycodebusinesspartner("P10395817").block());

	}

}
