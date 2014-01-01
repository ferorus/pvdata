package coop.bristolenergy.dataAcquisition.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="measure")
public class Measure {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "the_key", nullable=false)
	private String key;
	private BigDecimal min;
	private BigDecimal max;
	private BigDecimal mean;
	private String text;
	private BigDecimal base;
	private Integer period;
	private Date timestamp;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public BigDecimal getMin() {
		return min;
	}
	public void setMin(BigDecimal min) {
		this.min = min;
	}
	public BigDecimal getMax() {
		return max;
	}
	public void setMax(BigDecimal max) {
		this.max = max;
	}
	public BigDecimal getMean() {
		return mean;
	}
	public void setMean(BigDecimal mean) {
		this.mean = mean;
	}
	public BigDecimal getBase() {
		return base;
	}
	public void setBase(BigDecimal base) {
		this.base = base;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	

}
