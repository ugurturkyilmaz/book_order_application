package com.example.bookorder;

import com.example.bookorder.utils.ResultObject;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${version.name}")
    private String versionName;

    @Test
    public void getOrdersByDate() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
        ResponseEntity<Object> result = restTemplate.exchange("http://localhost:8080/" + versionName + "/orders?startDate=2022-01-01 00:00:00&endDate=" +
                "2022-03-01 00:00:00&limit=10&offset=0", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        assertEquals(200, result.getStatusCodeValue());

        ResponseEntity<Object> result400 = restTemplate.exchange("http://localhost:8080/" + versionName + "/orders?startDate=asdas&endDate=" +
                "2022-03-01 00:00:00&limit=10&offset=0", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        assertEquals(400, result400.getStatusCodeValue());

        ResponseEntity<Object> result401 = restTemplate.exchange("http://localhost:8080/" + versionName + "/orders?startDate=asdas&endDate=" +
                "2022-03-01 00:00:00&limit=10&offset=0", HttpMethod.GET,null, Object.class);

        assertEquals(401, result401.getStatusCodeValue());
    }

    @Test
    public void getOrdersByCustomer() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
        ResponseEntity<Object> result = restTemplate.exchange("http://localhost:8080/" + versionName + "/customers?customerId=61e577c05a498b14184557d5", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        assertEquals(200, result.getStatusCodeValue());

        ResponseEntity<ResultObject> resultEmpty = restTemplate.exchange("http://localhost:8080/" + versionName + "/customers?customerId=3432", HttpMethod.GET, new HttpEntity<>(headers), ResultObject.class);

        assertEquals("[]", resultEmpty.getBody().getData().toString());
    }

}
