package com.github.richardflee.astroimagej.listeners;


// TODO appears redundant -- REMOVE

import com.github.richardflee.astroimagej.data_objects.NoiseData;
import com.github.richardflee.astroimagej.data_objects.Observer;

public interface ObserverTabListener {
	
	public Observer getObserverData();
	public void setObserverData(Observer observer);
	
	public void setObservationSiteData();
	
	public void setNoiseData(NoiseData data);

}
