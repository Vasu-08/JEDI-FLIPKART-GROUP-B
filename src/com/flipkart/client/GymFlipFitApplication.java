package com.flipkart.client;

import com.flipkart.bean.FlipFitAdmin;
import com.flipkart.bean.FlipFitGymCustomer;
import com.flipkart.bean.FlipFitGymOwner;
import com.flipkart.bean.FlipFitUser;
import com.flipkart.business.FlipFitAdminBusiness;
import com.flipkart.business.FlipFitGymCustomerBusiness;
import com.flipkart.business.FlipFitGymOwnerBusiness;
import com.flipkart.business.interfaces.IFlipFitAdmin;
import com.flipkart.dao.FlipFitAdminDAOImpl;
import com.flipkart.dao.FlipFitGymCustomerDAOImpl;
import com.flipkart.dao.FlipFitGymOwnerDAOImpl;
import com.flipkart.exceptions.ExceptionHandler;
import com.flipkart.exceptions.InvalidChoiceException;

import java.util.*;

import com.flipkart.constant.ColorConstants;


public class GymFlipFitApplication {
    /**
     * main
     *
     * @param args
     * @throws InvalidChoiceException
     */
    public static void main(String[] args) throws InvalidChoiceException {
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

                choice = in.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.println(ColorConstants.BLUE + "=========== Login ===========" + ColorConstants.RESET);
                        System.out.print(ColorConstants.PURPLE + "Enter your emailId:> " + ColorConstants.RESET);
                        String username = in.next();
                        System.out.print(ColorConstants.PURPLE + "Enter your password:> " + ColorConstants.RESET);
                        String password = in.next();
                        System.out.print(ColorConstants.PURPLE + "Enter your role:> Customer/Admin/GymOwner " + ColorConstants.RESET);
                        String role = in.next();

                        switch (role) {
                            case "Customer": {
                                FlipFitUser gymCustomer = new FlipFitUser();
                                gymCustomer.setEmailID(username);
                                gymCustomer.setPassword(password);

                                FlipFitGymCustomerDAOImpl flipFitGymCustomerDAO = new FlipFitGymCustomerDAOImpl();
                                FlipFitGymCustomerBusiness GCBservice = new FlipFitGymCustomerBusiness(flipFitGymCustomerDAO);

                                gymCustomer = GCBservice.login(gymCustomer);
                                 if (gymCustomer == null)
                                     throw new IllegalStateException("Invalid credentials");
                                System.out.println(ColorConstants.GREEN + "=========== Customer Menu ===========" + ColorConstants.RESET);
                                GymFlipFitCustomerMenu.getFlipFitCustomerMenu(gymCustomer);
                                break;
                            }
                            case "Admin": {
                                FlipFitAdmin admin = new FlipFitAdmin();
                                admin.setEmailID(username);
                                admin.setPassword(password);

                                FlipFitAdminDAOImpl adminDAO = new FlipFitAdminDAOImpl();
                                IFlipFitAdmin flipFitAdmin = new FlipFitAdminBusiness(adminDAO);
                                boolean res = flipFitAdmin.adminLogin(admin);
                                if (res) {
                                    System.out.println(ColorConstants.GREEN + "=========== Admin Menu ===========" + ColorConstants.RESET);
                                    GymFlipFitAdminMenu.getAdminView();
                                }
                                break;
                            }
                            case "GymOwner": {
                                FlipFitUser gymOwner = new FlipFitUser();
                                gymOwner.setEmailID(username);
                                gymOwner.setPassword(password);

                                FlipFitGymOwnerDAOImpl flipFitGymOwnerDAO = new FlipFitGymOwnerDAOImpl();
                                FlipFitGymOwnerBusiness GOBservice = new FlipFitGymOwnerBusiness(flipFitGymOwnerDAO);

                                gymOwner = GOBservice.login(gymOwner);
                                if (gymOwner == null)
                                    throw new IllegalStateException("Invalid credentials");
                                System.out.println(ColorConstants.GREEN + "=========== GymOwner Menu ===========" + ColorConstants.RESET);
                                GymFlipFitOwnerMenu.getFlipFitOwnerView(gymOwner);
                                break;
                            }
                        }
                        break;
                    }

                    case 2: {
                        System.out.println(ColorConstants.BLUE + "=========== Registration of Gym Customer ===========" + ColorConstants.RESET);

                        System.out.print(ColorConstants.PURPLE + "Enter your email address:> " + ColorConstants.RESET);
                        String emailID = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your phone number:> " + ColorConstants.RESET);
                        String phoneNumber = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your city:> " + ColorConstants.RESET);
                        String city = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your pin code:> " + ColorConstants.RESET);
                        String pinCode = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your password:> " + ColorConstants.RESET);
                        String password = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter username:> " + ColorConstants.RESET);
                        String username = in.next();

                        FlipFitUser gymCustomer = new FlipFitUser();
                        gymCustomer.setEmailID(emailID);
                        gymCustomer.setPassword(password);
                        gymCustomer.setPhoneNumber(phoneNumber);
                        gymCustomer.setUserName(username);

                        FlipFitGymCustomerDAOImpl flipFitGymCustomerDAO = new FlipFitGymCustomerDAOImpl();
                        FlipFitGymCustomerBusiness GCBservice = new FlipFitGymCustomerBusiness(flipFitGymCustomerDAO);

                        FlipFitGymCustomer flipFitGymCustomer = new FlipFitGymCustomer();
                        flipFitGymCustomer.setEmailID(emailID);
                        flipFitGymCustomer.setPassword(password);
                        flipFitGymCustomer.setPhoneNumber(phoneNumber);
                        flipFitGymCustomer.setUserName(username);
                        flipFitGymCustomer.setCity(city);
                        flipFitGymCustomer.setPinCode(pinCode);
                        flipFitGymCustomer.setRole(0);

                        flipFitGymCustomer = GCBservice.registerCustomer(flipFitGymCustomer);
                        gymCustomer.setUserID(flipFitGymCustomer.getUserId());
                        System.out.println(ColorConstants.GREEN + "Registration completed for " + gymCustomer.getUserName() + ColorConstants.RESET);
                        System.out.println(ColorConstants.GREEN + "=========== Customer Menu ===========" + ColorConstants.RESET);

                        GymFlipFitCustomerMenu.getFlipFitCustomerMenu(gymCustomer);
                        break;
                    }

                    case 3: {
                        System.out.println(ColorConstants.BLUE + "=========== Registration of Gym Owner ===========" + ColorConstants.RESET);

                        System.out.print(ColorConstants.PURPLE + "Enter your email address:> " + ColorConstants.RESET);
                        String emailID = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your phone number:> " + ColorConstants.RESET);
                        String phoneNumber = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your city:> " + ColorConstants.RESET);
                        String city = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your pin code:> " + ColorConstants.RESET);
                        String pinCode = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your password:> " + ColorConstants.RESET);
                        String password = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter username:> " + ColorConstants.RESET);
                        String username = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your panId:> " + ColorConstants.RESET);
                        String panId = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your gstNum:> " + ColorConstants.RESET);
                        String gstNum = in.next();

                        System.out.print(ColorConstants.PURPLE + "Enter your aadharNumber:> " + ColorConstants.RESET);
                        String aadharNumber = in.next();

                        FlipFitGymOwner flipFitOwner = new FlipFitGymOwner();
                        flipFitOwner.setEmailID(emailID);
                        flipFitOwner.setPassword(password);
                        flipFitOwner.setPhoneNumber(phoneNumber);
                        flipFitOwner.setCity(city);
                        flipFitOwner.setPinCode(pinCode);
                        flipFitOwner.setUserName(username);
                        flipFitOwner.setRole(1);
                        flipFitOwner.setGSTIN(gstNum);
                        flipFitOwner.setAadharNumber(aadharNumber);
                        flipFitOwner.setPanId(panId);
                        flipFitOwner.setIsApproved(false);

                        FlipFitGymOwnerDAOImpl flipFitGymOwnerDAO = new FlipFitGymOwnerDAOImpl();
                        FlipFitGymOwnerBusiness GOBservice = new FlipFitGymOwnerBusiness(flipFitGymOwnerDAO);

                        GOBservice.registerOwner(flipFitOwner);
                        System.out.println(ColorConstants.GREEN + "Successfully registered " + flipFitOwner.getUserName() + ColorConstants.RESET);

                        break;
                    }

                    case 4: {
                        System.out.println(ColorConstants.RED + "Exit" + ColorConstants.RESET);
                        return;
                    }

                    default: {
                        throw new InvalidChoiceException(ColorConstants.RED + "Invalid choice entered: " + choice + ColorConstants.RESET);
                    }
                }
            }
            while (true);
        } catch (InvalidChoiceException e) {
            ExceptionHandler.InvalidChoiceMainMenu(e);
        }
    }
}
