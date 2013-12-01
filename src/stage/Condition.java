package stage;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public abstract class Condition {
	abstract boolean isFulfilled(Stage stage);

	protected Map<String, String> myData;

	public Condition() {
		myData = new HashMap<String, String>();
	}

	public Map<String, String> getData() {
		return myData;
	}

	public void setData(Map<String, String> data) {
		myData = data;
	}

	public void addData(String key, String data) {
		myData.put(key, data);
	}
}
