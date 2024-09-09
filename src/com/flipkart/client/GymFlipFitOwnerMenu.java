package com.flipkart.client;

import com.flipkart.bean.FlipFitGymCentre;
import com.flipkart.bean.FlipFitGymOwner;
import com.flipkart.bean.FlipFitSlots;
import com.flipkart.bean.FlipFitUser;
import com.flipkart.business.FlipFitAdminBusiness;
//import com.flipkart.business.FlipFitGymCentreBusiness;
import com.flipkart.business.*;
import com.flipkart.dao.*;
import com.flipkart.exceptions.InvalidChoiceException;
import com.flipkart.constant.ColorConstants;

import java.util.List;
import java.util.Scanner;

public class GymFlipFitOwnerMenu {

    /**
     * getFlipFitOwnerView
     * @param gymOwner
     * @throws InvalidChoiceException
     */
    public static void getFlipFitOwnerView(FlipFitUser gymOwner) throws InvalidChoiceException {
        try {
            FlipFitGymOwnerDAOImpl flipFitGymOwnerDAO = new FlipFitGymOwnerDAOImpl();
            FlipFitGymOwnerBusiness GOBservice = new FlipFitGymOwnerBusiness(flipFitGymOwnerDAO);

            Scanner sc = new Scanner(System.in);
            int choice = 0;

            do {
                System.out.println(ColorConstants.CYAN + "===========================" + ColorConstants.RESET);
                System.out.println(ColorConstants.CYAN + "    Gym Owner Menu          " + ColorConstants.RESET);
                System.out.println(ColorConstants.CYAN + "===========================" + ColorConstants.RESET);

                System.out.println(ColorConstants.YELLOW + """
                        Choose an option:
                         1. Add Centre
                         2. View Centres
                         3. Add Slot
                         4. Delete Slot
                         5. Logout
                        """ + ColorConstants.RESET);

                choice = sc.nextInt();

                switch (choice) {
                    case 1: {
                        System.out.println(ColorConstants.BLUE + "=========== Add Centre =========== " + ColorConstants.RESET);

                        Scanner scanner = new Scanner(System.in);
                        int ownerID = gymOwner.getUserID();

                        System.out.print(ColorConstants.PURPLE + "Enter Capacity:> " + ColorConstants.RESET);
                        int capacity = scanner.nextInt();

                        System.out.print(ColorConstants.PURPLE + "Enter City:> " + ColorConstants.RESET);
                        String city = scanner.next();

                        System.out.print(ColorConstants.PURPLE + "Enter State:> " + ColorConstants.RESET);
                        String state = scanner.next();

                        System.out.print(ColorConstants.PURPLE + "Enter Pincode:> " + ColorConstants.RESET);
                        String pincode = scanner.next();

                        FlipFitGymCentre flipFitGymCentre = new FlipFitGymCentre();
                        flipFitGymCentre.setOwnerID(ownerID);
                        flipFitGymCentre.setCapacity(capacity);
                        flipFitGymCentre.setCity(city);
                        flipFitGymCentre.setState(state);
                        flipFitGymCentre.setPincode(pincode);
                        flipFitGymCentre.setApproved(true);

                        GOBservice.addCentre(flipFitGymCentre);

                        System.out.println(ColorConstants.GREEN + "Gym Centre created successfully at "+ flipFitGymCentre.getCity() + " with pincode: "+ flipFitGymCentre.getPincode() + ColorConstants.RESET);
                        break;
                    }

                    case 2: {
                        System.out.println(ColorConstants.BLUE + "=========== View Centres for the owner =========== " + ColorConstants.RESET);

                        FlipFitGymOwner flipFitGymOwner = new FlipFitGymOwner();
                        flipFitGymOwner.setUserId(gymOwner.getUserID());

                        List<FlipFitGymCentre> centreList = GOBservice.viewCentres(flipFitGymOwner);
                        for (FlipFitGymCentre centre : centreList) {
                            System.out.println(ColorConstants.GREEN + "CentreID: " + centre.getCentreID() + ", Capacity: " + centre.getCapacity() + ", City: " + centre.getCity() + ", State: " + centre.getState() + ColorConstants.RESET);
                        }

                        break;
                    }

                    case 3: {
                        System.out.println(ColorConstants.BLUE + "=========== Add Slot in a Gym =========== " + ColorConstants.RESET);

                        System.out.print(ColorConstants.PURPLE + "Enter gym centre ID:> " + ColorConstants.RESET);
                        int centreId = sc.nextInt();

                        System.out.print(ColorConstants.PURPLE + "Enter slot time:> " + ColorConstants.RESET);
                        int slotTime = sc.nextInt();

                        System.out.print(ColorConstants.PURPLE + "Enter the max capacity of the slot:> " + ColorConstants.RESET);
                        int maxCapacity = sc.nextInt();

                        FlipFitSlots slot = new FlipFitSlots();
                        slot.setCentreId(centreId);
                        slot.setSlotTime(slotTime);
                        slot.setSeatsAvailable(maxCapacity);
                        slot.setMaxCapacity(maxCapacity);

                        FlipFitSlotDAOImpl slotDAO = new FlipFitSlotDAOImpl();
                        slotDAO.addSlot(slot);

                        System.out.println(ColorConstants.GREEN + "Slot created successfully with Slot Time: "+ slot.getSlotTime() + " and maximum Capacity: " + slot.getMaxCapacity() + ColorConstants.RESET);
                        break;
                    }

                    case 4: {
                        System.out.println(ColorConstants.BLUE + "=========== Delete Slot =========== " + ColorConstants.RESET);

                        System.out.print(ColorConstants.PURPLE + "Enter centre ID:> " + ColorConstants.RESET);
                        int centreId = sc.nextInt();

                        System.out.print(ColorConstants.PURPLE + "Enter slot ID:> " + ColorConstants.RESET);
                        int slotId = sc.nextInt();

                        FlipFitSlotDAOImpl slotDAO = new FlipFitSlotDAOImpl();
                        slotDAO.deleteSlot(centreId, slotId);

                        System.out.println(ColorConstants.GREEN + "Slot deleted successfully." + ColorConstants.RESET);
                        break;
                    }

                    case 5: {
                        System.out.println(ColorConstants.GREEN + "Successfully logged out." + ColorConstants.RESET);
                        break;
                    }

                    default: {
                        throw new InvalidChoiceException(ColorConstants.RED + "Invalid choice entered: " + choice + ColorConstants.RESET);
                    }
                }
            } while (choice != 5);
        } catch (InvalidChoiceException e) {
            System.out.println(ColorConstants.RED + e.getMessage() + ColorConstants.RESET);
        }
    }
}
