package social;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Social {

/**
* Creates a new account for a person
*
* @param code nickname of the account
* @param name first name
* @param surname last name
* @throws PersonExistsException in case of duplicate code
*
*/
Map<String, Person> Code2Person = new HashMap<>();



//Map<Person, Collection<String>> allPeople = new TreeMap<>();
public void addPerson(String code, String name, String surname) throws PersonExistsException {
	Person p = new Person(code, name, surname);
	
//	Optional<Person> filteredPerson = allPeople.entrySet().stream()
//	           .filter(entry -> entry.getKey().getCode().equals(code))
//	           .map(Map.Entry::getKey)
//	           .findFirst();
//	
//	//System.out.println(filteredPerson);
//
//	
//	if(!filteredPerson.isPresent()) {
//		allPeople.put(p, null);
//	}
//	else {
//		throw new PersonExistsException();
//	}
	
	if(!Code2Person.containsKey(code)) {
		Code2Person.put(code, p);
	}
	else {
		throw new PersonExistsException();
	}

}

/**
* Retrieves information about the person given their account code.
* The info consists in name and surname of the person, in order, separated by blanks.
*
* @param code account code
* @return the information of the person
* @throws NoSuchCodeException
*/
public String getPerson(String code) throws NoSuchCodeException {
//Person filteredPerson = null;
//	try {
//	   filteredPerson = allPeople.entrySet().stream()
//	           .filter(entry -> entry.getKey().getCode().equals(code))
//	           .map(Map.Entry::getKey)
//	           .findFirst()
//	           .orElseThrow(NoSuchCodeException::new);
//	   
//	
//	   // Process the filteredPerson object
//	 
//	} catch (NoSuchCodeException nsp) {
//	   nsp.printStackTrace();
//	}
//		return filteredPerson.getPersonInfo();
		
	if(Code2Person.containsKey(code)) {
		
		Person p = Code2Person.get(code);
		return p.getPersonInfo();
	}else {
		
		throw new NoSuchCodeException();
	}

}

/**
* Define a friendship relationship between to persons given their codes.
*
* Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
*
* @param codePerson1 first person code
* @param codePerson2 second person code
* @throws NoSuchCodeException in case either code does not exist
*/
public void addFriendship(String codePerson1, String codePerson2) throws NoSuchCodeException {
	// in the all persons list check if both person1 and 2 exists. If yes add list of friends to the 
	if(!Code2Person.containsKey(codePerson2) || !Code2Person.containsKey(codePerson1)) {
		throw new NoSuchCodeException();
		
	}
	else {
		Person person1 = Code2Person.get(codePerson1);
		person1.putFriendToList(codePerson2);
		
		Person person2 =Code2Person.get(codePerson2);
		person2.putFriendToList(codePerson1);
		
		//enrich the person to friendsList map
		//allPeople.put(person2, person2.listOfFriends());//all people are hte map with at least one friend
		//allPeople.put(person1, person1.listOfFriends());
		
	}
	

}

/**
* Retrieve the collection of their friends given the code of a person.
*
*
* @param codePerson code of the person
* @return the list of person codes
* @throws NoSuchCodeException in case the code does not exist
*/
public Collection<String> listOfFriends(String codePerson)
throws NoSuchCodeException {

		//if the given code is not in the Code2Person throw exception 
		//if the code exist but the person has no friends return an empty list
	if(Code2Person.containsKey(codePerson)) {
		Person p = Code2Person.get(codePerson);
		List<String> emptyList = Collections.emptyList();  
		if(p.listOfFriends() != null)
			return p.listOfFriends();
		else // when the person has no friends
			return emptyList;
		
	}
	else {
		throw new NoSuchCodeException();
	}

}

/**
* Retrieves the collection of the code of the friends of the friends
* of the person whose code is given, i.e. friends of the second level.
*
*
* @param codePerson code of the person
* @return collections of codes of second level friends
* @throws NoSuchCodeException in case the code does not exist
*/
public Collection<String> friendsOfFriends(String codePerson)
throws NoSuchCodeException {
	//for each firend of the given person take all the friend person and make a list and go to
	//here maybe make the list 
	Person p1 = Code2Person.get(codePerson);
	if (p1 == null)
		throw new NoSuchCodeException();
	List<String> secondLevelFriends = new ArrayList<>();
	Person P = Code2Person.get(codePerson);
	Collection <String> FriendsOfP = new ArrayList<>();
	FriendsOfP = P.listOfFriends();
	//secondLevelFriends.add(FriendsOfP.stream().forEach().getFriendsList());	
	//Friend of p is the list of strings for the friends code of the person
	//I want to use the stream to go over all the friends and call the getPerson method using the each friend code 
	//then call getFriendsList method on the Person object obtained after the call of getPerson.
	FriendsOfP.stream()
    .map(Code2Person::get) // Get Person object for each friend code
    .map(Person::listOfFriends) // Get friends list for each Person object
    .forEach(secondLevelFriends::addAll); // Add all friends to the secondLevelFriends list

	return secondLevelFriends;
}

/**
* Retrieves the collection of the code of the friends of the friends
* of the person whose code is given, i.e. friends of the second level.
* The result has no duplicates.
*
*
* @param codePerson code of the person
* @return collections of codes of second level friends
* @throws NoSuchCodeException in case the code does not exist
*/
public Collection<String> friendsOfFriendsNoRepetition(String codePerson)throws NoSuchCodeException {
	//hera maybe make a set of those returned by the friends of the friends
	if(Code2Person.containsKey(codePerson)) {
		Collection <String> withDuplicates = friendsOfFriends(codePerson);
		Collection<String> noRepetition = withDuplicates.stream()
										.filter(p -> !p.equals(codePerson)) // without the filter it has the person itself
										.collect(Collectors.toCollection(LinkedHashSet::new));
		return noRepetition;	
	}
	else {
		throw new NoSuchCodeException();
	}


}

/**
* Creates a new group with the given name
*
* @param groupName name of the group
*/
//Collection<String> allGroup = new ArrayList<>();
Map<String, Group> GroupName2Group = new HashMap<>();
//List<String> peopleOfTheGroups = new ArrayList<>();

public void addGroup(String groupName) {
	//it says group name should be single name should I check that
	//will this not throw an exception if the group name already exists No
	Group g = new Group(groupName);
	GroupName2Group.put(groupName, g);
	
}

/**
* Retrieves the list of groups.
*
* @return the collection of group names
*/
public Collection<String> listOfGroups() {
	Collection<String> groupList = GroupName2Group.keySet();
	return groupList;
}

/**
* Add a person to a group
*
* @param codePerson person code
* @param groupName  name of the group
* @throws NoSuchCodeException in case the code or group name do not exist
*/

public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
	
	if(!Code2Person.containsKey(groupName) && ! GroupName2Group.containsKey(groupName) ) {
		throw new NoSuchCodeException();
	}
	else {
		Group g = GroupName2Group.get(groupName);
		g.addPeople(codePerson);
		
		}
}

/**
* Retrieves the list of people on a group
*
* @param groupName name of the group
* @return collection of person codes
*/
public Collection<String> listOfPeopleInGroup(String groupName) {
	Group g = GroupName2Group.get(groupName);
	if(g == null) {
		return Collections.emptyList();
	}
	else 
		return g.giveList();
	
}

/**
* Retrieves the code of the person having the largest
* group of friends
*
* @return the code of the person
*/
public String personWithLargestNumberOfFriends() {
	//iterate throug the person to code hash map value.getlistoffriends.size()
	//filter the max
	//for each key fo the hashmap take the value(person) call list of friends in each of them. select the key with the maximum size of numfriends
//	Person p = new Person();
//	Stream<Person> famous = Code2Person.values().stream()
//										.filter(p-> p.listOfFriends().size() == max(p.listOfFriends())));
//	return famous.getCode();
	
    Person personWithLargestFriendList = Code2Person.values().stream()
            .max(Comparator.comparingInt(Person::getFriendCount))
            .orElse(null);
    if(personWithLargestFriendList ==null) {
    	return " ";
    }
    else {
    	return personWithLargestFriendList.getCode();
    }

}

/**
* Find the code of the person with largest number
* of second level friends
*
* @return the code of the person
*/


public String personWithMostFriendsOfFriends() {
	//i consodered the lsit of second order friends without the repetition
	//PwLargest2ndOrderFriends
	//Collection <String> secondOrderList = 
	 return Code2Person.keySet()
	            .stream()
	            .max(Comparator.comparingInt(personCode -> {
					try {
						return friendsOfFriendsNoRepetition(personCode).size();
					} catch (NoSuchCodeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return 0;
					}
				}))
	            .orElse(null);
}

/**
* Find the name of group with the largest number of members
*
* @return the name of the group
*/
public String largestGroup() {
	String m = GroupName2Group.keySet().stream()
							  .max(Comparator.comparingInt(name -> GroupName2Group.get(name).peopleIN.size()))
							  .orElse(null);
	
							  
	return m;
}

/**
* Find the code of the person that is member of
* the largest number of groups
*
* @return the code of the person
*/
public String personInLargestNumberOfGroups() {
	return null;
 }
}