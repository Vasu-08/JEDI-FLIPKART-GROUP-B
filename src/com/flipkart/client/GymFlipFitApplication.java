package com.flipkart.client;

import com.flipkart.bean.*;
import com.flipkart.business.*;
import com.flipkart.business.interfaces.IFlipFitAdmin;
import com.flipkart.constant.ColorConstants;
import com.flipkart.dao.*;
import com.flipkart.exceptions.*;

import java.util.*;

public class GymFlipFitApplication {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            int choice = 0;

            do {
                System.out.println(ColorConstants.CYAN + "=====================================" + ColorConstants.RESET);
                System.out.println(ColorConstants.CYAN + "           Welcome To FlipFit        " + ColorConstants.RESET);
                System.out.println(ColorConstants.CYAN + "=====================================" + ColorConstants.RESET);

                System.out.println(ColorConstants.YELLOW + """
                        Type:
                         1 -> Login
                         2 -> Registration of Customer
                         3 -> Registration of Gym Owner
                         4 -> Exit
                        """ + ColorConstants.RESET);

                if (!in.hasNextInt()) {
                    throw new InvalidChoiceException("Input must be a number.");
                }

                choice = in.nextInt();

                switch (choice) {
                    case 1 -> {
                        login(in);
                    }
                    case 2 -> {
                        registerCustomer(in);
                    }
                    case 3 -> {
                        registerGymOwner(in);
                    }
                    case 4 -> {
                        System.out.println(ColorConstants.RED + "Exit" + ColorConstants.RESET);
                        return;
                    }
                    default -> throw new InvalidChoiceException(ColorConstants.RED + "Invalid choice entered: " + choice + ColorConstants.RESET);
                }
            } while (true);
        } catch (InvalidChoiceException e) {
            ExceptionHandler.InvalidChoiceMainMenu(e);
        }
    }

    private static void login(Scanner in) throws InvalidChoiceException {
        try {
            System.out.println(ColorConstants.BLUE + "=========== Login ===========" + ColorConstants.RESET);
            System.out.print(ColorConstants.PURPLE + "Enter your emailId:> " + ColorConstants.RESET);
            String username = in.next();

            if (!username.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new InvalidDataException("Invalid email format");
            }

            System.out.print(ColorConstants.PURPLE + "Enter your password:> " + ColorConstants.RESET);
            String password = in.next();

            if (password.isEmpty()) {
                throw new InvalidDataException("Password cannot be empty");
            }

            System.out.print(ColorConstants.PURPLE + "Enter your role:> Customer/Admin/GymOwner " + ColorConstants.RESET);
            String role = in.next();

            switch (role) {
                case "Customer" -> customerLogin(username, password);
                case "Admin" -> adminLogin(username, password);
                case "GymOwner" -> gymOwnerLogin(username, password);
                default -> throw new InvalidChoiceException("Invalid role entered: " + role);
            }
        } catch (InvalidDataException | IllegalStateException e) {
            System.out.println(ColorConstants.RED + e.getMessage() + ColorConstants.RESET);
        }
    }

    private static void customerLogin(String username, String password) throws InvalidDataException, InvalidChoiceException {
        FlipFitUser gymCustomer = new FlipFitUser();
        gymCustomer.setEmailID(username);
        gymCustomer.setPassword(password);

        FlipFitGymCustomerDAOImpl flipFitGymCustomerDAO = new FlipFitGymCustomerDAOImpl();
        FlipFitGymCustomerBusiness GCBservice = new FlipFitGymCustomerBusiness(flipFitGymCustomerDAO);

        gymCustomer = GCBservice.login(gymCustomer);

        if (gymCustomer == null) {
            throw new InvalidDataException("Invalid credentials");
        }
        System.out.println(ColorConstants.GREEN + "=========== Customer Menu ===========" + ColorConstants.RESET);
        GymFlipFitCustomerMenu.getFlipFitCustomerMenu(gymCustomer);
    }

    private static void adminLogin(String username, String password) throws InvalidDataException, InvalidChoiceException {
        FlipFitAdmin admin = new FlipFitAdmin();
        admin.setEmailID(username);
        admin.setPassword(password);

        FlipFitAdminDAOImpl adminDAO = new FlipFitAdminDAOImpl();
        IFlipFitAdmin flipFitAdmin = new FlipFitAdminBusiness(adminDAO);
        boolean res = flipFitAdmin.adminLogin(admin);
        if (res) {
            System.out.println(ColorConstants.GREEN + "=========== Admin Menu ===========" + ColorConstants.RESET);
            GymFlipFitAdminMenu.getAdminView();
        } else {
            throw new InvalidDataException("Invalid admin credentials");
        }
    }

    private static void gymOwnerLogin(String username, String password) throws InvalidDataException, InvalidChoiceException {
        FlipFitUser gymOwner = new FlipFitUser();
        gymOwner.setEmailID(username);
        gymOwner.setPassword(password);

        FlipFitGymOwnerDAOImpl flipFitGymOwnerDAO = new FlipFitGymOwnerDAOImpl();
        FlipFitGymOwnerBusiness GOBservice = new FlipFitGymOwnerBusiness(flipFitGymOwnerDAO);

        gymOwner = GOBservice.login(gymOwner);
        if (gymOwner == null) {
            throw new InvalidDataException("Invalid credentials");
        }
        System.out.println(ColorConstants.GREEN + "=========== GymOwner Menu ===========" + ColorConstants.RESET);
        GymFlipFitOwnerMenu.getFlipFitOwnerView(gymOwner);
    }

    private static void registerCustomer(Scanner in) {
        try {
            System.out.println(ColorConstants.BLUE + "=========== Registration of Gym Customer ===========" + ColorConstants.RESET);
            String emailID = getInput(in, "email address");
            validateEmail(emailID);

            String phoneNumber = getInput(in, "phone number");
            validatePhoneNumber(phoneNumber);

            String city = getInput(in, "city");
            String pinCode = getInput(in, "pin code");
            validatePinCode(pinCode);

            String password = getInput(in, "password");
            String username = getInput(in, "username");

            FlipFitUser gymCustomer = new FlipFitUser();
            gymCustomer.setEmailID(emailID);
            gymCustomer.setPassword(password);
            gymCustomer.setPhoneNumber(phoneNumber);
            gymCustomer.setUserName(username);

            FlipFitGymCustomer flipFitGymCustomer = new FlipFitGymCustomer();
            flipFitGymCustomer.setEmailID(emailID);
            flipFitGymCustomer.setPhoneNumber(phoneNumber);
            flipFitGymCustomer.setCity(city);
            flipFitGymCustomer.setPinCode(pinCode);
            flipFitGymCustomer.setPassword(password);
            flipFitGymCustomer.setUserName(username);
            flipFitGymCustomer.setRole(0);

            FlipFitGymCustomerDAOImpl flipFitGymCustomerDAO = new FlipFitGymCustomerDAOImpl();
            FlipFitGymCustomerBusiness GCBservice = new FlipFitGymCustomerBusiness(flipFitGymCustomerDAO);

            flipFitGymCustomer = GCBservice.registerCustomer(flipFitGymCustomer);

            System.out.println(ColorConstants.GREEN + "Registration completed for " + flipFitGymCustomer.getUserName() + ColorConstants.RESET);

            GymFlipFitCustomerMenu.getFlipFitCustomerMenu(gymCustomer);
        } catch (InvalidDataException e) {
            System.out.println(ColorConstants.RED + e.getMessage() + ColorConstants.RESET);
        } catch (InvalidChoiceException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerGymOwner(Scanner in) {
        try {
            System.out.println(ColorConstants.BLUE + "=========== Registration of Gym Owner ===========" + ColorConstants.RESET);
            String emailID = getInput(in, "email address");
            validateEmail(emailID);

            String phoneNumber = getInput(in, "phone number");
            validatePhoneNumber(phoneNumber);

            String city = getInput(in, "city");
            String pinCode = getInput(in, "pin code");
            validatePinCode(pinCode);

            String password = getInput(in, "password");
            String username = getInput(in, "username");

            String panId = getInput(in, "PAN ID");
            String gstNum = getInput(in, "GST number");
            String aadharNumber = getInput(in, "Aadhar number");

            FlipFitGymOwner flipFitOwner = new FlipFitGymOwner();
            flipFitOwner.setEmailID(emailID);
            flipFitOwner.setPhoneNumber(phoneNumber);
            flipFitOwner.setCity(city);
            flipFitOwner.setPinCode(pinCode);
            flipFitOwner.setPassword(password);
            flipFitOwner.setUserName(username);
            flipFitOwner.setRole(1);
            flipFitOwner.setPanId(panId);
            flipFitOwner.setGSTIN(gstNum);
            flipFitOwner.setAadharNumber(aadharNumber);
            flipFitOwner.setIsApproved(false);

            FlipFitGymOwnerDAOImpl flipFitGymOwnerDAO = new FlipFitGymOwnerDAOImpl();
            FlipFitGymOwnerBusiness GOBservice = new FlipFitGymOwnerBusiness(flipFitGymOwnerDAO);

            GOBservice.registerOwner(flipFitOwner);
            System.out.println(ColorConstants.GREEN + "Successfully registered " + flipFitOwner.getUserName() + ColorConstants.RESET);
        } catch (InvalidDataException e) {
            System.out.println(ColorConstants.RED + e.getMessage() + ColorConstants.RESET);
        }
    }

    // Helper methods for input and validation
    private static String getInput(Scanner in, String field) {
        System.out.print(ColorConstants.PURPLE + "Enter your " + field + ":> " + ColorConstants.RESET);
        return in.next();
    }

    private static void validateEmail(String email) throws InvalidDataException {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidDataException("Invalid email format");
        }
    }

    private static void validatePhoneNumber(String phoneNumber) throws InvalidDataException {
        if (!phoneNumber.matches("\\d{10}")) {
            throw new InvalidDataException("Invalid phone number format");
        }
    }

    private static void validatePinCode(String pinCode) throws InvalidDataException {
        if (!pinCode.matches("\\d{6}")) {
            throw new InvalidDataException("Invalid pin code format");
        }
    }
}
