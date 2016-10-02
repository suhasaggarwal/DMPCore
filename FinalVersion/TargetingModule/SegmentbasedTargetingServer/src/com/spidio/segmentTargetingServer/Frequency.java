package com.spidio.segmentTargetingServer;

import java.util.Set;
import java.util.TreeSet;

public class Frequency implements Comparable<Frequency> {

  private String name;
  private int freq;

  public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getFreq() {
	return freq;
}

public void setFreq(int freq) {
	this.freq = freq;
}

public Frequency(String name, int freq) {
    this.name = name;
    this.freq = freq;
  }

  public static void main(String[] args) {
    Set<Frequency> set = new TreeSet<Frequency>();

    set.add(new Frequency("Travel", 1));
    set.add(new Frequency("Sport", 5));
    set.add(new Frequency("Films", 10));
    set.add(new Frequency("Politics", 4));
    

    for (Frequency f : set) {
      System.out.println(f);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) return false;
    if (o.getClass().isAssignableFrom(Frequency.class)) {
      Frequency other = (Frequency)o;
      return other.freq == this.freq && other.name.equals(this.name);
    } else {
      return false;
    }
  }

  @Override
  public int compareTo(Frequency other) {
    if (freq == other.freq) {
      return name.compareTo(other.name);
    } else {
      return other.freq - freq;
    }
  }

  @Override
  public String toString() {
    return name + ":" + freq;
  }

}