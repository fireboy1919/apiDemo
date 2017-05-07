package com.rustyphillips;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rustyphillips.model.Metric;
import com.rustyphillips.repository.MetricRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDemoApplicationTests {

	@Autowired
	private MetricRepository repo;
	
	@Test
	public void testHappyPath() {
		repo.put("test");
		repo.addValue("test", 1D);
		repo.addValue("test", 2D);
		
		Metric m = repo.get("test");
		assertEquals(1D, m.getMin(), 0);
		assertEquals(2D, m.getMax(), 0);
		assertEquals(1.5D, m.getMedian(), 0);
		assertEquals(1.5D, m.getMean(), 0);
	}
	
	@Test
	public void testOddMedian() {
		repo.put("test");
		repo.addValue("test", 1D);
		repo.addValue("test", 2D);
		repo.addValue("test", 3D);
		
		Metric m = repo.get("test");
		assertEquals(m.getMedian(), 2D, 0);
	}

}
