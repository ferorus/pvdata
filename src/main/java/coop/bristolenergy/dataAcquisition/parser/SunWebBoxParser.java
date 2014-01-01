package coop.bristolenergy.dataAcquisition.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coop.bristolenergy.dataAcquisition.domain.Measure;

public class SunWebBoxParser {
	
	private final Logger logger	= LoggerFactory.getLogger(SunWebBoxParser.class);
	
	
	public List<Measure> parseMeasureFile(File measureFile) {				
		logger.trace("[parse] Parsing measure file {}", measureFile);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(measureFile));
			// remove first line (prolog) to avoid "Content is not allowed in prolog" error
			reader.readLine();
			XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(reader);
			return parseMeasureReader(xmlEventReader);
		} catch (FileNotFoundException e) {			
			logger.error("File not found",e);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("IO Exception",e);
		} catch (XMLStreamException e) {
			logger.error("XMLStream exception",e);
		} catch (FactoryConfigurationError e) {
			logger.error("Cannot set up XMLInputFactory",e);
		}		
		return null;
	}	
	
	public List<Measure> parseMeasureInputStream(InputStream stream) throws XMLStreamException, FactoryConfigurationError {
		XMLEventReader xmlEventReader = XMLInputFactory.newInstance().createXMLEventReader(stream);
		return parseMeasureReader(xmlEventReader);
	}
	
	public List<Measure> parseMeasureReader(XMLEventReader xmlr){	
		try {					
			List<Measure> result = new ArrayList<Measure>();
			while (xmlr.hasNext()) {
				XMLEvent event = xmlr.nextEvent();			
				if (event.isStartElement()) {
					if(!((StartElement) event).getName().toString().equals("CurrentPublic")) {
						continue;
					}		
					try {
						result.add(getMeasure(xmlr));
					} catch (ParseException e) {
						logger.error("Error parsing xml",e);
					}								
				}
			}
			return result;
		}  catch (XMLStreamException e) {			
			logger.error("XMLStreamException",e);
			return null;
		}
	}

	private Measure getMeasure(XMLEventReader xmlr) throws XMLStreamException, ParseException {
		Measure measure = new Measure();
		while (xmlr.hasNext()) {
			XMLEvent event = xmlr.nextEvent();			
			if (event.isStartElement()) {
				String element = ((StartElement) event).getName().toString();
				String text = null;
				try {					
					switch(MeasureElement.getByElementName(element)){
						case KEY: measure.setKey(xmlr.nextEvent().toString()); break;
						case MEAN: text = xmlr.nextEvent().toString(); measure.setMean(new BigDecimal(text)); break;
						case BASE: measure.setBase(new BigDecimal(xmlr.nextEvent().toString())); break;
						case MIN: measure.setMin(new BigDecimal(xmlr.nextEvent().toString())); break;
						case MAX: measure.setMax(new BigDecimal(xmlr.nextEvent().toString())); break;
						case PERIOD: measure.setPeriod(Integer.valueOf(xmlr.nextEvent().toString())); break;
						case TIMESTAMP: measure.setTimestamp(getTimestamp(xmlr.nextEvent().toString())); break;
					}	
				} catch(NumberFormatException nfe) {
					measure.setText(text);
				} catch(IllegalArgumentException iae) {
					iae.printStackTrace();
				} catch(ParseException pe) {
					pe.printStackTrace();
				} 
				
							
			} else if (event.isEndElement()) {
				if(((EndElement) event).getName().toString().equals("CurrentPublic")) {
					return measure;
				}
			}
		}
		throw new ParseException("Unexpected end of file.",-1);
	}

	private Date getTimestamp(String str) throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				
		return sdf.parse(str.replace('T', ' '));		
	}

}
