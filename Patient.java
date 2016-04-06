package medProgram;

import java.util.Date;
import java.util.Scanner;
//import java.util.List;
public class Patient 
{
		String first,last,visit,status,doc,street,state,phone,notes,insurance,insuranceinfo,allergy;
		int ID, apptId, aptnumber,zipcode;
		Date apttime, birthdate;
		String email;
		
		Patient(int apptIdNum, int ID_number,String firstname, String lastname, Date appointmenttime, String visit_reason, String status_desc, String doctor)
		{
			apptId = apptIdNum;
			ID = ID_number;
			first = firstname;
			last = lastname;
			apttime = appointmenttime;
			visit = visit_reason;
			status = status_desc;
			doc = doctor;
		}
		
		Patient(int ID_number, String firstname, String lastname, Date birth, String street, int aptnumber, String state, int zipcode, String phone, String notes, String insurance, String insuranceinfo, String allergy )
		{
			ID = ID_number;
			first = firstname;
			last = lastname;
			birthdate = birth;
			this.street = street;
			this.aptnumber = aptnumber;
			this.state = state;
			this.zipcode = zipcode;
			this.phone = phone;
			this.notes = notes;
			this.insurance = insurance;
			this.insuranceinfo = insuranceinfo;
			if(allergy == "")
			{
				this.allergy = "None";
			}
			else
			{
				this.allergy = allergy;
			}
			
		}
		
		public Patient() {
			// TODO Auto-generated constructor stub
		}

		public void printPerson()
		{
	
		}
		
		public String getFirst()
		{
			return first;
		}
		
		public String getLast()
		{
			return last;
		}
		
		public Date getBirth()
		{
			return birthdate;
		}
		
		public String getStreet()
		{
			return street;
		}
		
		public int getAptNumber()
		{
			return aptnumber;
		}
		
		public String getState()
		{
			return state;
		}
		
		public int getZip()
		{
			return zipcode;
		}
		
		public String getPhone()
		{
			return phone;
		}
		
		public String getNotes()
		{
			return notes;
		}
		
		public String getInsurance()
		{
			return insurance;
		}
		
		public String getInsuranceInfo()
		{
			return insuranceinfo;
		}
		
		public String getAllergy()
		{
			return allergy;
		}
		
		public int getAppointmentId()
		{
			return apptId;
		}
		
		public int getID()
		{
			return ID;
		}
		
		public String getDoctor()
		{
			return doc;
		}
		
		public void createDatabase()
		{	
		}
		
		public void getList()
		{
		}
		
		public void addPatient(Scanner in)
		{
			System.out.println("Please input name:   ");
			String newPatient = in.nextLine();
			System.out.println("Age:   ");
			int newAge = in.nextInt();
			in.nextLine();
			System.out.println("Gender (M or F):   ");
			String newGender = in.nextLine();
			System.out.println("Email:   ");
			String newEmail = in.nextLine();
			//TODO increment ID number from last patient on database and change M or F into enum
		}
		public static void main (String args[])
		{
			
		}

}