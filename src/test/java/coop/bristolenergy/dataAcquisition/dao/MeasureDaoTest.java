package coop.bristolenergy.dataAcquisition.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

import coop.bristolenergy.dataAcquisition.domain.Measure;
import coop.bristolenergy.dataAcquisition.parser.SunWebBoxParser;

//import coop.bristolenergy.dataAcquisition.conf.ApplicationContext;

public class MeasureDaoTest {
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(coop.bristolenergy.dataAcquisition.conf.ApplicationContext.class);
	
	@Resource
	private MeasureDao dao;
	
	private SunWebBoxParser parser = new SunWebBoxParser();
	URL url = getClass().getClassLoader().getResource("2013-11-08_141500.xml");

	@Test
	public void test() {
		dao = (MeasureDao) ctx.getBean("measureDao");
		long countBefore = dao.count();
		List<Measure> measures = parser.parseMeasureFile(new File(url.getFile()));
		for(Measure aMeasure:measures)
			dao.saveAndFlush(aMeasure);
		assertEquals(countBefore+67,dao.count());
	}

}
