package com.github.richardflee.astroimagej.data_objects;


import com.github.richardflee.astroimagej.utils.AstroCoords;

/**
 * Single record resulting from an on-line catalog database query. Instances of this class
 * encapsulate coordinate and magnitude data for a single Comparison or
 * Reference star.
 */
public class FieldObject extends BaseFieldObject {
	
	private double mag = 0.0;
	private double magErr = 0.0;

	private Integer nObs = 1;
	private double radSepAmin = 0.0;

	private String apertureId = "Cnn"; 
	private boolean selected = true;
	private boolean accepted = true;
	private boolean isTarget = false;
	
	private double deltaMag = 0.0;

	/**
	 * No arg constructor, default Sirius parameters
	 */	
	public FieldObject() {
		super( "sirius", 
				AstroCoords.raHmsToRaHr("06:45:08.917"),
				AstroCoords.decDmsToDecDeg("-16:42:58.02"));		
		this.mag = -1.46;
		this.magErr = 0.02;
	}
	
	/**
	 * Four parameter constructor
	 * 
	 * @param objectId object identifier
	 * @param raHr J2000 RA in hour (0 to 24 hr)
	 * @param decDeg J2000 Dec in degree (±90°)
	 * @param mag catalog magnitude for current filter band
	 * @param magErr estimate uncertainty in mag value
	 */
	public FieldObject(String objectId, double raHr, double decDeg, double mag, double magErr) {
		super(objectId, raHr, decDeg);

		this.mag = mag;
		this.magErr = magErr;
	}
	
	// copy constructor
	public FieldObject (FieldObject fo) {
		super(fo.getObjectId(), fo.getRaHr(), fo.getDecDeg());
		
		this.mag = fo.getMag();
		this.magErr = fo.getMagErr();
		this.nObs = fo.getnObs();
		this.apertureId = fo.getApertureId();
		this.radSepAmin = fo.getRadSepAmin();
		this.selected = fo.isSelected();
		this.isTarget = fo.isTarget();
		this.deltaMag = fo.getDeltaMag();		
	}
	
	
	/**
	 * Delta mag setter
	 */
	public void setDeltaMag(double deltaMag) {
		this.deltaMag = deltaMag;
	}
	
	
	/**
	 * Computes mag difference between reference and target object
	 * 
	 * @param user estimate for target mag, current filter band
	 */
	public void computeDeltaMag(double targetMag) {
		this.deltaMag = mag - targetMag;
	}
	
	/**
	 * Delta mag getter
	 */
	public double getDeltaMag() {
		return deltaMag;
	}
	
	
	/**
	 * setRadSepAmin setter
	 */
	public void setRadSepAmin(double radSepAmin) {
		this.radSepAmin = radSepAmin;
	}

	/**
	 * Computes angular distance in arcmin between current object and the target object
	 * Eqn: A = acos(sin(dec)*sin(dec0)+cos(dec)*cos(dec0)*cos(ra-ra0))
	 * where (ra0, dec0) and (ra, dec) are astro coordinates for target & this object in DEGREEs
	 * 
	 * @param target target FieldObject 
	 */
	public void computeRadSepAmin(FieldObject target) {
		double ra = Math.toRadians(raHr * 15.0);
		double ra0 = Math.toRadians(target.getRaHr() * 15.0);

		double dec = Math.toRadians(decDeg);
		double dec0 = Math.toRadians(target.getDecDeg());

		double cosA = Math.sin(dec) * Math.sin(dec0) + Math.cos(dec) * Math.cos(dec0) * Math.cos(ra - ra0);
		this.radSepAmin = Math.toDegrees(Math.acos(cosA)) * 60.0;
	}
	
	
	public double getRadSepAmin() {
		return radSepAmin;
	}

	// autogenerated getters, setters and toString methods
	public String getApertureId() {
		return apertureId;
	}

	public void setApertureId(String apertureId) {
		this.apertureId = apertureId;
	}

	public Integer getnObs() {
		return nObs;
	}

	public void setnObs(Integer nObs) {
		this.nObs = nObs;
	}

	public String getObjectId() {
		return objectId;
	}


	public double getRaHr() {
		return raHr;
	}

	public void setRaHr(double raHr) {
		this.raHr = raHr;
	}

	public double getDecDeg() {
		return decDeg;
	}

	public void setDecDeg(double decDeg) {
		this.decDeg = decDeg;
	}

	public double getMag() {
		return mag;
	}

	public boolean isTarget() {
		return isTarget;
	}


	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}


	public void setMag(double mag) {
		this.mag = mag;
	}

	public double getMagErr() {
		return magErr;
	}

	public void setMagErr(double magErr) {
		this.magErr = magErr;
	}
	
	

	
	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isAccepted() {
		return accepted;
	}


	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	

	@Override
	public String toString() {
		return "FieldObject [mag=" + mag + ", magErr=" + magErr + ", nObs=" + nObs + ", radSepAmin=" + radSepAmin
				+ ", apertureId=" + apertureId + ", selected=" + selected + ", accepted=" + accepted + ", isTarget="
				+ isTarget + ", deltaMag=" + deltaMag + ", objectId=" + objectId + ", raHr=" + raHr + ", decDeg="
				+ decDeg + "]";
	}


	public static void main(String[] args) {

		FieldObject fo1 = new FieldObject("wasp12", 6.50862013, 29.688453, 12.345, 0.23);
		
		System.out.println(fo1.toString());
		
		FieldObject fo2 = new FieldObject(null, 6.50862013, 29.688453, 14.716, 0.09);
		String testId = "06303103+29411843";
		
		System.out.println(testId);
		System.out.println(fo2.getObjectId());
		System.out.println(testId.equals(fo2.getObjectId()));
		
		double deltaMag = 1.234;
		int deltaMag1000 = (int) (1000 * deltaMag);
		
		fo1.setDeltaMag(deltaMag);
		System.out.println(String.format("Test overload integer delta mag %.3f, %.3f", 
								deltaMag1000 / 1000.0, fo1.getDeltaMag()));
	}

}

