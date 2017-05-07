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
	public Metric getMetricStats(@PathVariable String name, ModelMap model) {
		return repo.get(name); 
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.PUT)
	public @ResponseBody Metric addMetric(@PathVariable String name) {
		return repo.put(name);
	}
	
	@RequestMapping(value = "/{name}/{value}", method = RequestMethod.PUT)
	public @ResponseBody Metric addValue(@PathVariable String name, @PathVariable double value) {
		return repo.addValue(name, value);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Set<String> getDefault(ModelMap model) {
		return repo.getNames(); 
	}

}