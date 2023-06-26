package social;

import java.util.ArrayList;
import java.util.List;

public class Group {
	List<String> peopleIN = new ArrayList<>();
	private String name;
	public Group(String name) {
		this.name = name;
		
	}
	public void addPeople(String person) {
		peopleIN.add(person);
	}
	public List<String> giveList(){
		return peopleIN;
	}
	public String getName() {
		return name;
	}
	//

}
