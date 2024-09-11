package com.flipkart.client;

import com.flipkart.bean.FlipFitGymCentre;
import com.flipkart.bean.FlipFitGymCustomer;
import com.flipkart.bean.FlipFitSlots;
import com.flipkart.bean.FlipFitUser;
import com.flipkart.business.BookingsBusiness;
import com.flipkart.business.FlipFitGymCentreBusiness;
import com.flipkart.business.FlipFitGymCustomerBusiness;
import com.flipkart.constant.ColorConstants;
import com.flipkart.dao.FlipFitBookingDAOImpl;
import com.flipkart.dao.FlipFitGymCentreDAOImpl;
import com.flipkart.dao.FlipFitGymCustomerDAOImpl;
import com.flipkart.exceptions.ExceptionHandler;
import com.flipkart.exceptions.InvalidChoiceException;

import java.util.Scanner;

import java.util.List;

public class GymFlipFitCustomerMenu {
    /**
     * getFlipFitCustomerMenu
     * @param gymCustomer
     * @throws InvalidChoiceException
     */
    public static void getFlipFitCustomerMenu(FlipFitUser gymCustomer) throws InvalidChoiceException {
        try {
            int userId = gymCustomer.getUserID();
            Scanner sc = new Scanner(System.in);

            FlipFitGymCustomerDAOImpl flipFitGymCustomerDAO = new FlipFitGymCustomerDAOImpl();
            FlipFitGymCustomerBusiness FCBservice = new FlipFitGymCustomerBusiness(flipFitGymCustomerDAO);

            FlipFitGymCentreDAOImpl flipFitGymCenterDAO = new FlipFitGymCentreDAOImpl();
            FlipFitGymCentreBusiness FCService = new FlipFitGymCentreBusiness(flipFitGymCenterDAO);

            FlipFitBookingDAOImpl flipFitBookingDAO = new FlipFitBookingDAOImpl();
            BookingsBusiness BService = new BookingsBusiness(flipFitBookingDAO);

            int choice = 0;

            do {
                System.out.println(ColorConstants.CYAN + "===========================" + ColorConstants.RESET);
                System.out.println(ColorConstants.CYAN + "    FlipFit Customer Menu   " + ColorConstants.RESET);
                System.out.println(ColorConstants.CYAN + "===========================" + ColorConstants.RESET);

                System.out.println(ColorConstants.YELLOW + """
                        Choose an option:
                         1. View Booked Slots
                         2. View Centres
                         3. Logout
                        """ + ColorConstants.RESET);

                choice = sc.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.println(ColorConstants.BLUE + "=========== View Booked Slots ===========" + ColorConstants.RESET);
                        FCBservice.viewBookedSlots(userId);

                        System.out.println(ColorConstants.YELLOW + "Type 1. If you wish to cancel" + ColorConstants.RESET);
                        System.out.println(ColorConstants.YELLOW + "Type 2. To return to main menu" + ColorConstants.RESET);

                        choice = sc.nextInt();

                        if (choice == 1) {
                            System.out.print(ColorConstants.PURPLE + "Choose the booking ID you wish to cancel:> " + ColorConstants.RESET);
                            int bookingId = sc.nextInt();
                            BService.deleteBooking(bookingId);
                        }

                        break;
                    }
                    case 2: {
                        System.out.println(ColorConstants.BLUE + "=========== View Centres ===========" + ColorConstants.RESET);

                        List<FlipFitGymCentre> centreList = FCBservice.viewCentres();
                        for (FlipFitGymCentre centre : centreList) {
                            System.out.println(ColorConstants.GREEN + "CentreId: " + centre.getCentreID() + ", City: " + centre.getCity() + ", Pincode: " + centre.getPincode() + ColorConstants.RESET);
                        }

                        System.out.print(ColorConstants.PURPLE + "Choose a centre you want to book a slot in:> " + ColorConstants.RESET);
                        int centreId = sc.nextInt();

                        List<FlipFitSlots> slotsList = FCService.viewAvailableSlots(centreId);
                        System.out.println(ColorConstants.BLUE + "These are the available slots:" + ColorConstants.RESET);
                        for (FlipFitSlots flipFitSlots : slotsList) {
                            System.out.println(ColorConstants.GREEN + "Slot Id: " + flipFitSlots.getSlotId() + ", Slot Timing: " + flipFitSlots.getSlotTime() + ", Availability: " + flipFitSlots.getSeatsAvailable() + ", CentreId: " + flipFitSlots.getCentreId() + ColorConstants.RESET);
                        }

                        System.out.print(ColorConstants.PURPLE + "Give the start time you wish to book:> " + ColorConstants.RESET);
                        int startTime = sc.nextInt();

                        System.out.print(ColorConstants.PURPLE + "Give the centre ID:> " + ColorConstants.RESET);
                        int centreID = sc.nextInt();

                        BService.makeBooking(userId, centreID, startTime);

                        break;
                    }
                    case 3: {
                        System.out.println(ColorConstants.GREEN + "Successfully logged out" + ColorConstants.RESET);
                        return;
                    }
                    default: {
                        throw new InvalidChoiceException(ColorConstants.RED + "Invalid choice entered: " + choice + ColorConstants.RESET);
                    }
                }
            } while (choice != 4);
        } catch (InvalidChoiceException e) {
            ExceptionHandler.InvalidChoiceMainMenu(e);
        }
    }
}
