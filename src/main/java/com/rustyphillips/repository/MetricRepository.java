package com.rustyphillips.repository;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.rustyphillips.model.Metric;

/* 
 * Simple in-memory mechanism for keeping track of metrics. 
 * I'm calling it a repository because that's the function it
 * serves, but not using the repository class because 
 * that level of work isn't required for this.
 */
@Service
@Scope("singleton")
public class MetricRepository  {
	HashMap<String,Metric> metrics;
	
	public MetricRepository() {
		this.metrics = new HashMap<String, Metric>();
	}
	
	public Metric get(String name) {
		return this.metrics.get(name);
	}
	
	public Metric put(String name) {
		if(metrics.containsKey(name)) {
			return metrics.get(name);
		}
		if(name != null )
			this.metrics.put(name, new Metric());
		else
			throw new NullPointerException("No name given");
		return new Metric();
	}
	
	// O(log2(N)) time, O(N) memory.
	public Metric addValue(String name, Double val) throws NoSuchElementException {
		if(val == null) {
			throw new NullPointerException("No value given");
		}
		if(name == null || name.trim().isEmpty())
			throw new NullPointerException("No name specified");
		Metric m = this.get(name);
		if(m == null)
			throw new NoSuchElementException("No metric exists by that name.");
		m.addValue(val);
		return m;
	}
	
	public Set<String> getNames() {
		return this.metrics.keySet();
	}
}
