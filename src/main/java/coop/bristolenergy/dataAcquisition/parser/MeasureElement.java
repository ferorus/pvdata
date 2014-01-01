package coop.bristolenergy.dataAcquisition.parser;

public enum MeasureElement {
	KEY("Key"), MIN("Min"), MAX("Max"), MEAN("Mean"), PERIOD("Period"), BASE("Base"), TIMESTAMP("Timestamp");
	
	private  String elementName;
	
	private MeasureElement(String str) {
		elementName = str;
	}
	
	public  String getElementName() {
		return elementName;
	}
	
	public static MeasureElement getByElementName(String str) {
		for(MeasureElement me : MeasureElement.values()) {
			if(me.getElementName().equals(str))
				return me;
		}
		throw new IllegalArgumentException("Not a valid MeasureElement: "+str);
	}
		
}
