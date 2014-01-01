package coop.bristolenergy.dataAcquisition.parser;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import coop.bristolenergy.dataAcquisition.domain.Measure;

public class SunWebBoxParserTest {

	private final Logger logger	= LoggerFactory.getLogger(SunWebBoxParserTest.class);
	
	@Test
	public void testParseMeasureReaderWithFile() {		
		URL url = getClass().getClassLoader().getResource("2013-11-08_141500.xml");		
		SunWebBoxParser parser = new SunWebBoxParser();		
		try {			
			List<Measure> measurements = parser.parseMeasureFile(new File(url.getFile())); 
			Measure firstMeasurement = measurements.get(0);
			assertNotNull(firstMeasurement);
		} catch (Exception e) {			
			logger.error("Failed test",e);
			fail();
		}

	}
	
	@Test
	public void testParseMeasureReaderWithStream() {	
		InputStream stream = getClass().getClassLoader().getResourceAsStream("2013-11-08_141500.xml");		
		SunWebBoxParser parser = new SunWebBoxParser();		
		try {			
			List<Measure> measurements = parser.parseMeasureInputStream(stream); 
			Measure firstMeasurement = measurements.get(0);
			assertNotNull(firstMeasurement);
		} catch (Exception e) {			
			logger.error("Failed test",e);
			fail();
		}

	}

}
