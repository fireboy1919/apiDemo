package com.rustyphillips.model;
import java.util.SortedSet;
import java.util.TreeSet;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Metric {
	// Insertion takes log2(N) time so that finding mean takes O(1) time.
	// Storage for the set takes N time.
	
	// SortedSet chosen as a compromise between implementing my own sorted list
	//  and using an embedded database without doing a code audit to find the big O for that database.
	//  In practice, a real database will nearly always be able to do better than this in the average case, but I can't 
	//  as easily prove this worst case.
	private SortedSet<Double> maxHeap;
	private SortedSet<Double> minHeap;
	
	private Double mean = 0D;
	private Double min = Double.MAX_VALUE;
	private Double max = Double.MIN_VALUE;
	
	public Metric() {
		this.maxHeap = Collections.synchronizedSortedSet(new TreeSet<Double>());
		
		// First element should be retrievable in O(1) time because it should be the root element.
		this.minHeap = Collections.synchronizedSortedSet(new TreeSet<Double>( Comparator.<Double>reverseOrder() ));
	}
	
	// Sets the min and max values, and adds the value to the list.
	// Should take O(log2(n))
	public void addValue(double val) {
		if(this.min > val)
			this.min = val;
		if(this.max < val)
			this.max = val;
		findNewMedian(val);
		findNewMean(val);
	}
	
	// Takes O(log2(N)) time in exchange for increased complexity.
	private void findNewMedian(double newVal) {
		if(this.maxHeap.size() == 0)
		{
			this.maxHeap.add(newVal);
			return; 
		}
		if(this.maxHeap.first() >= newVal) 
			this.minHeap.add(newVal);
		else
			this.maxHeap.add(newVal);
	
		if(Math.abs(this.maxHeap.size() - this.minHeap.size()) > 1)
		{
			if(this.maxHeap.size() > this.minHeap.size()){
				this.minHeap.add(this.maxHeap.first());
				this.maxHeap.remove(this.maxHeap.first());
			} else 
			{
				this.maxHeap.add(this.minHeap.first());
				this.minHeap.remove(this.minHeap.first());
			}
		}
	}
	
	// Should be O(1) in time if size is a constant operation (which it should be, and if it isn't, it's 
	// trivial to make it so, so I'm not going to add this in a prototype).
	private int size() {
		return this.maxHeap.size() + this.minHeap.size();
	}
	
	// Takes O(1) time in exchange for having floating point errors.
	//  Assumes that size() is an O(1) operation 
	// (otherwise, the order of this is whatever order that is).
	private void findNewMean( double newVal ) {
		this.mean = (this.mean * ( this.size() - 1) + newVal) / this.size();
	}
	
	/*
	 * Getting all four metrics produced from the dataset is actually an O(1) operation; they've been precomputed.
	 */
	@JsonGetter("median")
	public double getMedian() {
		// Should potentially throw exception in the case of nothing defined, but I don't think 0 is such a bad choice.
		if(this.minHeap.size() == 0 && this.maxHeap.size() == 0 )
			return 0;
		if(this.minHeap.size() == this.maxHeap.size())
			return (this.minHeap.first() + this.maxHeap.first()) / 2.0;
		else
			return this.minHeap.size() > this.maxHeap.size() ? this.minHeap.first(): this.maxHeap.first();
	}
	
	@JsonGetter("mean")
	public double getMean() {
		return this.mean;
	}
	
	@JsonGetter("max")
	public double getMax() {
		return this.max;
	}
	
	@JsonGetter("min")
	public double getMin() { 
		return this.min;
	}
	
}
