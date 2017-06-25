package finalProject.model;

public class Person {
	// you said private for these but protected makes more sense
	protected String firstName;
	protected String lastName;
	
	public Person(String firstName, String lastName) {
		//You see this doesn't work in an extended class if the scope is private
		this.firstName = firstName;
		this.lastName  = lastName;
		//or maybe I'm just slave to the red lines now and will do whatever they say
		//MUST OBEY ALL TWIZLERS FREE THEM FROM THEIR PLASTIC PRISONS
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
