package social;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Person  implements Comparator<Person>{
	String name, code , surname;
	List<String> friendsList = new ArrayList<>();

	public Person(String code, String name, String surname) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.name = name;
		this.surname = surname;
	}
	// this is a constructor defined for the method personWithLargestNumberOfFriend in the Social class
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}


	public String getCode() {
		return code;
	}


	public String getSurname() {
		return surname;
	}
	public String getPersonInfo(){
		return String.join(" ", code, name, surname);
	}

	@Override
	public int compare(Person o1, Person o2) {
		// TODO Auto-generated method stub
		return o1.getCode().compareTo(o2.getCode());
		
	}
	public void putFriendToList(String friendName) {
		friendsList.add(friendName);
		
	}
	public Collection<String> listOfFriends(){
		return friendsList;
	}
	public Integer getFriendCount() {
		return friendsList.size();
	}

	


}
