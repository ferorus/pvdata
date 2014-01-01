package coop.bristolenergy.dataAcquisition.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import coop.bristolenergy.dataAcquisition.domain.Measure;


public interface MeasureDao extends JpaRepository <Measure,Long>{

}
