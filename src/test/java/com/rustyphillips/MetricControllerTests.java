package com.rustyphillips;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.rustyphillips.controller.MetricController;
import com.rustyphillips.repository.MetricRepository;

import static org.junit.Assert.*;
import org.junit.Before;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricControllerTests {
	@Autowired
	private MetricRepository repo;
	
	@Autowired
	private MetricController controller;

	private String testTarget = "test";
	
	@Before public void initialize() {
		repo.clear();
		repo.put(testTarget);
		repo.addValue(testTarget, 1D);
	}
	
	@Test public void getMetricStats() {
		assertEquals(1D, controller.getMetricStats(testTarget).getMean(), 1D); 
	}
	
	@Test public void addMetric() {
		controller.addMetric("test2");
		assertNotEquals(repo.get("test2"), null);
	}
	
	@Test public void smokeTestStats() {
		assertEquals(1D, controller.getMetricStats(testTarget).getMedian(), 0D); 
		assertEquals(1D, controller.getMean(testTarget), 0D); 
		assertEquals(1D, controller.getMax(testTarget), 0D); 
		assertEquals(1D, controller.getMin(testTarget), 0D); 
		assertEquals(1D, controller.getMedian(testTarget), 0D); 
	}
	
	@Test public void addValue() {
		controller.addValue(testTarget, 2D);
		assertEquals(1.5D, repo.get(testTarget).getMedian(), 0D); 
	}

	@Test public void getDefault() {
		assertEquals(1, controller.getDefault().size());
	}	
	
}