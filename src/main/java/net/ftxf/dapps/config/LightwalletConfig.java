package net.ftxf.dapps.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

class LightwalletInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		headers.setBearerAuth(LightwalletConfig.TOKEN);
		return execution.execute(request, body);
	}
}

@Configuration
public class LightwalletConfig {
	public static final String EMAIL = "system@share.com";
	public static final String PASSWORD = "4dminsh4r3@#123";
	
	public static String URL = "https://lightwallet.appspot.com/";
	public static String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJsaWdodHdhbGxldCIsImp0aSI6InN5c3RlbUBzaGFyZS5jb20ifQ._Y72goYvO77ThtHkEzFWjruuMIJvdIILglGU3exKUKU";

	@Bean
	public RestTemplate lightWalletRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(Collections.singletonList(new LightwalletInterceptor()));
		return restTemplate;
	}
}
