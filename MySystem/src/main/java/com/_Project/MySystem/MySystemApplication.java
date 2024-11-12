package com._Project.MySystem;

import com._Project.MySystem.model.*;
import com._Project.MySystem.repository.*;
import com._Project.MySystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class MySystemApplication implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private InstructorRepository instructorRepository;
	@Autowired
	OfferingRepository offeringRepository;
	@Autowired
	LocationRepository locationRepository;
	@Autowired
	ScheduleRepository scheduleRepository;
    @Autowired
    private AdminRepository adminRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private LessonRepository lessonRepository;

	public static void main(String[] args) {

//		Scanner scan = new Scanner(System.in);
//		System.out.println("Welcome to our organization's website\n");
//		while (true) {
//
//			System.out.println("What would you like to do?");
//			System.out.println("Press 1: Sign in");
//			System.out.println("Press 2: Sign up");
//			System.out.println("Press 3: View our available offerings");
//			System.out.println("Press 4: Exit application");
//
//			System.out.print("Enter your choice: ");
//			int choice = scan.nextInt();
//			switch(choice) {
//				case 1: //sign in
//					System.out.println("Please enter your sign in credentials");
//					//sign in logic
//					break;
//				case 2: //sign up
//					System.out.println("Thank you for wanting to sign up! Are you a client or instructor?");
//					System.out.println("1: Client");
//					System.out.println("2: Instructor");
//
//					System.out.print("Enter your choice: ");
//					int signUpChoice = scan.nextInt();
//
//					while (true) {
//						switch (signUpChoice) {
//							case 1: //Client Sign up --> do they want to have a guardian or not?
//								System.out.println("As a client, will you have a guardian or not?");
//								System.out.println("1: I will have a guardian");
//								System.out.println("2: I will NOT have a guardian");
//
//								System.out.print("Enter your choice: ");
//								int clientTypeChoice = scan.nextInt();
//
//								while (true) {
//									switch (clientTypeChoice) {
//										case 1: //Client sign up WITH guardian
//											//Logic for client sign up with guardian
//											return;
//										case 2: //Client sign up WITHOUT guardian
//											return;
//										default:
//											System.out.println("Invalid choice, try again");
//											break;
//									}
//									break;
//								}
//							case 2: //Instructor sign up
//								//Sign up logic for instructor
//								return;
//							default:
//								System.out.println("Invalid choice, try again");
//								break;
//						}
//						System.out.println();
//						break;
//					}
//				case 3: //view available offerings
//					break;
//				case 4: //exit
//					System.out.println("Thank you & Goodbye!");
//					return;
//				default:
//					System.out.println("Invalid choice, try again");
//					break;
//			}
//			System.out.println();
//		}
		SpringApplication.run(MySystemApplication.class, args);
		// can sign in
		// can sing up
		// sign up as client or instructor
		// sing up as client with guardian without guardian
		// can sign in with Admin account (have a username and password) that will then make it so you are using admin
		// view offerings


	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Admin admin = new Admin();
		admin.setEmail("admin@FakeEmail.com");
		admin.setPassword("admin");
		adminRepository.save(admin);  // Save admin first

		Client client = new Client();
		client.setFirstName("John");
		client.setLastName("Doe");
		client.setPhoneNumber("123-456-7890");
		client.setEmail("john.doe@example.com");
		client.setAge(30);
		client.setPassword("password123");

		client = clientRepository.save(client);

		Instructor instructor = new Instructor();
		instructor.setFirstName("John");
		instructor.setLastName("Doe");
		instructor.setPhoneNumber("123-456-7890");
		instructor.setSpecialization("Math");
		instructor.setEmail("john.doe@example.com");
		instructor.setPassword("instructor1");
		instructor.getAvailableCities().add("New York");
		instructor = instructorRepository.save(instructor);

		Location location = new Location();
		location.setName("Library");
		location.setCity("New York");
		location = locationRepository.save(location);

		Schedule schedule = new Schedule();
		schedule.setStartDate(LocalDate.now());
		schedule.setEndDate(LocalDate.now().plusWeeks(1));
		schedule.setDayOfWeek("Monday");
		schedule.setStartTime(LocalTime.of(10, 0));
		schedule.setEndTime(LocalTime.of(11, 0));
		schedule = scheduleRepository.save(schedule);

		Lesson lesson = new Lesson();
		lesson.setName("Math Lesson");
		lesson.setIsPrivateLesson(false);
		lesson.setStartTime(LocalTime.of(10, 0));
		lesson.setEndTime(LocalTime.of(11, 0));
		lesson = lessonRepository.save(lesson);

		Offering offering = new Offering();
		offering.setTypeOfLesson("Math");
		offering.setIsAvailable(false);
		offering.setInstructorApplied(false);
		offering.verifyAndSetInstructor(instructor,instructor.getSpecialization(),location.getCity());
		offering.setLocation(location);
		offering.setSchedule(schedule);
		offering.getLessons().add(lesson);

		offering = offeringRepository.save(offering);
		instructor.getOfferings().add(offering);
		instructorRepository.save(instructor);

		Booking booking = new Booking();
		booking.setStartTime(LocalTime.of(10, 0));
		booking.setEndTime(LocalTime.of(11, 0));
		booking.setIsCancelled(false);
		booking.setClient(client);
		booking.setOffering(offering);

		booking = bookingRepository.save(booking);

		client.getBookings().add(booking);
		client = clientRepository.save(client);

		System.out.println(instructorRepository.findByEmail("john.doe@example.com").getOfferings());

//		bookingRepository.delete(client.deleteBooking(booking));
//		client = clientRepository.save(client);
//		clientRepository.delete(client);
//		instructorRepository.delete(instructor);

	}


}
