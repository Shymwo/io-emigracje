package put.poznan.io;

import java.io.Serializable;

public class ExampleBean implements Serializable {

	private static final long serialVersionUID = 3388684164782903155L;

	private String test;

	public ExampleBean() {
		test = "Hello world!";
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

}
