package com.rustyphillips.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rustyphillips.repository.MetricRepository;
import com.rustyphillips.model.Metric;

@Controller
@RequestMapping("/")
public class MetricController {

	@Autowired
	MetricRepository repo;
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	public Metric getMetricStats(@PathVariable String name) {
		return repo.get(name); 
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.POST)
	public @ResponseBody Metric addMetric(@PathVariable String name) {
		return repo.put(name);
	}
	
	@RequestMapping(value = "/{name}/median", method = RequestMethod.GET)
	public @ResponseBody double getMedian(@PathVariable String name) {
		return repo.get(name).getMedian();
	}
	
	@RequestMapping(value = "/{name}/mean", method = RequestMethod.GET)
	public @ResponseBody double getMean(@PathVariable String name) {
		return repo.get(name).getMean();
	}
	
	@RequestMapping(value = "/{name}/min", method = RequestMethod.GET)
	public @ResponseBody double getMin(@PathVariable String name) {
		return repo.get(name).getMin();
	}
	
	@RequestMapping(value = "/{name}/max", method = RequestMethod.GET)
	public @ResponseBody double getMax(@PathVariable String name) {
		return repo.get(name).getMax();
	}
	
	
	@RequestMapping(value = "/{name}/add/{value}", method = RequestMethod.PUT)
	public @ResponseBody Metric addValue(@PathVariable String name, @PathVariable double value) {
		return repo.addValue(name, value);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Set<String> getDefault() {
		return repo.getNames(); 
	}

}