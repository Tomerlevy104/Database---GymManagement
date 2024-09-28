
import java.io.IOException;
import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.*;

public class Gym_Management_System {
	public static void main(String[] args) throws IOException, SQLException {
		Scanner sc = new Scanner(System.in);
		Connection con = createConnection("postgres", "1041998"); // Create connection to data base
		Statement stmt = con.createStatement();
		// Code For Interface...
		System.out.println("____________________________________________________________________________");
		System.out.println("                            WELCOME TO SYSTEM");
		System.out.println("____________________________________________________________________________");
		System.out.println("****************************************************************************");
		System.out.println("*********************  _______  *  _____  *****                           **");
		System.out.println("********************* |  _____| * |  ___| *****       **           **     **");
		System.out.println("********************* | |___    * | |     *****      ***___________***    **");
		System.out.println("********************* |  ___|   * | |     *****     ****-----------****   **");
		System.out.println("********************* | |       * | |___  *****      *** Stone Man ***    **");
		System.out.println("********************* |_|       * |_____| *****       **    GYM    **     **");
		System.out.println("*********************           *         *****                           **");
		System.out.println("************************  FITNESS CLUB  ************************************");
		System.out.println("****************************************************************************");
		System.out.println("*    *     *   WHEN LIFE GIVES YOU PAIN.....GO TO THE GYM   *    *    *    *");
		System.out.println("____________________________________________________________________________\n");
		login(con, stmt);
		stmt.close();
		con.close();

	}

	// ............................................Login Menu
	// Method....................................................
	public static void login(Connection con, Statement stmt) throws IOException, SQLException {
		Scanner sc = new Scanner(System.in);
		boolean isValidUserName = false;
		boolean isValidPass = false;
		boolean isValidUserNameAndPass = false;
		String U_name = null;
		String U_pass = null;
		System.out.println("Hello, please choose one of the following options:");
		System.out.println("\n1. Login as Admin");
		System.out.println("2. Add new Admin");
		System.out.println("0. Quit Program");
		System.out.print("Enter Your Choice: ");
		String cho = sc.next();
		while (true) {
			if (cho.equals("1") || cho.equals("2") || cho.equals("0"))
				break;
			else {
				System.out.println("\n\n\t\tInvalid Choice.....Please Choose between 0-2\n");
				System.out.print("Enter Your Choice: ");
				cho = sc.next();
			}
		}
		if (cho.equals("1")) { // ...............Login Option.............................

			System.out.println("____________________________________________________________________________");
			System.out.println("\n\t\t\t\t**********************");
			System.out.println("\t\t\t\t     SYSTEM LOGIN     ");
			System.out.println("\t\t\t\t  Login Into Account  ");
			System.out.println("\t\t\t\t**********************");

			do {
				do {
					System.out.print("\nEnter UserName: "); // ...Taking username from Admin....
					U_name = sc.next();
					if ((U_name.length() >= 4) && (U_name.length() <= 10)) { // username length between 4 to 10
						isValidUserName = true;
					} else {
						System.out.println("Error: Username must be 4-10 characters long");
					}
				} while (!isValidUserName);
				try {
					String SelectAdminQuery = "SELECT adminID, adminPass FROM adminTable WHERE adminID=?";
					PreparedStatement pstSelectAdmin = con.prepareStatement(SelectAdminQuery);
					pstSelectAdmin.setString(1, U_name);
					ResultSet rsAdmin = pstSelectAdmin.executeQuery();
					if (rsAdmin.next()) { // if user name exists in admin table
						do {
							System.out.print("\nEnter password: "); // ...Taking password from Admin....
							U_pass = sc.next();
							if ((U_pass.length() >= 4) && (U_pass.length() <= 10)) { // password length between 4 to 10
								if (U_pass.equals(rsAdmin.getString("adminPass"))) {
									isValidPass = true;
									isValidUserNameAndPass = true;
								} else {
									System.out.println("The password is incorrect");
								}
							} else {
								System.out.println("Error: Password must be 4-10 characters long");
							}
						} while (!isValidPass);
					} else {
						System.out.println("This username doesn't exist!");
					}
				} catch (SQLException e) {
					System.out.println("SQL Error: " + e.getMessage());
					e.printStackTrace();
				}
			} while (!isValidUserNameAndPass);

		} else if (cho.equals("2")) { // ...............Add Admin Option................
			System.out.println("\n\t\t\t\t\t*************************");
			System.out.println("\t\t\t\t\t         Add new admin");
			System.out.println("\t\t\t\t\t*************************");
			System.out.println("\n");

			do {
				System.out.print("\nEnter new UserName: "); // ...Taking username from Admin....
				U_name = sc.next();
				if ((U_name.length() >= 4) && (U_name.length() <= 10)) { // username length between 4 to 10
					isValidUserName = true;
				} else {
					System.out.println("Error: Username must be 4-10 characters long");
				}
			} while (!isValidUserName);

			do {
				System.out.print("\nEnter password: "); // ...Taking password from Admin....
				U_pass = sc.next();
				if ((U_pass.length() >= 4) && (U_pass.length() <= 10)) { // password length between 4 to 10
					isValidPass = true;
				} else {
					System.out.println("Error: Password must be 4-10 characters long");
				}
			} while (!isValidPass);

			try {
				String InsertAdminQuery = "INSERT INTO adminTable (adminID, adminPass) VALUES (?, ?);";
				PreparedStatement pstInsertAdmin = con.prepareStatement(InsertAdminQuery);
				pstInsertAdmin.setString(1, U_name);
				pstInsertAdmin.setString(2, U_pass);
				int rowsAffected = pstInsertAdmin.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println();
					System.out.println("\n\t\t\t\t**********************************************");
					System.out.println("\t\t\t\t  Username and Password added successfully ");
					System.out.print("\t\t\t\t**********************************************\n");
					System.out.println(
							"_________________________________________________________________________________________________________\n\n");
					login(con, stmt);// ...Line 148...
				}
			} catch (SQLException e) {
				System.out.println("SQL Error: " + e.getMessage());
				e.printStackTrace();
			}
		} else
			System.exit(1);
		System.out.println(
				"\n\n_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tAdmin Details");
		System.out.println(" Admins Name : " + " Adir, Omer, Tomer");
		System.out.println("\n GYM Timings: ");
		System.out.println("Morning Time: " + 5 + "am - " + 11 + "am");
		System.out.println("Evening Time: " + 4 + "pm - " + 11 + "pm");
		System.out.println(
				"_________________________________________________________________________________________________________");

		mainmenu(con, stmt);
	}

	// ...................................................Main Menu
	// Method..................................................
	public static void mainmenu(Connection con, Statement stmt) throws IOException, SQLException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n\t\t\t\t\t***************");
			System.out.println("\t\t\t\t\t** MAIN MENU **");
			System.out.println("\t\t\t\t\t***************\n");
			System.out.println("1. Manage Member");
			System.out.println("2. Manage Trainer");
			System.out.println("3. Manage Machines");
			System.out.println("4. Manage Booking");
			System.out.println("0. Logout");
			System.out.println("00.Exit");
			System.out.print("\nEnter Your Choice: ");
			String cho = sc.next();
			while (true) {
				if (cho.equals("1") || cho.equals("2") || cho.equals("3") || cho.equals("4") || cho.equals("0")
						|| cho.equals("00"))
					break;
				else {
					System.out.println("\nInvalid Choice.....Please Choose between 0-4 or 00 \n");
					System.out.print("Enter Your Choice: ");
					cho = sc.next();
				}
			}
			if (cho.equals("00")) {
				System.exit(1);
			} else if (cho.equals("0")) {
				System.out.println("\n");
				System.out.println("\t\t\t\t\t****************");
				System.out.println("\t\t\t\t\t     LOGOUT     ");
				System.out.println("\t\t\t\t\t Have a nice Day");
				System.out.println("\t\t\t\t\t****************\n\n\n");
				login(con, stmt);
				break;
			}
//.....................................................................................Members...
			else if (cho.equals("1")) {
				while (true) {
					System.out.println("\n\t\t\t\t\t***************");
					System.out.println("\t\t\t\t\t*** OPTIONS ***");
					System.out.println("\t\t\t\t\t***************\n\n");
					System.out.println("1- Details of Members");
					System.out.println("2- View All Members");
					System.out.println("3- Modify Members");
					System.out.println("4- Add new Member");
					System.out.println("5- Delete Member");
					System.out.println("6- Back To Main Menu");
					System.out.print("Enter choice from (1 - 6): ");
					String choic = sc.next();
					if (choic.equals("1"))
						DetailsOfMember(con, stmt);
					else if (choic.equals("2"))
						View_Members(con, stmt);
					else if (choic.equals("3")) {
						modify_Members(con, stmt);
					} else if (choic.equals("4")) {
						add_new_members(con, stmt);
					} else if (choic.equals("5"))
						delete_Members(con, stmt);
					else if (choic.equals("6")) {
						mainmenu(con, stmt);
						break;
					} else {
						System.out.println();
						System.out.println("\n\t\t\t\tWrong Choice! Select choice from (1-6)");
					}
				}
			}
//.........................................................................Trainers...
			else if (cho.equals("2")) {
				while (true) {
					System.out.println();
					System.out.println("\t\t\t\t\t***************");
					System.out.println("\t\t\t\t\t*** OPTIONS ***");
					System.out.println("\t\t\t\t\t***************");
					System.out.println("\n1- View All Trainers");
					System.out.println("2- Detail of Trainer");
					System.out.println("3- Add Trainer");

					System.out.println("4- Delete Trainer");
					System.out.println("5- Back to Main Menu");
					System.out.print("Enter choice from(1 - 5): ");
					String choic = sc.next();
					if (choic.equals("1"))
						View_Trainers(con);
					else if (choic.equals("2"))
						Trainers_detail(con, stmt);
					else if (choic.equals("3")) {
						Add_Trainer(con, stmt);
					} else if (choic.equals("4")) {
						delete_Trainers(con, stmt);
					} else if (choic.equals("5")) {
						mainmenu(con, stmt);
						break;
					} else {
						System.out.println();
						System.out.println("\n\t\t\t\tWrong Choice! Choice should be from (1-5)");
					}
				}
			}
//...............................................................................Machines...
			else if (cho.equals("3")) {
				while (true) {
					System.out.println();
					System.out.println("\n\t\t\t\t\t***************");
					System.out.println("\t\t\t\t\t*** OPTIONS ***");
					System.out.println("\t\t\t\t\t***************\n");
					System.out.println("1- Details of a Machine");
					System.out.println("2- View All Machines");
					System.out.println("3- Add new Machine");
					System.out.println("4- Delete Machine");
					System.out.println("5- Back to Main Menu");
					System.out.print("Enter choice from(1 - 5): ");
					String choic = sc.next();
					if (choic.equals("1"))
						DetailsOfMachines(con, stmt);
					else if (choic.equals("2"))
						View_Machines(stmt);
					else if (choic.equals("3")) {
						add_new_machines(stmt);
					} else if (choic.equals("4"))
						delete_Machines(stmt);
					else if (choic.equals("5")) {
						mainmenu(con, stmt);
						break;
					} else {
						System.out.println();
						System.out.println("\n\t\t\t\tWrong Choice! Choice should be from (1-5)");
					}
				}
			}
//.......................................................................................Booking...
			else if (cho.equals("4")) {
				while (true) {
					System.out.println("\n\n\t\t\t\t\t***************");
					System.out.println("\t\t\t\t\t*** OPTIONS ***");
					System.out.println("\t\t\t\t\t***************\n");
					System.out.println("1. Book a machine ");
					System.out.println("2. Cancel Booking ");
					System.out.println("3. Booking Details");
					System.out.println("4. Morning Time Reservations ");
					System.out.println("5. Evening Time Reservations ");
					System.out.println("6. Reset Reservations ");
					System.out.println("7. Back to Main Menu ");
					System.out.print("\nEnter choice from(1-7): ");
					String choic = sc.next();
					while (true) {
						if (!(choic.equals("1") || choic.equals("2") || choic.equals("3") || choic.equals("4")
								|| choic.equals("5") || choic.equals("6") || choic.equals("7"))) {
							System.out.println("\n\t\t\t\tWrong Choice! Select choice from (1-7)\n");
							System.out.print("Enter Choice (1-7): ");
							choic = sc.next();
						} else
							break;
					}
					if (choic.equals("1")) {
						bookMachine(con, stmt);
					} else if (choic.equals("2")) {
						cancelBooking(con, stmt);
					} else if (choic.equals("3")) {
						bookingDetail(con, stmt);
					} else if (choic.equals("4")) {
						morningBookingInfo(con);
					} else if (choic.equals("5")) {
						eveningBookingInfo(con);
					} else if (choic.equals("6")) {
						resetBooking(con, stmt);
					} else if (choic.equals("7")) {
						mainmenu(con, stmt);
						break;
					}
				}
			}
		}
	}

	// ..................................................Method to Show Details of a
	// Member.................................
	public static void DetailsOfMember(Connection con, Statement stmt) throws IOException, SQLException {
		System.out.println("\n"
				+ "_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tDetails Of Members \n\n");
		Scanner input = new Scanner(System.in);
		boolean okID = false;
		String memberID;
		do {
			System.out.println("Enter member ID: ");
			System.out.println("OR 'Y' for Main Menu");
			memberID = input.next();
			okID = isValidID(con, stmt, memberID);
			if (okID) {
				ResultSet rsMember = stmt
						.executeQuery("SELECT (memberID) FROM membersTable WHERE memberID = '" + memberID + "'");
				if (rsMember.next()) {
					System.out.println(
							"\n_________________________________________________________________________________________________________");
					System.out.println();
					System.out.println("\t\t\t\t\t* Data of " + rsMember.getString("memberID") + " *");
					System.out.println(
							"_________________________________________________________________________________________________________\n");
					System.out.println("\n-> Member ID: " + rsMember.getString("memberID"));
					rsMember.close();
					// Get the personal details from personsTable
					ResultSet rsPerson = stmt.executeQuery(
							"SELECT personID, firstName, lastName, dateOfBirth, gender, contactNumber FROM personsTable WHERE personID = '"
									+ memberID + "'");
					if (rsPerson.next()) {
						System.out.println("\n-> First Name: " + rsPerson.getString("firstName"));
						System.out.println("\n-> Last Name: " + rsPerson.getString("lastName"));
						System.out.println("\n-> Date Of Birth: " + rsPerson.getString("dateOfBirth"));
						System.out.println("\n-> Gender: " + rsPerson.getString("gender"));
						System.out.println("\n-> Contact Number: " + rsPerson.getString("contactNumber"));
					}
					rsPerson.close();
					ResultSet rsMember2 = stmt.executeQuery(
							"SELECT memberID, weight, height, programID, trainerID, workoutSession, MemberShip FROM membersTable WHERE memberID = '"
									+ memberID + "'");
					if (rsMember2.next()) {
						// Get the member's fitness details from membersTable
						System.out.println("\n-> Weight: " + rsMember2.getBigDecimal("weight"));
						System.out.println("\n-> Height: " + rsMember2.getBigDecimal("height") + " Meter");
						System.out.println("\n-> Session: " + rsMember2.getString("workoutSession"));
					}
					rsMember2.close();
					// Get the fitness program name
					ResultSet rsProgram = stmt.executeQuery(
							"SELECT programName FROM programTable JOIN membersTable ON programTable.programID = membersTable.programID WHERE membersTable.memberID = '"
									+ memberID + "'");
					if (rsProgram.next()) {
						System.out.println("\n-> Fitness Program: " + rsProgram.getString("programName"));
					}
					rsProgram.close();
				} else {
					System.out.print(
							"\nMember not found. Enter 'Y' for main menu or Again enter registration number for details:  \n\n");
					okID = false;
				}
			}
		} while (!okID);
	}

	// ................................................Method to View All
	// Members...........................................
	public static void View_Members(Connection con, Statement stmt) throws SQLException {
		ResultSet rsMember = stmt
				.executeQuery("SELECT memberID, workoutSession, personsTable.firstName, personsTable.lastName "
						+ "FROM membersTable " + "JOIN personsTable ON membersTable.memberID = personsTable.personID");

		StringBuilder output = new StringBuilder();
		output.append(
				"\n_________________________________________________________________________________________________________\n");
		output.append("   Member ID       |   Sessions             |        Names\n");
		output.append(
				"___________________|________________________|_________________________________________________________________\n");

		int d = 1;
		while (rsMember.next()) {
			output.append(String.format("%-3d %-15s| \t%-20s|\t%s %s\n", d++, rsMember.getString("memberID"),
					rsMember.getString("workoutSession"), rsMember.getString("firstName"),
					rsMember.getString("lastName")));
		}
		output.append(
				"___________________|________________________|_________________________________________________________________\n");
		if (d > 1) {
			System.out.println(output.toString());
		} else {
			System.out.println("No members found.");
		}
	}

	// ...........................................Method to Modify The
	// Members..........................................
	public static void modify_Members(Connection con, Statement stmt) throws IOException, SQLException {
		boolean isOkID = false;
		String modifyChoice = null;
		System.out.println(
				"\n_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tModify Members");
		System.out.print("\nEnter Member ID: ");
		do {
			Scanner input = new Scanner(System.in);
			String memberID = input.next();
			isOkID = isValidID(con, stmt, memberID);
			if (isOkID) {
				String sql = "SELECT personID, firstName, lastName, dateOfBirth, gender, contactNumber, weight, height, programID, trainerID, workoutSession "
						+ "FROM personsTable JOIN membersTable ON personsTable.personID = membersTable.memberID "
						+ "WHERE personID = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, memberID);
				ResultSet rsMember = pstmt.executeQuery();

				if (rsMember.next()) {
					System.out.println("\t\t\t\t* Modify the Details of " + rsMember.getString("firstName") + " "
							+ rsMember.getString("lastName") + " *");
					System.out.println("\nWhat do you want to modify: ");

					do {
						System.out.println("1- Name ");
						System.out.println("2- Last Name ");
						System.out.println("3- Weight ");
						System.out.println("4- Height ");
						System.out.println("5- Trainer ");
						System.out.println("6- Contact Number ");
						System.out.println("7- Program ");
						System.out.println("8- Session ");
						System.out.println("Y- Main Menu");
						System.out.print("Enter choice from (1-8) or 'Y' for Main Menu: ");
						modifyChoice = input.next();
						if (modifyChoice.equalsIgnoreCase("y")) {
							mainmenu(con, stmt);
							break;
						}

					} while (!(modifyChoice.equals("1") || modifyChoice.equals("2") || modifyChoice.equals("3")
							|| modifyChoice.equals("4") || modifyChoice.equals("5") || modifyChoice.equals("6")
							|| modifyChoice.equals("7") || modifyChoice.equals("8")
							|| modifyChoice.equalsIgnoreCase("y")));

					switch (modifyChoice) {
					// modify first name
					case "1": {
						System.out.print("\nEnter new first name: ");
						input.nextLine();
						String newFirstName = input.nextLine();
						newFirstName = validate_name(newFirstName);
						stmt.executeUpdate("UPDATE personsTable SET firstName = '" + newFirstName
								+ "' WHERE personID = '" + memberID + "'");
						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tName Updated Successfully! \n\n");
						break;
					}

					// modify last name
					case "2": {
						System.out.print("\nEnter new last name: ");
						input.nextLine();
						String newLastName = input.nextLine();
						newLastName = validate_name(newLastName);
						stmt.executeUpdate("UPDATE personsTable SET lastName = '" + newLastName + "' WHERE personID = '"
								+ memberID + "'");
						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tLast Name Updated Successfully! \n\n");
						break;
					}
					// modify weight
					case "3": {
						double updatedWeight = InputforHeightAndWeight(modifyChoice);
						stmt.executeUpdate("UPDATE membersTable SET weight = '" + updatedWeight + "' WHERE memberID = '"
								+ memberID + "'");
						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tWeight Updated Successfully! \n\n");
						break;
					}
					// modify height
					case "4": {
						double updatedHeight = InputforHeightAndWeight(modifyChoice);
						stmt.executeUpdate("UPDATE membersTable SET height = '" + updatedHeight + "' WHERE memberID = '"
								+ memberID + "'");
						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tHeight Updated Successfully! \n\n");
						break;
					}

					// modify trainer
					case "5": {
						int j = 1;
						boolean isOkIDTrainer = false;
						if (input.hasNextLine()) {
							input.nextLine(); // cleaner buffer
						}
						try {
							// Display available trainers with headers
							String query = "SELECT personID, firstName, lastName, gender " + "FROM personsTable "
									+ "JOIN trainersTable ON personsTable.personID=trainersTable.trainerID";
							stmt = con.createStatement();
							ResultSet rsTrainer = stmt.executeQuery(query);
							System.out.println("\n\nOptional trainers: ");
							System.out.printf("%-5s%-15s%-15s%-10s%n", "No.", "ID", "First Name", "Last Name",
									"Gender");
							System.out.println("-----------------------------------------------------");
							while (rsTrainer.next()) {
								System.out.printf("%-5d%-15s%-15s%-10s%n", j, rsTrainer.getString("personID"),
										rsTrainer.getString("firstName"), rsTrainer.getString("lastName"),
										rsTrainer.getString("gender"));
								j++;
							}
							rsTrainer.close();

							do {
								System.out.print("\nEnter the ID of the trainer that you want: ");
								String newTrainerID = input.nextLine();

								isOkIDTrainer = isValidID(con, stmt, newTrainerID); // Validate
								if (isOkIDTrainer) {
									// Check if the trainer exists
									String trainerCheckQuery = "SELECT personID, firstName, lastName, gender "
											+ "FROM personsTable "
											+ "JOIN trainersTable ON personsTable.personID=trainersTable.trainerID "
											+ "WHERE personID = ?";
									PreparedStatement pstTrainerCheck = con.prepareStatement(trainerCheckQuery);
									pstTrainerCheck.setString(1, newTrainerID);
									ResultSet rsChosenTrainer = pstTrainerCheck.executeQuery();
									if (rsChosenTrainer.next()) {
										// Update the member's trainer
										String updateQuery = "UPDATE membersTable SET trainerID = ? WHERE memberID = ?";
										PreparedStatement pstUpdate = con.prepareStatement(updateQuery);
										pstUpdate.setString(1, newTrainerID);
										pstUpdate.setString(2, memberID);
										pstUpdate.executeUpdate();
										System.out.println(
												"_________________________________________________________________________________________________________");
										System.out.println("\t\t\t\tTrainer Updated Successfully! \n\n");
									} else {
										System.out.println("Trainer not found.");
										isOkIDTrainer = false;
									}

									rsChosenTrainer.close();
									pstTrainerCheck.close();
								} else {
									System.out.println("Invalid trainer ID. Please try again.");
								}
							} while (!isOkIDTrainer);

						} catch (Exception e) {
							e.printStackTrace();
						}
						break;
					}

					// modify contact number
					case "6": {
						System.out.print("\nEnter contact Number - 10 digits (05XXXXXXXX): ");
						String contact_num = input.next();
						contact_num = validatePhoneNumber(contact_num);
						stmt.executeUpdate("UPDATE personsTable SET contactNumber = '" + contact_num
								+ "' WHERE personID = '" + memberID + "'");
						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tContact Number Updated Successfully! \n\n");
						break;
					}
					// modify program
					case "7": {
						boolean isOkProgramID = false;
						ResultSet rsProgram = stmt.executeQuery("SELECT programID, programName FROM programTable");
						System.out.println("\n\nOptional programs:\n ");
						System.out.printf("%-5s %-20s%n", "ID", "ProgramName");
						System.out.println("------------------------------");

						while (rsProgram.next()) {
							System.out.printf("%-5d", rsProgram.getInt("programID"));
							System.out.printf("%-20s%n", rsProgram.getString("programName"));
						}
						rsProgram.close();
						do {
							System.out.println("\nSelect the ID of the new fitness Program");
							if (!input.hasNextInt()) {
								System.out.println("Error: The Program ID must be a number. Please try again.");
								input.next(); // eater buffer
							} else {
								int newProgramID = input.nextInt();

								ResultSet rsChosenProgram = stmt.executeQuery(
										"SELECT programID, programName FROM programTable WHERE programID= '"
												+ newProgramID + "'");
								if (rsChosenProgram.next()) {
									stmt.executeUpdate("UPDATE membersTable SET programID = '" + newProgramID
											+ "' WHERE memberID = '" + memberID + "'");
									isOkProgramID = true;
								} else {
									System.out.println("Program not found.");
									isOkProgramID = false;
								}
							}
						} while (!isOkProgramID);

						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tFitness Program Updated Successfully! \n\n");
						break;
					}
					// modify session
					case "8": {
						String Morning = "Morning";
						String Evening = "Evening";
						System.out.println("\nSelect Workout Session : ");
						System.out.println("1- Morning");
						System.out.println("2- Evening");
						System.out.print("Enter Your Choice: ");
						String ch = input.next();
						while (ch.equals("1") == false && ch.equals("2") == false) {
							System.out.print("\nInvalid choice. Choose between 1-2: ");
							ch = input.next();
						}
						if (ch.equals("1")) {
							stmt.executeUpdate("UPDATE membersTable SET workoutSession = '" + Morning
									+ "' WHERE memberID = '" + memberID + "'");
						} else if (ch.equals("2")) {
							stmt.executeUpdate("UPDATE membersTable SET workoutSessios = '" + Evening
									+ "' WHERE memberID = '" + memberID + "'");
						}
						System.out.println(
								"_________________________________________________________________________________________________________");
						System.out.println("\t\t\t\tWorkout Session Updated Successfully! \n\n");
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + modifyChoice);
					}

				} else {
					System.out.println("Member not founded! Please enter member ID again: ");
					isOkID = false;
				}
			}

		} while (!isOkID);
	}

	// ................................................Method to Add New
	// Member.............................................
	public static boolean add_new_members(Connection con, Statement stmt) throws IOException, SQLException {

		Scanner input = new Scanner(System.in);
		String memberID;
		boolean isOkID = false;
		System.out.println(
				"_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tAdding New Member\n\n");
		do {
			System.out.println("Please enter member ID (9 digits):");
			memberID = input.nextLine();
			isOkID = isValidID(con, stmt, memberID);
		} while (!isOkID);

		System.out.print("Enter the first name of Member: ");
		String firstName = input.nextLine();
		firstName = validate_name(firstName);
		System.out.print("\nEnter the last name of Member: ");
		String lastName = input.nextLine();
		lastName = validate_name(lastName);
		System.out.print("\nEnter Date Of Birth (DD-MM-YYYY): ");
		String dateOfBirth;
		int date;
		int month;
		int year;
		int count;
		// Validation for Maintaining Format (DD-MM-YYYY) in Input....................
		while (true) {
			dateOfBirth = input.next();
			if (!(dateOfBirth.length() == 10 && dateOfBirth.charAt(2) == '-' && dateOfBirth.charAt(5) == '-')) {
				System.out.println("\nIt appears that you entered wrong format.Please enter as (DD-MM-YYYY) ");
				continue;
			} else {
				count = 0;
				for (int i = 0; i < dateOfBirth.length(); i++) {
					if (!(Character.isDigit(dateOfBirth.charAt(i)))) {
						if (i == 2 || i == 5) {
							continue;
						}
						System.out.println("\nIt appears that you entered wrong format.Please enter only digits ");
						count++;
						break;
					}
				}
				if (count != 0) {
					continue;
				}
			}
			date = Integer.parseInt(dateOfBirth.substring(0, 2));
			month = Integer.parseInt(dateOfBirth.substring(3, 5));
			year = Integer.parseInt(dateOfBirth.substring(6, 10));
			// Controlling Values be normal...........................................
			if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
				if (month == 2) {
					if (date > 29 || date <= 0) {
						System.out.println(
								"\nIt appears that you entered wrong date for Month (" + month + "),Try again ");
						continue;
					}
				}
			} else {
				if (month == 2) {
					if (date > 28 || date <= 0) {
						System.out.println(
								"\nIt appears that you entered wrong date for Month (" + month + "),Try again ");
						continue;
					}
				}
			}

			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				if (date > 31 || date <= 0) {
					System.out.println("\nIt appears that you entered wrong date for Month (" + month + "),Try again ");
					continue;
				}
			}
			if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (date > 30 || date <= 0) {
					System.out.println("\nIt appears that you entered wrong date for Month (" + month + "),Try again ");
					continue;
				}
			}
			if (month > 12 || month <= 0) {
				System.out.println("\nIt appears that you entered wrong month in Date of birth,Try again ");
				continue;
			}
			if (year > 2024 || year < 1922) {
				System.out.println("\nIt appears that you entered wrong year in Date of birth,Try again ");
				continue;
			}
			break;
		}

		// gender
		System.out.println("\n\nSelect Gender ");
		System.out.println("1- Male");
		System.out.println("2- Female");
		System.out.println("3- Other");
		String genderChoice = input.next();
		while (genderChoice.equals("1") == false && genderChoice.equals("2") == false
				&& genderChoice.equals("3") == false) {
			System.out.println("\n\t\t\t\tWrong choice! Please select valid choice\n");
			System.out.print("Select choice from 1-3: ");
			genderChoice = input.next();
		}
		if (genderChoice.equals("1") == true) {
			genderChoice = "M";
		} else if (genderChoice.equals("2") == true) {
			genderChoice = "F";
		} else {
			genderChoice = "O";
		}

		// Weight
		String choiceforWeight = "3";
		double memberWeight = InputforHeightAndWeight(choiceforWeight);
		BigDecimal memberWeight2 = BigDecimal.valueOf(memberWeight);

		// Height
		String choiceforHeight = "4";
		double memberHeight = InputforHeightAndWeight(choiceforHeight);
		BigDecimal memberHeight2 = BigDecimal.valueOf(memberHeight);

		// program
		PreparedStatement pst = con.prepareStatement("SELECT * FROM programTable;");
		ResultSet rsProgram = pst.executeQuery();
		System.out.println("\nSelect your fitness Program:");
		// Print column headers
		System.out.println("-------------------------------");
		System.out.printf("%-10s %-20s\n", "Program ID", "Program Name");
		System.out.println("-------------------------------");

		while (rsProgram.next()) {
			System.out.printf("%-10d %-20s\n", rsProgram.getInt("programID"), rsProgram.getString("programName"));
		}

		System.out.println("");
		int chosenProgramID;
		while (true) {
			System.out.print("Enter the ID of the program that you want: ");
			if (input.hasNextInt()) {
				chosenProgramID = input.nextInt();
				input.nextLine();
				PreparedStatement pstChosenProgram = con
						.prepareStatement("SELECT programID, programName FROM programTable WHERE programID=?");
				pstChosenProgram.setInt(1, chosenProgramID);
				ResultSet rsProgram2 = pstChosenProgram.executeQuery();
				if (rsProgram2.next()) {
					break;
				} else {
					System.out.println("Program not found. Please enter a valid program ID.");
				}
				rsProgram2.close();
				pstChosenProgram.close();
			} else {
				System.out.println("Invalid input. Please enter an ID of program.");
				input.nextLine();
			}
		}

		// Contact number
		System.out.print("\nEnter Contact Number - 10 digits (Format ==> 05X-XXXXXXX): ");
		String Phone = input.next();
		Phone = validatePhoneNumber(Phone);

		// Trainer
		System.out.println("\n\nNeed Trainer? ");
		System.out.print("Press 'Y' for Yes and 'N' for No: ");
		String c;

		if (input.hasNextLine()) {
			input.nextLine(); // Clean buffer
		}

		c = input.nextLine();
		String chosenTrainerID = null;
		while (true) {
			if (c.equalsIgnoreCase("Y") || c.equalsIgnoreCase("N")) {
				break;
			} else {
				System.out.println("\nInvalid. Choice must be Y/N \n");
				System.out.print("Enter choice as 'Y' or 'N': ");
				c = input.nextLine();
			}
		}

		if (c.equalsIgnoreCase("Y")) {
			PreparedStatement pstTrainer = con.prepareStatement(
					"SELECT personID, firstName, lastName, gender FROM personsTable JOIN trainersTable ON personID=trainerID");
			ResultSet rsTrainer = pstTrainer.executeQuery();

			System.out.println("\n\nFollowing Trainers are Available: \n");

			// Print column headers
			System.out.printf("%-5s %-10s %-10s %-10s %-6s\n", "No.", "ID", "First Name", "Last Name", "Gender");
			System.out.println("-----------------------------------------------------");

			int j = 1;
			while (rsTrainer.next()) {
				System.out.printf("%-5d %-10s %-10s %-10s %-6s\n", j, rsTrainer.getString("personID"),
						rsTrainer.getString("firstName"), rsTrainer.getString("lastName"),
						rsTrainer.getString("gender"));
				j++;
			}
			boolean isOkTID = false;
			while (!isOkTID) {
				System.out.print("Enter the ID of the trainer that you want: ");
				chosenTrainerID = input.nextLine();
				isOkTID = isValidID(con, stmt, chosenTrainerID);
				if (isOkTID) {
					PreparedStatement pstChosenTrainer = con
							.prepareStatement("SELECT trainerID FROM trainersTable WHERE trainerID = ?");
					pstChosenTrainer.setString(1, chosenTrainerID);
					ResultSet rsTrainer2 = pstChosenTrainer.executeQuery();
					if (rsTrainer2.next()) {
						isOkTID = true;
					} else {
						System.out.println("Trainer ID not found. Please enter a valid ID.");
						isOkTID = false;
					}
					rsTrainer2.close();
					pstChosenTrainer.close();
				} else {
					System.out.println("Invalid ID format. Please enter a valid ID consisting of numbers only.");
				}
			}
			rsTrainer.close();
			pstTrainer.close();
		} else {
			chosenTrainerID = "NULL";
		}

		// Session
		System.out.println("\nSelect Workout Session : ");
		System.out.println("1- Morning");
		System.out.println("2- Evening");
		System.out.print("Enter the choice(1-2): ");
		String chosenSession = input.next();
		while (chosenSession.equals("1") == false && chosenSession.equals("2") == false) {
			System.out.println("\nInvalid choice. Please Select the valid choice \n");
			System.out.print("Select the choice(1-2): ");
			chosenSession = input.next();
		}
		if (chosenSession.equals("1")) {
			chosenSession = "Morning";
		} else if (chosenSession.equals("2")) {
			chosenSession = "Evening";
		}

		if (input.hasNextLine()) {
			input.nextLine(); // Clean buffer
		}
		// Membership
		String chosenMemberShip = null;
		System.out.println();
		if (c.equalsIgnoreCase("N")) {
			System.out.println("MemberShip Rates without Trainer");
			System.out.println("1- One Year (1,800 NIS)");
			System.out.println("2- Six Month (1,000 NIS)");
			System.out.println("3- Three Month (600 NIS)");
			System.out.println("4- One Month (250 NIS)");
			System.out.print("Select Your Choice: ");
			chosenMemberShip = input.nextLine();
			while (!chosenMemberShip.equals("1") && !chosenMemberShip.equals("2") && !chosenMemberShip.equals("3")
					&& !chosenMemberShip.equals("4")) {
				System.out.println("\nInvalid choice. Please select a valid choice \n");
				System.out.print("Select the choice (1-4): ");
				chosenMemberShip = input.nextLine();
			}

			switch (chosenMemberShip) {
			case "1":
				chosenMemberShip = "One Year (1,800 NIS)";
				break;
			case "2":
				chosenMemberShip = "Six Month (1,000 NIS)";
				break;
			case "3":
				chosenMemberShip = "Three Month (600 NIS)";
				break;
			case "4":
				chosenMemberShip = "One Month (250 NIS)";
				break;
			}
		}

		if (c.equalsIgnoreCase("Y")) {
			System.out.println("MemberShip Rates with Trainer ");
			System.out.println("1- One Year (2,000 NIS)");
			System.out.println("2- Six Month (1,200 NIS)");
			System.out.println("3- Three Month (700 NIS)");
			System.out.println("4- One Month (400 NIS)");
			System.out.print("Select Your Choice: ");
			chosenMemberShip = input.nextLine();
			while (!chosenMemberShip.equals("1") && !chosenMemberShip.equals("2") && !chosenMemberShip.equals("3")
					&& !chosenMemberShip.equals("4")) {
				System.out.println("\nInvalid choice. Please Select a valid choice \n");
				System.out.print("Select the choice(1-4): ");
				chosenMemberShip = input.nextLine();
			}

			switch (chosenMemberShip) {
			case "1":
				chosenMemberShip = "One Year (2,000 NIS)";
				break;
			case "2":
				chosenMemberShip = "Six Month (1,200 NIS)";
				break;
			case "3":
				chosenMemberShip = "Three Month (700 NIS)";
				break;
			case "4":
				chosenMemberShip = "One Month (400 NIS)";
				break;
			}
		}

		String insertPersonQuery = "INSERT INTO personsTable (personID, firstName, lastName, dateOfBirth, gender, contactNumber) VALUES (?, ?, ?, ?, ?, ?)";
		String insertMemberQuery = "INSERT INTO membersTable (memberID, weight, height, programID, trainerID, workoutSession, MemberShip) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement pstInsertPerson = null;
		PreparedStatement pstInsertMember = null;
		boolean isError = false;
		try {
			con.setAutoCommit(false); // Start transaction

			// Insert to person table
			pstInsertPerson = con.prepareStatement(insertPersonQuery);
			pstInsertPerson.setString(1, memberID);
			pstInsertPerson.setString(2, firstName);
			pstInsertPerson.setString(3, lastName);
			pstInsertPerson.setString(4, dateOfBirth);
			pstInsertPerson.setString(5, genderChoice);
			pstInsertPerson.setString(6, Phone);
			pstInsertPerson.executeUpdate();

			// Insert to member table
			pstInsertMember = con.prepareStatement(insertMemberQuery);
			pstInsertMember.setString(1, memberID);
			pstInsertMember.setBigDecimal(2, memberWeight2);
			pstInsertMember.setBigDecimal(3, memberHeight2);
			pstInsertMember.setInt(4, chosenProgramID);
			pstInsertMember.setString(5, chosenTrainerID);
			pstInsertMember.setString(6, chosenSession);
			pstInsertMember.setString(7, chosenMemberShip);
			pstInsertMember.executeUpdate();

			con.commit(); // Commit transaction

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
			isError = true;
			if (con != null) {
				try {
					con.rollback(); // If error- cancel the transaction
				} catch (SQLException ex) {
					System.out.println("Error during rollback: " + ex.getMessage());
				}
			}
		} finally {
			try {
				if (pstInsertPerson != null)
					pstInsertPerson.close();
				if (pstInsertMember != null)
					pstInsertMember.close();
			} catch (SQLException e) {
				System.out.println("Error closing resources: " + e.getMessage());
			}
		}
		if (!isError) {
			System.out.println("\n");
			System.out.println("\t\t\t\tMember Added Successfully");
			System.out.println(
					"_________________________________________________________________________________________________________");
		}

		return true;
	}

	// .......................................Method to Delete
	// Members......................................................
	public static void delete_Members(Connection con, Statement stmt) throws IOException, SQLException {
		Scanner input = new Scanner(System.in);
		boolean isOkID = false;
		String memberIDToDelete = null;

		do {
			System.out.print("\n\nEnter the ID of the member to delete: ");
			memberIDToDelete = input.next();

			// Validate the ID
			isOkID = isValidID(con, stmt, memberIDToDelete);

			if (isOkID) {
				try {
					// Start transaction
					con.setAutoCommit(false);

					String deleteMemberFromBookingQuery = "DELETE FROM bookingTable WHERE memberID = ?";
					PreparedStatement pstDeleteMemberFromBooking = con.prepareStatement(deleteMemberFromBookingQuery);
					pstDeleteMemberFromBooking.setString(1, memberIDToDelete);
					pstDeleteMemberFromBooking.executeUpdate();

					// First, delete from membersTable
					String deleteMemberQuery = "DELETE FROM membersTable WHERE memberID = ?";
					PreparedStatement pstDeleteMember = con.prepareStatement(deleteMemberQuery);
					pstDeleteMember.setString(1, memberIDToDelete);
					int rowsAffectedMember = pstDeleteMember.executeUpdate();
					if (rowsAffectedMember > 0) {// if success to delete from member table
						// Then, delete from personsTable
						String deletePersonQuery = "DELETE FROM personsTable WHERE personID = ?";
						PreparedStatement pstDeletePerson = con.prepareStatement(deletePersonQuery);
						pstDeletePerson.setString(1, memberIDToDelete);
						int rowsAffectedPerson = pstDeletePerson.executeUpdate();

						if (rowsAffectedPerson > 0) {// if success to delete from person table and member table
							System.out.println("\nMembership removed successfully");
							System.out.println(
									"_________________________________________________________________________________________________________\n");
							isOkID = true;
						} else {
							System.out.println("\nPerson not found.");
							// Rollback transaction if person not found
							con.rollback();
						}

						// Commit transaction if both deletions were successful
						con.commit();

						// Close resources
						pstDeletePerson.close();
					} else {
						System.out.println("\nMember not found.");
						// Rollback transaction if member not found
						con.rollback();
					}

					// Close resources
					pstDeleteMember.close();
				} catch (Exception e) {
					try {
						// Rollback transaction in case of an exception
						if (con != null) {
							con.rollback();
						}
					} catch (Exception rollbackEx) {
						rollbackEx.printStackTrace();
					}
					e.printStackTrace();
				} finally {
					try {
						// Set auto-commit back to true
						if (con != null) {
							con.setAutoCommit(true);
						}
					} catch (Exception autoCommitEx) {
						autoCommitEx.printStackTrace();
					}
				}
			} else {
				System.out.println("Invalid ID or ID does not exist. Please try again.");
			}
		} while (!isOkID);
	}

	// .............................................Method To View All
	// Trainers.............................................
	public static void View_Trainers(Connection con) throws SQLException { // accept only Connection
																			// and work with
																			// PreparedStatement
		PreparedStatement pst = con
				.prepareStatement("SELECT trainerstable.trainerid, personstable.firstname, personstable.lastname "
						+ "									  FROM trainerstable JOIN personstable ON trainerstable.trainerid = personstable.personid");
		ResultSet rsTrainers = pst.executeQuery();

		StringBuilder output = new StringBuilder();
		output.append("\n_____________________________________________________________________________________\n");
		output.append("   Trainer's ID    |        Name\n");
		output.append("___________________|_________________________________________________________________\n");

		int d = 1;
		while (rsTrainers.next()) {
			String firstName = rsTrainers.getString("firstName");
			String lastName = rsTrainers.getString("lastName");
			int ID = rsTrainers.getInt("trainerID");
			output.append(String.format("%-3d %-15d| \t%s %s\n", d++, ID, firstName, lastName));
		}
		output.append("___________________|_________________________________________________________________\n");
		if (d > 1) {
			System.out.println(output.toString());
		} else {
			System.out.println("No trainers found.");
		}

		System.out.println(
				"_________________________________________________________________________________________________________\n");
	}

	// .............................................Method For Details of a
	// Trainer.........................................
	public static void Trainers_detail(Connection con, Statement stmt) throws IOException, SQLException {
		Scanner sc = new Scanner(System.in);
		System.out.println(
				"_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tTrainers' Details");
		boolean okID = false;
		System.out.print("\n\nEnter the ID of the trainer or 'Y' to return : ");
		do {
			String name = sc.nextLine();
			okID = isValidID(con, stmt, name);

			if (okID) {
				PreparedStatement pst = con.prepareStatement("SELECT personstable.firstname, personstable.lastname, "
						+ "personstable.gender, personstable.contactNumber FROM personstable JOIN trainerstable ON personstable.personid = ?");

				pst.setString(1, name);

				ResultSet rsTrainer = pst.executeQuery();

				if (rsTrainer.next()) {
					String firstName = rsTrainer.getString("firstname");
					String lastName = rsTrainer.getString("lastname");
					String gender = rsTrainer.getString("gender");
					String contactNumber = rsTrainer.getString("contactnumber");

					System.out.println(
							"\n_________________________________________________________________________________________________________");
					System.out.println("\t\t\t\t\tDetails of " + firstName + " " + lastName);
					System.out.println("\n-> Trainer's ID: " + name);
					System.out.println("-> Trainer's Name: " + firstName + " " + lastName);
					System.out.println("-> Gender: " + gender);
					System.out.println("-> Contact Number: " + contactNumber);
				} else {
					System.out.print(
							"\nTrainer not found. Enter 'Y' for main menu or Again enter registration number for details:  \n\n");
					okID = false;
				}

				pst = con.prepareStatement("SELECT personsTable.firstName, personsTable.lastName FROM membersTable "
						+ "JOIN personsTable ON membersTable.memberID = personsTable.personID "
						+ "WHERE membersTable.trainerID = ?");

				pst.setString(1, name);

				ResultSet rsMembers = pst.executeQuery();

				System.out.println("-> Members:");
				int count = 0;
				while (rsMembers.next()) {
					count++;
					String firstName = rsMembers.getString("firstname");
					String lastName = rsMembers.getString("lastname");
					System.out.println("   " + count + "-" + firstName + " " + lastName);
				}
				if (count == 0)
					System.out.println("   Trainer has No Members....");
			}

		} while (!okID);
		System.out.println(
				"_________________________________________________________________________________________________________");
	}

	// .............................................Method to Add
	// Trainers..................................................
	public static void Add_Trainer(Connection con, Statement stmt) throws SQLException, IOException {
		Scanner input = new Scanner(System.in);
		String trainerID;
		boolean isOkID = false;

		// Prompt for trainer's ID
		System.out.println("_");
		System.out.println("\t\t\t\t\tAdding New Trainer\n\n");
		do {
			System.out.print("Please enter trainer ID (up to 9 characters): ");
			trainerID = input.nextLine();
			isOkID = isValidID(con, stmt, trainerID);
		} while (!isOkID);

		// Check if the trainerID exists in the personsTable
		String checkPersonQuery = "SELECT personID FROM personsTable WHERE personID = ?";
		try (PreparedStatement checkPersonStmt = con.prepareStatement(checkPersonQuery)) {
			checkPersonStmt.setString(1, trainerID);
			ResultSet rs = checkPersonStmt.executeQuery();

			// If the person does not exist, prompt for additional person details
			if (!rs.next()) {
				System.out.println(
						"Trainer ID does not exist in personsTable. Please enter the details to add a new person.");

				// Prompt for personal details
				System.out.print("Enter the first name of the Trainer: ");
				String firstName = input.nextLine();
				firstName = validate_name(firstName); // Ensure valid name

				System.out.print("Enter the last name of the Trainer: ");
				String lastName = input.nextLine();
				lastName = validate_name(lastName); // Ensure valid name

				System.out.print("Enter Date Of Birth (DD-MM-YYYY): ");
				String dateOfBirthStr = validateDateOfBirth(input); // Validate date format

				System.out.println("Select Gender ");
				System.out.println("1- Male");
				System.out.println("2- Female");
				System.out.println("3- Other");
				String genderChoice = input.next();
				while (!genderChoice.equals("1") && !genderChoice.equals("2") && !genderChoice.equals("3")) {
					System.out.println("Invalid choice! Please select 1 for Male, 2 for Female, or 3 for Other.");
					genderChoice = input.next();
				}
				genderChoice = genderChoice.equals("1") ? "M" : (genderChoice.equals("2") ? "F" : "O");

				System.out.print("\nEnter Contact Number - 10 digits (Format ==> 05X-XXXXXXX): ");
				String phone = input.next();
				phone = validatePhoneNumber(phone); // Validate phone number

				// Insert the new person into personsTable
				String insertPersonQuery = "INSERT INTO personsTable (personID, firstName, lastName, dateOfBirth, gender, contactNumber) VALUES (?, ?, ?, ?, ?, ?)";
				try (PreparedStatement insertPersonStmt = con.prepareStatement(insertPersonQuery)) {
					insertPersonStmt.setString(1, trainerID);
					insertPersonStmt.setString(2, firstName);
					insertPersonStmt.setString(3, lastName);
					insertPersonStmt.setString(4, dateOfBirthStr);
					insertPersonStmt.setString(5, genderChoice);
					insertPersonStmt.setString(6, phone);
					insertPersonStmt.executeUpdate();
				}
				// Prompt for trainer's salary
				System.out.print("Enter the salary of the Trainer: ");
				double salary = input.nextDouble();
				input.nextLine(); // Clean buffer

				// Ensure the salary is a valid positive number
				while (salary < 0) {
					System.out.print("Salary must be a positive number. Please enter again: ");
					salary = input.nextDouble();
					input.nextLine(); // Clean buffer
				}

				// Insert the trainer into trainersTable
				String insertTrainerQuery = "INSERT INTO trainersTable (trainerID, salary) VALUES (?, ?)";
				try (PreparedStatement insertTrainerStmt = con.prepareStatement(insertTrainerQuery)) {
					insertTrainerStmt.setString(1, trainerID);
					insertTrainerStmt.setDouble(2, salary);
					insertTrainerStmt.executeUpdate();
				}

				System.out.println("\n\t\t\t\tTrainer Added Successfully");
				System.out.println("_");
			} else {
				System.out.println("It is not possible for a member to also be a trainer");
			}

		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	// ..............................................Method to Delete
	// Trainers..............................................
	public static void delete_Trainers(Connection con, Statement stmt) throws IOException, SQLException {

		Scanner input = new Scanner(System.in);
		boolean isOkID = false;
		String trainerIDToDelete = null;

		do {
			System.out.print("\n\nEnter the ID of the trainer to delete: ");
			trainerIDToDelete = input.next();

			// Validate the ID
			isOkID = isValidID(con, stmt, trainerIDToDelete);

			if (isOkID) {
				try {
					// Start transaction
					con.setAutoCommit(false);

					// Update all the members with this trainer and set NULL for them
					String updateMemberQuery = "UPDATE membersTable SET trainerID = NULL WHERE trainerID = ?;";
					PreparedStatement pstUpdateMember = con.prepareStatement(updateMemberQuery);
					pstUpdateMember.setString(1, trainerIDToDelete);
					pstUpdateMember.executeUpdate();

					// First, delete from trainersTable
					String deleteTrainerFromMemberTableQuery = "DELETE FROM membersTable WHERE memberID = ?";
					PreparedStatement pstDeleteTrainerFromMemberTable = con
							.prepareStatement(deleteTrainerFromMemberTableQuery);
					pstDeleteTrainerFromMemberTable.setString(1, trainerIDToDelete);

					String deleteTrainerQuery = "DELETE FROM trainersTable WHERE trainerID = ?";
					PreparedStatement pstDeleteTrainer = con.prepareStatement(deleteTrainerQuery);
					pstDeleteTrainer.setString(1, trainerIDToDelete);
					int rowsAffectedTrainer = pstDeleteTrainer.executeUpdate();

					if (rowsAffectedTrainer > 0) {// if success to delete from trainers table
						// Then, delete from personsTable
						String deletePersonQuery = "DELETE FROM personsTable WHERE personID = ?";
						PreparedStatement pstDeletePerson = con.prepareStatement(deletePersonQuery);
						pstDeletePerson.setString(1, trainerIDToDelete);
						int rowsAffectedPerson = pstDeletePerson.executeUpdate();

						if (rowsAffectedPerson > 0) {// if success to delete from person table and trainer table

							System.out.println("\nTrainer removed successfully");
							System.out.println(
									"_________________________________________________________________________________________________________\n");
							isOkID = true;
						} else {
							System.out.println("\nPerson not found.");
							// Rollback transaction if person not found
							con.rollback();
						}

						// Commit transaction if both deletions were successful
						con.commit();

						// Close resources
						pstDeletePerson.close();
					} else {
						System.out.println("\nTrainer not found.");
						// Rollback transaction if Trainer not found
						con.rollback();
					}

					// Close resources
					pstDeleteTrainer.close();
				} catch (Exception e) {
					try {
						// Rollback transaction in case of an exception
						if (con != null) {
							con.rollback();
						}
					} catch (Exception rollbackEx) {
						rollbackEx.printStackTrace();
					}
					e.printStackTrace();
				} finally {
					try {
						// Set auto-commit back to true
						if (con != null) {
							con.setAutoCommit(true);
						}
					} catch (Exception autoCommitEx) {
						autoCommitEx.printStackTrace();
					}
				}
			} else {
				System.out.println("Invalid ID or ID does not exist. Please try again.");
			}
		} while (!isOkID);
	}

	// ..................................................Method For Details of
	// Machine......................................
	public static void DetailsOfMachines(Connection con, Statement stmt) throws IOException, SQLException {
		ResultSet rs = null;
		System.out.println(
				"_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tDetails Of A Machine ");
		boolean flag = false;
		String Sn;
		do {
			System.out.println("\n\nEnter Serial Number (XXXX Format) ");
			System.out.print("(OR 'Y' for Main Menu: ");
			Scanner input = new Scanner(System.in);
			Sn = input.next();
			Sn = validate_regNo(con, stmt, Sn);
			rs = stmt.executeQuery(
					"SELECT machineID, machineName, maxWeight, exercise FROM machinesTable WHERE machineID = '" + Sn
							+ "'");
			if (rs.next())
				flag = true;
			else {
				System.out.println("\nMachine not found. Enter 'Y' for Main Menu or Serial number for details:  ");
			}
		} while (!flag);

		System.out.println(
				"\n_________________________________________________________________________________________________________");
		System.out.println();
		System.out.println("\t\t\t\t Details of machine number: " + rs.getInt("machineID"));
		System.out.println(
				"_________________________________________________________________________________________________________\n");
		System.out.println("-> Serial Number: " + rs.getInt("machineID"));
		System.out.println("-> Machine Name: " + rs.getString("machineName"));
		System.out.println("-> Weight: " + rs.getDouble("maxWeight") + " KG");
		System.out.println("-> Exercise: " + rs.getString("exercise"));
		System.out.println(
				"_________________________________________________________________________________________________________\n");
	}

	// .............................................Method to View All
	// Machines.............................................
//.....................................Method view all machines ...................................
	public static void View_Machines(Statement stmt) throws SQLException {

		System.out.println(
				"_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tList Of Machines  ");
		System.out.println(
				"_________________________________________________________________________________________________________");
		System.out.println("\nSerial No." + "\t  " + "\tNames\n");
		ResultSet rs = stmt.executeQuery("SELECT machineID, machineName FROM machinesTable");
		while (rs.next()) {
			System.out.println(rs.getInt("machineID") + "\t\t| \t" + rs.getString("machineName"));
		}
		System.out.println(
				"_________________________________________________________________________________________________________");
	}

	// ...................................................Method to Add New
	// Machines........................................
	public static boolean add_new_machines(Statement stmt) throws SQLException {
		ResultSet rs = null;
		Scanner input = new Scanner(System.in);
		rs = stmt.executeQuery("SELECT machineID FROM machinesTable");
		String name;
		double tempWeight;
		String selectedExerciseStr;
		System.out.println(
				"_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\tAdd New Machine\n\n");
		System.out.print("\nEnter the name of Machine: ");
		name = input.nextLine();
		String choiceforWeight = "3";
		tempWeight = InputforHeightAndWeight(choiceforWeight);
		enum Exercise {
			CHEST("Chest"), BACK("Back"), BICEP("Bicep"), TRICEPS("Triceps"), ABS("ABS"), SHOULDERS("Shoulders"),
			LEGS("Legs"), OTHERS("Others");

			private final String name;

			Exercise(String name) {
				this.name = name;
			}

			public String getName() {
				return name;
			}
		}
		System.out.println("\nExercise : ");
		for (Exercise exercise : Exercise.values()) {
			System.out.println((exercise.ordinal() + 1) + "- " + exercise.getName());
		}
		String choice2 = input.next();
		while (!choice2.matches("[1-8]")) {
			System.out.println("\n\t\t\t\tWrong choice! Select Valid Choice");
			System.out.print("\nChoose from 1-8: ");
			choice2 = input.next();
		}
		Exercise selectedExercise = Exercise.values()[Integer.parseInt(choice2) - 1];
		selectedExerciseStr = selectedExercise.getName();
		stmt.executeUpdate("INSERT INTO machinesTable (machineName, maxweight, exercise) VALUES ('" + name + "', "
				+ tempWeight + ", '" + selectedExerciseStr + "')");
		System.out.println("\t\t\t\t   Machine Added Successfully");
		System.out.println(
				"_________________________________________________________________________________________________________\n");
		return true;
	}

	// ...............................................Method to Delete
	// Machines.............................................
	public static void delete_Machines(Statement stmt) throws IOException, SQLException {
		ResultSet rs = null;
		Scanner input = new Scanner(System.in);
		System.out.print("\n\nEnter the machine's ID to delete Machine: ");
		String str = input.nextLine();
		int tempMachineID = Integer.parseInt(str);
		rs = stmt.executeQuery("SELECT machineID FROM machinesTable WHERE machineID = '" + tempMachineID + "'");

		if (!rs.next()) {
			System.out.println("\nMachine not found!");
			System.out.println(
					"_________________________________________________________________________________________________________\n");
		} else {
			System.out.print("\nMachine found\nDo you want to Delete (Y/N): ");
			while (true) {
				System.out.print("Enter Choice (Y/N): ");
				String choice = input.next();
				if (choice.equalsIgnoreCase("Y")) {
					rs.close();
					stmt.executeUpdate("DELETE FROM machinesTable WHERE machineID='" + tempMachineID + "'");
					System.out.println("\nMachine removed successfully!");
					System.out.println(
							"_________________________________________________________________________________________________________\n");
					break;
				} else if (choice.equalsIgnoreCase("N")) {
					return;
				} else {
					System.out.println("\nIncorrect. Input must be Y or N \n");
				}
			}
		}
		rs.close();
	}

	// .......................................................Method to Book
	// Machines.......................................
	public static void bookMachine(Connection con, Statement stmt) throws IOException, SQLException {

		Scanner input = new Scanner(System.in);
		ResultSet rsSelectMember = null;
		boolean isOkID = false;
		boolean loop = true;
		String memberIDToAddBooking = null;
		String machine_num = null;

		do {
			System.out.print("\n\nEnter the ID of the member to add booking: ");
			memberIDToAddBooking = input.nextLine().trim(); // Read the entire line and trim any extra spaces
			isOkID = isValidID(con, stmt, memberIDToAddBooking); // Validate the ID

			if (isOkID) {
				String memberIDToAddBookingQuery = "SELECT workoutSession FROM membersTable WHERE memberID = ?";
				PreparedStatement pstfoudMember = con.prepareStatement(memberIDToAddBookingQuery);
				pstfoudMember.setString(1, memberIDToAddBooking);
				rsSelectMember = pstfoudMember.executeQuery();

				if (rsSelectMember.next()) {
					View_Machines(stmt);

					while (loop) {
						System.out.println(
								"Enter Serial number of machine (You Want) from list OR 'Y' to return to the Main Menu: ");
						machine_num = input.nextLine().trim(); // Read the entire line and trim any extra spaces

						if (machine_num.equalsIgnoreCase("Y")) {
							mainmenu(con, stmt);
							break;
						}

						// Validate the machine number
						machine_num = validate_regNo(con, stmt, machine_num);

						boolean isValid = validateMachineNum(con, machine_num);

						if (isValid) {
							// Check if the booking already exists
							String checkBookingQuery = "SELECT COUNT(*) FROM bookingTable WHERE memberID = ? AND machineID = ?";
							PreparedStatement pstmtCheck = con.prepareStatement(checkBookingQuery);
							pstmtCheck.setString(1, memberIDToAddBooking);
							pstmtCheck.setInt(2, Integer.parseInt(machine_num));
							ResultSet rsCheck = pstmtCheck.executeQuery();

							if (rsCheck.next() && rsCheck.getInt(1) > 0) {
								// Booking already exists
								System.out.println("This machine is already booked by the member.");
							} else {
								// Insert the new booking
								String query = "INSERT INTO bookingTable (memberID, machineID, workoutSession) VALUES (?, ?, ?)";
								PreparedStatement pstmt = con.prepareStatement(query);
								pstmt.setString(1, memberIDToAddBooking);
								pstmt.setInt(2, Integer.parseInt(machine_num));
								pstmt.setString(3, rsSelectMember.getString("workoutSession"));
								pstmt.executeUpdate();
								System.out.println("Booking added to database.");
								loop = false;
							}
							rsCheck.close();
						} else {
							System.out.println("Machine number does not exist.");
						}
					}
				} else {
					System.out.println("Member not found. Please try again.");
					isOkID = false;
				}
			} else {
				System.out.println("Invalid ID or ID does not exist. Please try again.");
			}

		} while (!isOkID);
	}

	// ..............................................Method for Canceling
	// Booking..........................................
	public static void cancelBooking(Connection con, Statement stmt) throws IOException, SQLException {

		Scanner input = new Scanner(System.in);
		ResultSet rsSelectMember = null;
		boolean isOkID = false;
		boolean loop = true;
		String memberIDToCancelBooking = null;
		String machine_num = null;

		do {
			System.out.print("\n\nEnter the ID of the member to cancel booking: ");
			memberIDToCancelBooking = input.next();

			// Validate the ID
			isOkID = isValidID(con, stmt, memberIDToCancelBooking);

			if (isOkID) {
				String memberIDToCancelBookingQuery = "SELECT memberID FROM bookingTable WHERE memberID = ?";
				PreparedStatement pstBookingMember = con.prepareStatement(memberIDToCancelBookingQuery);
				pstBookingMember.setString(1, memberIDToCancelBooking);
				rsSelectMember = pstBookingMember.executeQuery();

				if (rsSelectMember.next()) {

					// Display the member's bookings

					View_Bookings(con, memberIDToCancelBooking);

					// Clean buffer
					if (input.hasNextLine()) {
						input.nextLine();
					}
					while (loop) {
						System.out.print(
								"Enter the ID of the machine to cancel booking or 'Y' to return to the Main Menu: ");

						machine_num = input.nextLine();

						if (machine_num.equalsIgnoreCase("Y")) {
							mainmenu(con, stmt);
							break;
						}

						boolean isValid = validateMachineNum(con, machine_num);

						if (isValid) {
							// Cancel the booking
							String cancelBookingQuery = "DELETE FROM bookingTable WHERE memberID = ? AND machineID = ?";
							PreparedStatement pstmt = con.prepareStatement(cancelBookingQuery);
							pstmt.setString(1, memberIDToCancelBooking);
							pstmt.setInt(2, Integer.parseInt(machine_num));
							int rowsAffected = pstmt.executeUpdate();

							if (rowsAffected > 0) {
								System.out.println("Booking successfully canceled.");
								loop = false;
							} else {
								System.out.println("No matching booking found for this machine.");
							}

						} else {
							System.out.println("Machine number does not exist.");
						}
					}
				} else {
					System.out.println("Member not found. Please try again.");
					isOkID = false;
				}
			} else {
				System.out.println("Invalid ID or ID does not exist. Please try again.");
			}
		} while (!isOkID);
	}

	// ...................................................Method for Booking
	// Details....................................
	public static void bookingDetail(Connection con, Statement stmt) throws IOException, SQLException {
		Scanner input = new Scanner(System.in);
		System.out.println(
				"\n\n_________________________________________________________________________________________________________");
		System.out.println("\t\t\t\t\t Reservation Details\n");
		System.out.println();
		int bookingID;
		boolean isOkBookingID = false;
		String SelectBookingIDQuery = null;
		ResultSet rsBookingID = null;

		do {
			System.out.print("\n\nEnter the ID of the booking: ");
			bookingID = input.nextInt();
			String query = "SELECT COUNT(*) FROM bookingTable WHERE bookingID = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, bookingID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				isOkBookingID = count > 0;
			}

			if (isOkBookingID) {
				SelectBookingIDQuery = "SELECT \r\n" + "    b.bookingID,\r\n" + "    m.machineName,\r\n"
						+ "    p.firstName,\r\n" + "    p.lastName,\r\n" + "    p.personID\r\n" + "FROM \r\n"
						+ "    bookingTable b\r\n" + "JOIN \r\n"
						+ "    membersTable mem ON b.memberID = mem.memberID\r\n" + "JOIN \r\n"
						+ "    personsTable p ON mem.memberID = p.personID\r\n" + "JOIN \r\n"
						+ "    machinesTable m ON b.machineID = m.machineID\r\n" + "WHERE \r\n"
						+ "    b.bookingID = ? ;" + "";

				PreparedStatement pstSelectBookingID = con.prepareStatement(SelectBookingIDQuery);
				pstSelectBookingID.setInt(1, bookingID);
				rsBookingID = pstSelectBookingID.executeQuery();

				if (rsBookingID.next()) {

					System.out.println("booking ID: " + rsBookingID.getInt("bookingID"));
					// Clean buffer
					if (input.hasNextLine()) {
						input.nextLine();
					}
				}
			}
		} while (!isOkBookingID);

		System.out.println(
				"\n_________________________________________________________________________________________________________");
		System.out.println();
		System.out.println(
				"_________________________________________________________________________________________________________\n");
		System.out.println("-> Member ID: " + rsBookingID.getInt("personID"));
		System.out.println("-> first Name: " + rsBookingID.getString("firstName"));
		System.out.println("-> last name: " + rsBookingID.getString("lastName"));
		System.out.println("-> machine name: " + rsBookingID.getString("machineName"));
		System.out.println(
				"_________________________________________________________________________________________________________\n");

	}

	// ..........................................Method for Morning Booking
	// Info........................................
	public static void morningBookingInfo(Connection con) throws SQLException {
		String SelectMorningBookingQuery = "SELECT " + "    m.machineName, " + "    p.firstName, " + "    p.lastName, "
				+ "    p.personID, " + "    b.bookingID " + "FROM " + "    bookingTable b " + "JOIN "
				+ "    membersTable mem ON b.memberID = mem.memberID " + "JOIN "
				+ "    personsTable p ON mem.memberID = p.personID " + "JOIN "
				+ "    machinesTable m ON b.machineID = m.machineID " + "WHERE " + "    b.workoutSession = ?;";

		PreparedStatement pstSelectMorningBooking = con.prepareStatement(SelectMorningBookingQuery);
		pstSelectMorningBooking.setString(1, "Morning");
		ResultSet rsMorningBooking = pstSelectMorningBooking.executeQuery();

		System.out.println(
				"_____________________________________________________________________________________________");
		System.out.println("\t\t\t\t    Machine Reservation Details");
		System.out.println();
		System.out.println("\t\t\t\t\t*******************");
		System.out.println("\t\t\t\t\t**Morning Booking**");
		System.out.println("\t\t\t\t\t*******************\n");

		System.out.println(
				"_____________________________________________________________________________________________");
		System.out.printf("-> %-10s | %-20s | %-15s %-15s | %s\n", "Booking ID", "Machines Name", "First Name",
				"Last Name", "Member's ID");
		System.out.println(
				"_____________________________________________________________________________________________");

		while (rsMorningBooking.next()) {
			System.out.printf("-> %-10d | %-20s | %-15s %-15s | %s\n", rsMorningBooking.getInt("bookingID"),
					rsMorningBooking.getString("machineName"), rsMorningBooking.getString("firstName"),
					rsMorningBooking.getString("lastName"), rsMorningBooking.getString("personID"));
		}

		System.out.println(
				"_____________________________________________________________________________________________");

	}

	// ..............................................Method for Evening
	// BookingInfo.........................................
	public static void eveningBookingInfo(Connection con) throws SQLException {

		String SelectEveningBookingQuery = "SELECT " + "    m.machineName, " + "    p.firstName, " + "    p.lastName, "
				+ "    p.personID, " + "    b.bookingID " + "FROM " + "    bookingTable b " + "JOIN "
				+ "    membersTable mem ON b.memberID = mem.memberID " + "JOIN "
				+ "    personsTable p ON mem.memberID = p.personID " + "JOIN "
				+ "    machinesTable m ON b.machineID = m.machineID " + "WHERE " + "    b.workoutSession = ?;";

		PreparedStatement pstSelectEveningBooking = con.prepareStatement(SelectEveningBookingQuery);
		pstSelectEveningBooking.setString(1, "Evening");
		ResultSet rsEveningBooking = pstSelectEveningBooking.executeQuery();

		System.out.println(
				"_____________________________________________________________________________________________");
		System.out.println("\t\t\t\t    Machine Reservation Details");
		System.out.println();
		System.out.println("\t\t\t\t\t*******************");
		System.out.println("\t\t\t\t\t**Evening Booking**");
		System.out.println("\t\t\t\t\t*******************\n");

		System.out.println(
				"_____________________________________________________________________________________________");
		System.out.printf("-> %-10s | %-20s | %-15s %-15s | %s\n", "Booking ID", "Machines Name", "First Name",
				"Last Name", "Member's ID");
		System.out.println(
				"_____________________________________________________________________________________________");

		while (rsEveningBooking.next()) {
			System.out.printf("-> %-10d | %-20s | %-15s %-15s | %s\n", rsEveningBooking.getInt("bookingID"),
					rsEveningBooking.getString("machineName"), rsEveningBooking.getString("firstName"),
					rsEveningBooking.getString("lastName"), rsEveningBooking.getString("personID"));
		}

		System.out.println(
				"_____________________________________________________________________________________________");
	}

	// ..............................................Method to Reset
	// Booking............................................
	public static void resetBooking(Connection con, Statement stmt) throws IOException, SQLException {
		Scanner input = new Scanner(System.in);
		int rowsAffected = 0;
		PreparedStatement pstResetBooking = null;
		String ResetMorningBookingQuery = "DELETE FROM bookingTable WHERE workoutSession = 'Morning'";
		String ResetEveningBookingQuery = "DELETE FROM bookingTable WHERE workoutSession = 'Evening'";
		System.out.println("\n\n");
		System.out.println("\t\t\t\t\t**********************");
		System.out.println("\t\t\t\t\t**Reset Reservations**");
		System.out.println("\t\t\t\t\t**********************");
		System.out.println(
				"_________________________________________________________________________________________________________\n");

		System.out.println("Which Reservations would you like to Reset?");
		System.out.println("1. Morning");
		System.out.println("2. Evening");
		System.out.println("3. Both");
		System.out.println("0. Main Menu\n");
		System.out.print("Enter Your Choice: ");
		String cho = input.next();
		while (true) {
			if (!(cho.equals("1") || cho.equals("2") || cho.equals("3") || cho.equals("0"))) {
				System.out.print("\nInvalid Choice.... \nPlease Try Again: ");
				cho = input.next();
			} else
				break;
		}
		if (cho.equals("0")) {
			mainmenu(con, stmt);
		}
		if (cho.equals("1")) {
			System.out.println("\nDo you want to remove all of The Morning Reservation details Y/N ?");
		}
		if (cho.equals("2")) {
			System.out.println("\nDo you want to remove all of The Evening Reservation details Y/N ?");
		}
		if (cho.equals("3")) {
			System.out.println("\nDo you want to remove all of the Reservation details Y/N ?");
		}
		System.out.print("Enter Your Choice: ");
		String yn = input.next();
		while (true) {
			if (!(yn.equalsIgnoreCase("y") || yn.equalsIgnoreCase("n"))) {
				System.out.print("\nInvalid Choice.... \nPlease Try Again: ");
				yn = input.next();
			} else
				break;
		}
		if (yn.equalsIgnoreCase("n")) {
			mainmenu(con, stmt);
		}

		if (cho.equals("1") || cho.equals("3")) {
			pstResetBooking = con.prepareStatement(ResetMorningBookingQuery);
			rowsAffected = pstResetBooking.executeUpdate();
		}
		if (cho.equals("2") || cho.equals("3")) {
			pstResetBooking = con.prepareStatement(ResetEveningBookingQuery);
			rowsAffected = pstResetBooking.executeUpdate();
		}

		if (rowsAffected > 0) {// if success to delete from member table
			System.out.println(
					"\n_________________________________________________________________________________________________________");
			System.out.println("\t\t\t\t    Reservations have been reset");
			System.out.println(
					"---------------------------------------------------------------------------------------------------------\n\n");
		}
	}

	// ..........................................Method to Validate
	// Name....................................................
	public static String validate_name(String name) {
		Scanner input = new Scanner(System.in);
		int count;
		// Validating only alphabets in
		// input.............................................
		while (true) {
			if (name.length() > 50) {
				System.out.println("\n\t\t\t\t\tSeems incorrect,Please try again: ");
				name = input.nextLine();
			} else
				break;
		}
		while (true) {
			count = 0;
			for (int i = 0; i < name.length(); i++) {
				if (!(Character.isLetter(name.charAt(i)) || name.charAt(i) == '.' || name.charAt(i) == ' ')) {
					System.out.println("\n\t\t\t\tIt appears that you entered wrong name,Please try again ");
					count++;
					break;
				}
			}
			if (count != 0) {
				name = input.nextLine();
			} else
				break;
		}
		return name;
	}

	// ........................................Method to Validate Phone
	// Number..............................................

	public static String validatePhoneNumber(String contactNumber) {
		Scanner input = new Scanner(System.in);

		// Loop until a valid phone number is provided
		while (true) {
			// Check if the phone number has exactly 10 digits
			if (contactNumber.length() == 10 && contactNumber.matches("\\d{10}")) {
				break; // Valid phone number
			} else {
				// If the input is invalid, prompt the user to enter the phone number again
				System.out.println(
						"\n\t\tInvalid phone number. Please enter a 10-digit number (digits only). Try again: ");
				contactNumber = input.next(); // Read the next input
			}
		}

		return contactNumber; // Return the validated phone number
	}

	// ...................................................Method to validate
	// ID......................................................
	public static boolean isValidID(Connection con, Statement stmt, String id) throws IOException, SQLException {
		if (id.equalsIgnoreCase("y")) {
			mainmenu(con, stmt);
		}
		if (id.length() != 9) {
			System.out.println("Invalid input: The length of ID must be 9 characters");
			return false;
		}
		for (int i = 0; i < id.length(); i++) {
			if (!Character.isDigit(id.charAt(i))) {
				System.out.println("Invalid input: Please enter only numbers");
				return false;
			}
		}
		return true;
	}

	public static String validate_regNo(Connection con, Statement stmt, String regNo) throws IOException, SQLException {
		Scanner input = new Scanner(System.in);

		while (true) {
			// Check if the input is "Y" to go back to the main menu
			if (regNo.equalsIgnoreCase("y")) {
				mainmenu(con, stmt);
			}
			// Validate length and digits
			else if (regNo.length() != 4 || !regNo.matches("\\d{4}")) {
				System.out.println(
						"\nIt appears that you entered the wrong format. Please enter only 4 digits or 'Y' for Main Menu.");
				regNo = input.nextLine().trim(); // Read the input again and trim any extra spaces
			} else {
				break; // Input is valid
			}
		}
		return regNo;
	}

	// ....................................Method to Input Height or
	// Weight.................................................
	public static double InputforHeightAndWeight(String choice) {
		Scanner input = new Scanner(System.in);
		boolean takeInput = true;
		double weightdoub;
		double heightdoub;
		String str = (choice.equals("3")) ? "weight" : "height";
		do {
			try {
				if (choice.equals("3")) {
					System.out.print("\nEnter Weight in Kg: ");
					while (true) {
						weightdoub = input.nextDouble();
						if (weightdoub > 0 && weightdoub <= 300)
							break;
						else
							System.out.println("\n\t\t\t\tWeight seems incorrect. Please try once more.");
					}
					String weightstr = String.valueOf(weightdoub);
					takeInput = false;
					return weightdoub;
				} else {
					System.out.print("\nEnter Height (-Meter.-centimeter): ");
					while (true) {
						heightdoub = input.nextDouble();
						if (heightdoub > 0 && heightdoub < 10)
							break;
						else
							System.out.println("\n\t\t\t\tHeight seems incorrect. Please try once more.");
					}
					String heightstr = String.valueOf(heightdoub);
					takeInput = false;
					return heightdoub;
				}
			} catch (Exception ex) {
				System.out.println("\n\t\t\t\tTry again. (" + "Incorrect input: " + str + " must be in a number)");
				input.nextLine();
				return -1;
			}
		} while (takeInput);
	}

	// ....................................Method to create a connection with the
	// Data Base.................................
	public static Connection createConnection(String userName, String password) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql:gymmanagmentdb", userName, password);
		} catch (SQLException sql) {
			System.out.println("SQL EROOR:" + sql.getMessage());
		} catch (Exception exc) {
			System.out.println("Error");
		}

		return con;
	}

	// ............................Method to validate machine
	// number.................
	public static boolean validateMachineNum(Connection con, String machine_num) throws SQLException {
		String query = "SELECT machineId FROM machinesTable WHERE machineId = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, Integer.parseInt(machine_num));
		ResultSet rsMachine = pstmt.executeQuery();
		return rsMachine.next();

	}

	// ............................Method to View Bookings.................
	public static void View_Bookings(Connection con, String memberID) throws SQLException {
		String query = "SELECT bookingTable.bookingID, machinesTable.machineID, machinesTable.machineName FROM bookingTable JOIN machinesTable ON bookingTable.machineID=machinesTable.machineID WHERE bookingTable.memberID = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, memberID);

		ResultSet rs = pstmt.executeQuery();
		System.out.println("\n\t\t\t\t\t**Current Bookings**");
		System.out.println(" Booking ID   \t\tMachine's Name \t\tMachine's ID\n");
		System.out.println(
				"_________________________________________________________________________________________________________");

		while (rs.next()) {
			System.out.println("> " + rs.getString("bookingID") + "        |\t\t" + rs.getString("machineName")
					+ "|\t\t" + rs.getString("machineID"));
		}
	}

	// ..........................Validate date of birth.........................
	private static String validateDateOfBirth(Scanner input) {
		String dateOfBirth;
		while (true) {
			dateOfBirth = input.next();
			if (dateOfBirth.length() == 10 && dateOfBirth.charAt(2) == '-' && dateOfBirth.charAt(5) == '-') {
				try {
					int date = Integer.parseInt(dateOfBirth.substring(0, 2));
					int month = Integer.parseInt(dateOfBirth.substring(3, 5));
					int year = Integer.parseInt(dateOfBirth.substring(6, 10));

					if (year < 1922 || year > 2024) {
						System.out.println("Year must be between 1922 and 2024. Try again.");
						continue;
					}

					if (month < 1 || month > 12) {
						System.out.println("Month must be between 1 and 12. Try again.");
						continue;
					}

					if (date < 1 || date > 31) {
						System.out.println("Day must be between 1 and 31. Try again.");
						continue;
					}

					// Further checks for specific months can be added here if needed

					break; // Valid date
				} catch (NumberFormatException e) {
					System.out.println("Date of Birth must be in the format DD-MM-YYYY. Try again.");
				}
			} else {
				System.out.println("Date of Birth must be in the format DD-MM-YYYY. Try again.");
			}
		}
		return dateOfBirth;
	}

}
