package put.poznan.io.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the contact database table.
 *
 */
@Entity
@Table(name = "Groups")
public class Group {
	private Integer id;
	private String name;
	private String field;
	private int year;
	private int maxlimit;

	@Id
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMaxlimit() {
		return maxlimit;
	}

	public void setMaxlimit(int maxlimit) {
		this.maxlimit = maxlimit;
	}

}