package com._Project.MySystem;

import com._Project.MySystem.model.*;
import com._Project.MySystem.repository.ClientRepository;
import com._Project.MySystem.repository.InstructorRepository;
import com._Project.MySystem.repository.OfferingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class MySystemApplication implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InstructorRepository instructorRepository;
	@Autowired
	OfferingRepository offeringRepository;

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to our organization's website\n");
		while (true) {

			System.out.println("What would you like to do?");
			System.out.println("Press 1: Sign in");
			System.out.println("Press 2: Sign up");
			System.out.println("Press 3: View our available offerings");
			System.out.println("Press 4: Exit application");

			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();
			switch(choice) {
				case 1: //sign in
					System.out.println("Please enter your sign in credentials");
					//sign in logic
					break;
				case 2: //sign up
					System.out.println("Thank you for wanting to sign up! Are you a client or instructor?");
					System.out.println("1: Client");
					System.out.println("2: Instructor");

					System.out.print("Enter your choice: ");
					int signUpChoice = scan.nextInt();

					while (true) {
						switch (signUpChoice) {
							case 1: //Client Sign up --> do they want to have a guardian or not?
								System.out.println("As a client, will you have a guardian or not?");
								System.out.println("1: I will have a guardian");
								System.out.println("2: I will NOT have a guardian");

								System.out.print("Enter your choice: ");
								int clientTypeChoice = scan.nextInt();

								while (true) {
									switch (clientTypeChoice) {
										case 1: //Client sign up WITH guardian
											//Logic for client sign up with guardian
											return;
										case 2: //Client sign up WITHOUT guardian
											return;
										default:
											System.out.println("Invalid choice, try again");
											break;
									}
									break;
								}
							case 2: //Instructor sign up
								//Sign up logic for instructor
								return;
							default:
								System.out.println("Invalid choice, try again");
								break;
						}
						System.out.println();
						break;
					}
				case 3: //view available offerings
					break;
				case 4: //exit
					System.out.println("Thank you & Goodbye!");
					return;
				default:
					System.out.println("Invalid choice, try again");
					break;
			}
			System.out.println();
		}
		//SpringApplication.run(MySystemApplication.class, args);
		// can sign in
		// can sing up
		// sign up as client or instructor
		// sing up as client with guardian without guardian
		// can sign in with Admin account (have a username and password) that will then make it so you are using admin
		// view offerings


	}

	@Override
	public void run(String... args) throws Exception {
		Admin admin = new Admin();
		admin.setEmail("admin@FakeEmail.com");
		admin.setPassword("admin");
		Client noGuardianClient = new Client(
				"Jon",
				"Dan",
				"123-456-7891",
				"client1@FakeEmail.com",
				20,
				"client1"
		);

		Client guardian = new Client(
				"Jack",
				"Smith",
				"111-222-3333",
				"client3@FakeEmail.com",
				45,
				"client1"
		);

		Client clientWithGuardian = new Client(
				"Mary",
				"Smith",
				"222-333-4444",
				"client2@FakeEmail.com",
				12,
				"client1",
				guardian
		);
		clientRepository.save(guardian);
		clientRepository.save(noGuardianClient);
		clientRepository.save(clientWithGuardian);

		Instructor instructor1 = new Instructor("Alex", "Forg", "123-456-7890", "Yoga", "instructor1@FakeEmail.com", "password123");
		instructor1.getAvailableCities().add("Montreal");
		instructor1.getAvailableCities().add("Laval");
		instructor1.getAvailableCities().add("Sherbrooke");

		instructorRepository.save(instructor1);


		Location location = new Location();
		location.setName("Downtown Gym");
		location.setCity("Montreal");

		Schedule schedule = new Schedule();
		schedule.setStartDate(LocalDate.of(2024, 11, 1));
		schedule.setEndDate(LocalDate.of(2024, 12, 31));
		schedule.setDayOfWeek("Monday");
		schedule.setStartTime(LocalTime.of(10, 0));
		schedule.setEndTime(LocalTime.of(11, 0));

		Lesson lesson1 = new Lesson();
		lesson1.setName("Yoga");
		lesson1.setIsPrivate(false);
		lesson1.setStartTime(LocalTime.of(10, 0));
		lesson1.setEndTime(LocalTime.of(11, 0));

		Lesson lesson2 = new Lesson();
		lesson2.setName("Yoga");
		lesson2.setIsPrivate(true);
		lesson2.setStartTime(LocalTime.of(11, 0));
		lesson2.setEndTime(LocalTime.of(12, 0));

		List<Lesson> lessons = List.of(lesson1, lesson2);



		Offering offering1 = new Offering("Yoga", location, lessons, schedule, true);
		offering1.assignInstructor(instructor1);

		offeringRepository.save(offering1);


	}

}
