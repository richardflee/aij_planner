package com.github.richardflee.astroimagej.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.github.richardflee.astroimagej.data_objects.CatalogSettings;
import com.github.richardflee.astroimagej.data_objects.FieldObject;

public class FieldObjectsCollection {
	
	private List<FieldObject> fieldObjects;
	
	public FieldObjectsCollection() {
		this.fieldObjects = new ArrayList<>();
	}
	
	
	public void addFieldObjects(List<FieldObject> fos) {
		this.fieldObjects.addAll(fos);
	}
	
	
	public void update(FieldObject target) {
		this.fieldObjects.stream().forEach(p -> p.setRadSepAmin(target));
		this.fieldObjects.stream().forEach(p -> p.setDeltaMag(target));
	}
	
	public void sortByDistance(FieldObject target) {		
		this.fieldObjects = this.fieldObjects.stream()
				.sorted(Comparator.comparingDouble(p -> p.getRadSepAmin()))
				.collect(Collectors.toList());	
	}
	
	public void sortByDeltaMag(FieldObject target) {
		this.fieldObjects = this.fieldObjects.stream()
				.sorted(Comparator.comparingDouble(p -> Math.abs(p.getDeltaMag())))
				.collect(Collectors.toList());		
	}
	
	public void filterByNumberObservations(int numberObs) {
		this.fieldObjects.stream().forEach(p -> p.setFiltered(p.getnObs() >= numberObs));		
	}
	
	public void filterByMagLimits(CatalogSettings settings) {		
		// apply mag limits filter
		if (settings.isApplyLimitsValue() == true) {
			var upperMagRange = settings.getUpperLimitValue() + settings.getNominalMagValue();
			var lowerMagRange = settings.getLowerLimitValue() + settings.getNominalMagValue();
			var disableUpperLimit = CatalogSettings.isUpperLimitDisabled(settings);
			var disableLowerLimit = CatalogSettings.isLowerLimitDisabled(settings);
			for (var fo : fieldObjects) {
				// Initialise with result of nobs filter
				var isFiltered = fo.isFiltered();
				// apply mag limits unless limits disabled
				isFiltered = isFiltered && (disableUpperLimit || (fo.getMag() <= upperMagRange));
				isFiltered = isFiltered && (disableLowerLimit || (fo.getMag() >= lowerMagRange));
				fo.setFiltered(isFiltered);
			}
			System.out.println();
		}
	}
	
	public List<FieldObject> getFieldObjects() {		
		return this.fieldObjects;
	}
	
	public int getTotalCount() {
		return this.fieldObjects.size();
	}
	
	public int getFilteredCount() {
		long count = this.fieldObjects.stream()
				.filter(p -> p.isFiltered())
				.count();
		return Math.toIntExact(count);		
	}
	
	public int getSelectedCount() {		
		long count = this.fieldObjects.stream()
				.filter(p -> p.isFiltered())
				.filter(p -> p.isSelected())
				.count();
		return Math.toIntExact(count);	 
	}
	
	@Override
	public String toString() {
		var s = "";
		for (var fo : fieldObjects) {
			s += fo.toString() + "\n";
		}
		return s;
	}
	

	

}