package com._Project.MySystem;

import com._Project.MySystem.model.*;
import com._Project.MySystem.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class MySystemApplication implements CommandLineRunner {

	private final ClientRepository clientRepository;

	private final InstructorRepository instructorRepository;

	private final OfferingRepository offeringRepository;

	private final LocationRepository locationRepository;

	private final ScheduleRepository scheduleRepository;

    private final AdminRepository adminRepository;

	private final BookingRepository bookingRepository;

	private final LessonRepository lessonRepository;

	public static void main(String[] args) {
		SpringApplication.run(MySystemApplication.class, args);
	}

	@Override

	public void run(String... args) throws Exception {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to our organization's website\n");
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("Press 1: Sign in");
			System.out.println("Press 2: Sign up");
			System.out.println("Press 3: View our available offerings");
			System.out.println("Press 4: Exit application");

			System.out.print("Enter your choice: ");
			int choice = Integer.parseInt(scan.nextLine());
			switch (choice) {
				case 1:
					signIn(scan);
					break;
				case 2:
					signUp(scan);
					break;
				case 3:
					getAllOfferingsWithInstructors();
					System.out.println("\nMust sign in as a client in order to make a booking ...\n");
					break;
				case 4:
					System.out.println("Thank you for using the application!");
					return;
				default:
					System.out.println("Invalid choice, try again");
					break;
			}
			System.out.println();
		}






	}
	public void signIn(Scanner scan) {
		System.out.println("What are you signing in as: ");
		System.out.println("Press 1: Client");
		System.out.println("Press 2: Instructor");
		System.out.println("Press 3: Admin");
		System.out.println("Press 4: Return");
		System.out.print("Enter your choice: ");
		int signInChoice = Integer.parseInt(scan.nextLine());

		switch (signInChoice) {
			case 1:
				Client client = signInAsClient(scan);
				if (client != null) {
					System.out.println("\nWelcome " + client.getFirstName());
					clientActionsMenu(client, scan);
				}
				break;
			case 2:
				Instructor instructor = signInAsInstructor(scan);
				if (instructor != null) {
					System.out.println("\nWelcome " + instructor.getFirstName());
					instructorActionsMenu(instructor,scan);
				}
				break;
			case 3:
				Admin admin = signInAsAdmin(scan);
				if (admin != null) {
					System.out.println("\nWelcome Admin...");
					adminActionsMenu(scan);
				}
				break;
			case 4:
				System.out.println("\nReturning to the main menu...\n");
				return;
			default:
				System.out.println("Invalid choice, try again");
				break;
		}
	}

	public void signUp(Scanner scan) {
		System.out.println("Sign up as: ");
		System.out.println("Press 1: Client");
		System.out.println("Press 2: Instructor");
		System.out.println("Press 3: Return to main menu");
		System.out.print("Enter your choice: ");
		int signUpChoice = Integer.parseInt(scan.nextLine());

		switch (signUpChoice) {
			case 1:
				Client client = signUpAsClient(scan);
				if (client != null) {
					System.out.println("Welcome, " + client.getFirstName() + "! Your client account has been created.");
					clientActionsMenu(client, scan);
				}
				break;
			case 2:
				Instructor instructor = signUpAsInstructor(scan);
				if (instructor != null) {
					System.out.println("Welcome, " + instructor.getFirstName() + "! Your instructor account has been created.");
					instructorActionsMenu(instructor,scan);
				}
				break;
			case 3:
				System.out.println("Returning to main menu...");
				return;
			default:
				System.out.println("Invalid choice, try again.");
				break;
		}
	}

	private Client signUpAsClient(Scanner scan) {
		System.out.print("\nEnter your first name: ");
		String firstName = scan.nextLine();
		System.out.print("Enter your last name: ");
		String lastName = scan.nextLine();
		System.out.print("Enter your phone number: ");
		String phoneNumber = scan.nextLine();
		System.out.print("Enter your email: ");
		String email = scan.nextLine();
		System.out.print("Enter your age: ");
		int age = Integer.parseInt(scan.nextLine());
		System.out.print("Enter your password: ");
		String password = scan.nextLine();

		Client guardian = null;
		if (age < 18) {
			System.out.print("\nYou are underage. A guardian account will also be created.\n\n");
			System.out.print("Enter guardian's first name: ");
			String guardianFirstName = scan.nextLine();
			System.out.print("Enter guardian's last name: ");
			String guardianLastName = scan.nextLine();
			System.out.print("Enter guardian's age: ");
			Integer guardianAge = Integer.parseInt(scan.nextLine());
			System.out.print("Enter guardian's phone number: ");
			String guardianPhoneNumber = scan.nextLine();
			System.out.print("Enter guardian's email: ");
			String guardianEmail = scan.nextLine();
			System.out.print("Enter guardian's password: ");
			String guardianPassword = scan.nextLine();

			guardian = new Client();
			guardian.setFirstName(guardianFirstName);
			guardian.setLastName(guardianLastName);
			guardian.setPhoneNumber(guardianPhoneNumber);
			guardian.setAge(guardianAge);
			guardian.setEmail(guardianEmail);
			guardian.setPassword(guardianPassword);
			guardian = clientRepository.save(guardian);
			System.out.println("\nGuardian account created successfully.\n");
		}

		Client client = new Client();
		client.setFirstName(firstName);
		client.setLastName(lastName);
		client.setPhoneNumber(phoneNumber);
		client.setAge(age);
		client.setEmail(email);
		client.setPassword(password);
		client.setGuardian(guardian);
		return clientRepository.save(client);
	}

	private Instructor signUpAsInstructor(Scanner scan) {
		System.out.print("Enter your first name: ");
		String firstName = scan.nextLine().trim();
		System.out.print("Enter your last name: ");
		String lastName = scan.nextLine().trim();
		System.out.print("Enter your phone number: ");
		String phoneNumber = scan.nextLine().trim();
		System.out.print("Enter your email: ");
		String email = scan.nextLine().trim();
		System.out.print("Enter your password: ");
		String password = scan.nextLine().trim();
		System.out.print("Enter your specialization: ");
		String specialization = scan.nextLine().trim();

		System.out.print("Enter the cities where you are available (comma-separated): ");
		String citiesInput = scan.nextLine().trim();
		List<String> availableCities = List.of(citiesInput.split(",\\s*")); // Split by comma and optional space


		Instructor instructor = new Instructor();
		instructor.setFirstName(firstName);
		instructor.setLastName(lastName);
		instructor.setPhoneNumber(phoneNumber);
		instructor.setEmail(email);
		instructor.setPassword(password);
		instructor.setSpecialization(specialization);
		instructor.setAvailableCities(availableCities);
		return instructorRepository.save(instructor);
	}

	public Client signInAsClient(Scanner scan) {
		System.out.println("\nPlease enter your sign in credentials");
		System.out.print("Enter your email: ");
		String email = scan.nextLine().trim();
		System.out.print("Enter your password: ");
		String password = scan.nextLine().trim();

		Client client = clientRepository.findByEmail(email).orElse(null);
		if (client != null && client.getPassword().equals(password)) {
			return client; // Return client object if credentials match
		} else {
			System.out.println("Invalid credentials for client.");
			return null;
		}
	}

	private void clientActionsMenu(Client client, Scanner scan) {

		while (true) {
			System.out.println("\n\nWhat would you like to do?");
			System.out.println("1: View your bookings");
			System.out.println("2: Cancel a booking");
			System.out.println("3: Make a new booking");
			System.out.println("4: Return to main menu");
			System.out.print("Enter your choice: ");
			int actionChoice = Integer.parseInt(scan.nextLine());

			switch (actionChoice) {
				case 1:
					viewBookings(client.getEmail());
					break;
				case 2:
					viewBookings(client.getEmail());
					deleteBooking(scan);
					break;
				case 3:
					viewBookings(client.getEmail());
					createBooking(client,scan);
					break;
				case 4:
					System.out.println("\n\n Returning to main menu...\n");
					return;
				default:
					System.out.println("\nInvalid choice, please try again.\n");
					break;
			}
		}
	}

	private void instructorActionsMenu(Instructor instructor, Scanner scan) {

		while (true) {
			System.out.println("\nWhat would you like to do?");
			System.out.println("1: View available offerings");
			System.out.println("2: Select an available offering");
			System.out.println("3: Cancel offering");
			System.out.println("4: View accepted offerings");
			System.out.println("5: Return to main menu");
			System.out.print("Enter your choice: ");
			int actionChoice = Integer.parseInt(scan.nextLine());

			switch (actionChoice) {
				case 1:
					viewAllOfferingsWithNoInstructors();
					break;
				case 2:
					selectLesson(instructor,scan);
					break;
				case 3:
					viewInstructorsOfferings(instructor);
					cancelLesson(instructor,scan);
					break;
				case 4:
					viewInstructorsOfferings(instructor);
					break;
				case 5:
					System.out.println("\n\n Returning to main menu...\n");
					return;
				default:
					System.out.println("\nInvalid choice, please try again.\n");
					break;
			}
		}
	}

	private void adminActionsMenu(Scanner scan) {

		while (true) {
			System.out.println("\nAdmin Actions:");
			System.out.println("1: Create a new offering");
			System.out.println("2: Delete a booking");
			System.out.println("3: Delete a client");
			System.out.println("4: Delete an instructor");
			System.out.println("5: Return to main menu");
			System.out.print("Enter your choice: ");
			int actionChoice = Integer.parseInt(scan.nextLine());

			switch (actionChoice) {
				case 1:
					createOffering(scan);
					break;
				case 2:
					deleteBooking(scan);
					break;
				case 3:
					deleteClient(scan);
					break;
				case 4:
					deleteInstructor(scan);
					break;
				case 5:
					System.out.println("\nReturning to main menu...\n");
					return; // Return to main menu
				default:
					System.out.println("Invalid choice, please try again.");
					break;
			}
		}
	}

	// Instructor sign-in method
	public Instructor signInAsInstructor(Scanner scan) {
		System.out.println("Please enter your sign in credentials");
		System.out.print("Enter your email: ");
		String email = scan.nextLine().trim();
		System.out.print("Enter your password: ");
		String password = scan.nextLine().trim();

		Instructor instructor = instructorRepository.findByEmail(email);
		if (instructor != null && instructor.getPassword().equals(password)) {
			return instructor; // Return instructor object if credentials match
		} else {
			System.out.println("Invalid credentials for instructor.");
			return null;
		}
	}

	// Admin sign-in method
	public Admin signInAsAdmin(Scanner scan) {
		System.out.println("Please enter your sign in credentials");
		System.out.print("Enter your email: ");
		String email = scan.nextLine().trim();
		System.out.print("Enter your password: ");
		String password = scan.nextLine().trim();

		// Admin hardcoded credentials check
		if (email.equals("admin@FakeEmail.com") && password.equals("admin")) {
			return adminRepository.findByEmail(email);
		} else {
			System.out.println("Invalid credentials for admin.");
			return null;
		}
	}

	public void viewBookings(String email) {

		// Step 2: Find the client by email
		Client client = clientRepository.findByEmail(email).orElse(null);  // Assuming there's a method to fetch the client by email

		// Step 3: Check if the client exists
		if (client == null) {
			System.out.println("No client found with the provided email.");
			return;
		}

		// Step 4: Check if the client has any bookings
		if (client.getBookings().isEmpty()) {
			System.out.println("This client has no bookings.");
			return;
		}

		// Step 5: Display the bookings in a nice format
		System.out.println("\nBookings for Client: " + client.getFirstName() + " " + client.getLastName());
		System.out.println("Email: " + client.getEmail());
		System.out.println("==========================================================================================================================================");

		// Print header for the table with consistent column widths
		System.out.printf("%-12s %-20s %-25s %-18s %-12s %-12s\n", "Booking ID", "Lesson Name", "Instructor Name", "Date", "Start Time", "End Time");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");

		for (Booking booking : client.getBookings()) {
			Lesson lesson = booking.getLesson();
			String instructorName = lesson.getInstructor().getFirstName() + " " + lesson.getInstructor().getLastName();

			System.out.printf("%-12d %-20s %-25s %-18s %-12s %-12s\n",
					booking.getId(),
					lesson.getName(),
					instructorName,
					booking.getStartTime(), // Assuming booking's startTime is a LocalDateTime
					booking.getStartTime(),
					booking.getEndTime());
		}

		System.out.println("============================================================================================================================================");
	}

	public void deleteBooking(Scanner scanner) {

		// Step 2: Ask the user to input the booking ID they wish to delete
		System.out.print("Enter the booking ID you would like to delete: ");
		Long bookingId = Long.parseLong(scanner.nextLine());

		Booking bookingToDelete = bookingRepository.findById(bookingId).orElse(null);

		if (bookingToDelete == null) {
			System.out.println("No booking found with the provided ID.");
			return;
		}

		// Step 5: Remove the booking from the client
		Client client = bookingToDelete.getClient();
		client.getBookings().remove(bookingToDelete);
		clientRepository.save(client); // Save the updated client information

		// Step 6: Set the associated lesson's availability back to true
		Lesson lesson = bookingToDelete.getLesson();
		lesson.setIsBooked(false);  // Mark the lesson as available again
		lessonRepository.save(lesson);  // Save the updated lesson information

		// Step 7: Delete the booking from the booking repository
		bookingRepository.delete(bookingToDelete);

		System.out.println("Booking with ID " + bookingId + " has been successfully deleted.");
	}




	public Booking createBooking(Client client, Scanner scanner) {
		getAllOfferingsWithInstructors();

		// Step 3: Prompt the client to select a lesson
		System.out.print("\n\nEnter the number of the lesson you want to book: ");
		Long lessonChoice = Long.parseLong(scanner.nextLine());

		Lesson selectedLesson = lessonRepository.findById(lessonChoice).get();

		// Step 4: Check if the lesson is already booked
		if (selectedLesson.getIsBooked()) {
			System.out.println("This lesson is already booked. Please select another lesson.");
			return null;
		}

		Booking booking = new Booking();
		booking.setClient(client);
		booking.setLesson(selectedLesson); // Associate the lesson's offering with the booking
		booking.setStartTime(selectedLesson.getStartTime());
		booking.setEndTime(selectedLesson.getEndTime());
		booking.setIsCancelled(false); // By default, booking is not cancelled

		booking = bookingRepository.save(booking);

		selectedLesson.setIsBooked(true);
		lessonRepository.save(selectedLesson);

		client.getBookings().add(booking);
		clientRepository.save(client);

		System.out.println("Booking created successfully for " + selectedLesson.getName() +
				" with " + selectedLesson.getInstructor().getFirstName() + " " + selectedLesson.getInstructor().getLastName());

		return booking;
	}


	public Offering createOffering(Scanner scanner) {
		// Create a new Offering instance
		Offering offering = new Offering();

		// Get type of lesson
		System.out.print("Enter the type of lesson (e.g., Yoga, Music): ");
		offering.setTypeOfLesson(scanner.nextLine().trim());

		// Set availability of the offering to true initially
		offering.setIsAvailable(true);

		// Create and save a new Schedule for the Offering
		Schedule schedule = new Schedule();

		System.out.print("Enter the start date (yyyy-mm-dd): ");
		schedule.setStartDate(LocalDate.parse(scanner.nextLine()));

		System.out.print("Enter the end date (yyyy-mm-dd): ");
		schedule.setEndDate(LocalDate.parse(scanner.nextLine()));

		System.out.print("Enter the day of the week (e.g., Monday): ");
		schedule.setDayOfWeek(scanner.nextLine().trim());

		System.out.print("Enter the start time (HH:mm, 24-hour format): ");
		schedule.setStartTime(LocalTime.parse(scanner.nextLine()));

		System.out.print("Enter the end time (HH:mm, 24-hour format): ");
		schedule.setEndTime(LocalTime.parse(scanner.nextLine()));

		// Save the schedule to the database and associate it with the offering
		schedule = scheduleRepository.save(schedule);
		offering.setSchedule(schedule);

		// Create and save a new Location for the Offering
		Location location = new Location();

		System.out.print("Enter the location name: ");
		location.setName(scanner.nextLine().trim());

		System.out.print("Enter the city: ");
		location.setCity(scanner.nextLine().trim());

		// Save the location to the database and associate it with the offering
		location = locationRepository.save(location);
		offering.setLocation(location);

		// Loop to add lessons to the offering and save each lesson
		List<Lesson> lessons = new ArrayList<>();
		String addMoreLessons;
		do {
			Lesson lesson = new Lesson();

			lesson.setName(offering.getTypeOfLesson().trim());

			System.out.print("Is this a private lesson? (yes/no): ");
			lesson.setIsPrivateLesson(scanner.nextLine().trim().equalsIgnoreCase("yes"));

			System.out.print("Enter lesson start time (HH:mm, 24-hour format): ");
			lesson.setStartTime(LocalTime.parse(scanner.nextLine()));

			System.out.print("Enter lesson end time (HH:mm, 24-hour format): ");
			lesson.setEndTime(LocalTime.parse(scanner.nextLine()));

			// Set the offering for the lesson and initial states
			lesson.setOffering(offering);
			lesson.setInstructorAssigned(false);
			lesson.setIsBooked(false);

			// Save the lesson to the database
			lesson = lessonRepository.save(lesson);
			lessons.add(lesson);

			System.out.print("Would you like to add another lesson? (yes/no): ");
			addMoreLessons = scanner.nextLine().trim();
		} while (addMoreLessons.equalsIgnoreCase("yes"));

		// Assign the lessons list to the offering
		offering.setLessons(lessons);

		// Check if offering should be available to the public based on assigned lessons
		offering.checkIfAvailableToPublic();

		// Display summary of created offering
		System.out.println("\nOffering created successfully with the following details:");
		System.out.println("Type of Lesson: " + offering.getTypeOfLesson());
		System.out.println("Location: " + offering.getLocation().getName() + " in " + offering.getLocation().getCity());
		System.out.println("Schedule: " + offering.getSchedule().getDayOfWeek() + " from " +
				offering.getSchedule().getStartTime() + " to " + offering.getSchedule().getEndTime());
		System.out.println("Lessons:");
		for (Lesson lesson : offering.getLessons()) {
			System.out.println("  - " + lesson.getName() + " (" + (lesson.getIsPrivateLesson() ? "Private" : "Group") +
					") from " + lesson.getStartTime() + " to " + lesson.getEndTime());
		}

		return offeringRepository.save(offering);
	}

	public void viewInstructorsOfferings(Instructor instructor) {
		for (Lesson lesson : instructor.getLessons()) {
			if (lesson.getInstructorAssigned()) {
				String lessonInfo = "Lesson ID: " + lesson.getId() +
						" | Time: " + lesson.getStartTime() + "-" + lesson.getEndTime() +
						" | " + (lesson.getIsPrivateLesson() ? "Private" : "Group") +
						" | Instructor: " + lesson.getInstructor().getFirstName() + " " + lesson.getInstructor().getLastName() +
						(lesson.getIsBooked() ? " (Unavailable)" : " (Available)");
				Offering offer = lesson.getOffering();
				if (offer != null) {
					lessonInfo += " | Day: " + offer.getSchedule().getDayOfWeek() +
							" | Location: " + offer.getLocation().getCity() +
							" | Where: " + offer.getLocation().getName();
				}

				System.out.println(lessonInfo);
			}
		}
	}
	public void cancelLesson(Instructor instructor, Scanner scanner) {
		System.out.print("\n\nSelect ID of lesson you would like to cancel: ");
		Long lesson_id = Long.parseLong(scanner.nextLine());

		Lesson lessonToRemove = null;
		for (Lesson lesson : instructor.getLessons()) {
			if (lesson.getId().equals(lesson_id)) {
				lessonToRemove = lesson;
				break;
			}
		}

		if (lessonToRemove == null) {
			System.out.println("No lesson found with ID " + lesson_id);
			return;
		}

		if (lessonToRemove.getIsBooked()) {
			Booking booking = bookingRepository.findByLesson(lessonToRemove);
			if (booking != null) {
				Client client = booking.getClient();
				if (client != null) {
					client.getBookings().remove(booking);
					clientRepository.save(client);
				}


				bookingRepository.delete(booking);
				System.out.println("Booking for lesson ID " + lesson_id + " has been cancelled.");
			}


		}
		lessonToRemove.setIsBooked(false);
		lessonToRemove.setInstructorAssigned(false);
		lessonToRemove.setInstructor(null);
		lessonRepository.save(lessonToRemove);
		instructor.getLessons().remove(lessonToRemove);
		instructorRepository.save(instructor);

		System.out.println("Lesson with ID " + lesson_id + " has been cancelled.");
	}


	public void selectLesson(Instructor instructor, Scanner scanner) {
		viewAllOfferingsWithNoInstructors();

		System.out.print("\n\nInput the ID of the lesson you would like to accept: ");
		Long lessonId;
		try {
			lessonId = Long.parseLong(scanner.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid input. Please enter a numeric lesson ID.");
			return;
		}

		Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
		if (!lessonOptional.isPresent()) {
			System.out.println("Lesson with ID " + lessonId + " not found. Please select a valid lesson.");
			return;
		}

		Lesson lesson = lessonOptional.get();

		if (!instructor.checkCities(lesson.getOffering().getLocation().getCity())) {
			System.out.println("\nLesson not given in instructor's city. Please select another lesson.");
			return;
		}

		if (lesson.getInstructorAssigned()) {
			System.out.println("An instructor has already been assigned to this lesson.");
			return;
		}

		for (Lesson existingLesson : instructor.getLessons()) {
			if (existingLesson.getOffering().getSchedule().getDayOfWeek().equals(lesson.getOffering().getSchedule().getDayOfWeek()) &&
					existingLesson.getStartTime().isBefore(lesson.getEndTime()) &&
					existingLesson.getEndTime().isAfter(lesson.getStartTime())) {
				System.out.println("This lesson conflicts with another lesson the instructor has scheduled at the same time.");
				return;
			}
		}

		lesson.assignInstructor(instructor, lesson.getOffering().getLocation().getCity());
		lessonRepository.save(lesson);
		instructor.getLessons().add(lesson);
		instructorRepository.save(instructor);

		System.out.println("Lesson successfully assigned to instructor.");
	}


	public void viewAllOfferingsWithNoInstructors() {
		List<Offering> offerings = offeringRepository.findAll();
		for (Offering offer : offerings) {
			System.out.println("\nWe offer private and group " + offer.getTypeOfLesson() + " in " + offer.getLocation().getName() +
					" on " + offer.getSchedule().getDayOfWeek() + "'s from " + offer.getSchedule().getStartTime() + " to " +
					offer.getSchedule().getEndTime() + " from " + offer.getSchedule().getStartDate() + " to " +
					offer.getSchedule().getEndDate() + " as follows:\n");

			for (Lesson lesson : offer.getLessons()) {
				if (!lesson.getInstructorAssigned()) {
					System.out.println("Lesson ID: " + lesson.getId() + " | Time: " + lesson.getStartTime() + "-" + lesson.getEndTime() +
							" | " + (lesson.getIsPrivateLesson() ? "Private" : "Group") +
							" | Instructor: Unassigned" +
							(lesson.getIsBooked() ? " (Unavailable)" : " (Available)"));
				}
			}
		}
	}


	public void getAllOfferingsWithInstructors() {
		List<Offering> offerings = offeringRepository.findAll();
		for (Offering offer : offerings) {
			boolean hasInstructor = offer.getLessons().stream().anyMatch(Lesson::getInstructorAssigned);

			if (hasInstructor) {
				System.out.println("\nWe offer private and group " + offer.getTypeOfLesson() + " in " + offer.getLocation().getName() +
						" on " + offer.getSchedule().getDayOfWeek() + "'s from " + offer.getSchedule().getStartTime() + " to " +
						offer.getSchedule().getEndTime() + " from " + offer.getSchedule().getStartDate() + " to " +
						offer.getSchedule().getEndDate() + " as follows:\n");

				for (Lesson lesson : offer.getLessons()) {
					if (lesson.getInstructorAssigned()) {
						System.out.println("Lesson ID: " + lesson.getId() + " | Time: " + lesson.getStartTime() + "-" + lesson.getEndTime() +
								" | " + (lesson.getIsPrivateLesson() ? "Private" : "Group") +
								" | Instructor: " + lesson.getInstructor().getFirstName() + " " + lesson.getInstructor().getLastName() +
								(lesson.getIsBooked() ? " (Unavailable)" : " (Available)"));
					}
				}
			}
		}
	}

	public void deleteInstructor(Scanner scan) {
		System.out.print("Enter the instructor email to delete: ");
		String instructorEmail = scan.nextLine().trim();

		Instructor instructor = instructorRepository.findByEmail(instructorEmail);
		if (instructor != null) {
			List<Booking> bookings = bookingRepository.findAll().stream()
					.filter(booking -> booking.getLesson().getInstructor() != null &&
							booking.getLesson().getInstructor().getId().equals(instructor.getId()))
					.toList();

			for (Booking booking : bookings) {
				Lesson lesson = booking.getLesson();
				lesson.setInstructor(null);
				lesson.setInstructorAssigned(false);
				lesson.setIsBooked(false);
				Client client = booking.getClient();
				if (client != null) {
					client.getBookings().remove(booking);
					clientRepository.save(client);
				}

				bookingRepository.delete(booking);
			}

			List<Lesson> instructorLessons = instructor.getLessons();
			for (Lesson lesson : instructorLessons) {
					lesson.setInstructor(null);
					lesson.setInstructorAssigned(false);
					lesson.setIsBooked(false);
					lessonRepository.save(lesson);
				}


			instructorRepository.delete(instructor);

			System.out.println("Instructor and their lessons have been updated.");
			return;
		}
		System.out.println("Instructor not found.");
		}


	public void deleteClient(Scanner scan) {
		System.out.print("Enter the client email to delete: ");
		String clientEmail = scan.nextLine().trim();

		// Find the client by email
		Optional<Client> clientOpt = clientRepository.findByEmail(clientEmail);

		if (clientOpt.isPresent()) {
			Client client = clientOpt.get();

			// Create a list to hold the bookings to be removed
			List<Booking> bookingsToDelete = new ArrayList<>(client.getBookings());

			// Iterate over the bookings and delete each one
			for (Booking booking : bookingsToDelete) {
				Lesson lesson = booking.getLesson();
				if (lesson != null) {
					lesson.setIsBooked(false);
					lessonRepository.save(lesson);
				}

				bookingRepository.delete(booking);
			}

			client.getBookings().clear();
			clientRepository.save(client);

			clientRepository.delete(client);

			System.out.println("Client and their bookings have been deleted.");
		} else {
			System.out.println("Client not found.");
		}
	}




}



