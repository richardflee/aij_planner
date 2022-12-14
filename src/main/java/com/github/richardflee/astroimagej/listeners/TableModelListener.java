package com.github.richardflee.astroimagej.listeners;

import java.util.List;

import com.github.richardflee.astroimagej.data_objects.FieldObject;

/**
 * Interface to handle button click updateTable events 
 *
 */
@FunctionalInterface
public interface TableModelListener {
	public void updateTable(List<FieldObject> fieldObjects);
	
}


