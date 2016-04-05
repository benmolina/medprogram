package medProgram;

import java.util.Date;
import java.util.Scanner;
//import java.util.List;
public class Patient 
{
		String first,last,visit,status, doc;
		int ID, apptId;
		Date apttime;
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
		
		public Patient() {
			// TODO Auto-generated constructor stub
		}

		public void printPerson()
		{
	
		}
		
		public void getName()
		{
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