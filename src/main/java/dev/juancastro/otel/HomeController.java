package dev.juancastro.otel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.observation.annotation.Observed;

@RestController
@RequestMapping("/home")
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@GetMapping
	@Observed(name = "home.countV1")
	public String home() {
		log.info("Home endpoint called");
		return "Hello from v1!";
	}

	@GetMapping(version = "2.0.0")
	@Observed(name = "home.countV2")
	public String homeV2() {
		log.info("Home endpoint called");
		return "Hello from v2!";
	}

	@GetMapping("/greet/{name}")
	public String greet(@PathVariable("name") String name) {
		log.info("Greeting user: {}", name);
		simulateWork();
		return "Hello, " + name + "!";
	}

	@GetMapping("/slow")
	public String slow() throws InterruptedException {
		log.info("Starting slow operation");
		Thread.sleep(500);
		log.info("Slow operation completed");
		return "Done!";
	}

	private void simulateWork() {
		try {
			Thread.sleep(50);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
