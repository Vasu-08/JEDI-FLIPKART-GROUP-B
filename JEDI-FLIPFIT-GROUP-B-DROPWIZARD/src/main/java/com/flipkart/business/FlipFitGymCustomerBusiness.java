package com.flipkart.business;

import java.util.List;

import com.flipkart.business.interfaces.IFlipFitGymCustomer;
import com.flipkart.dao.FlipFitBookingDAOImpl;
import com.flipkart.dao.FlipFitGymCustomerDAOImpl;
import com.flipkart.dao.FlipFitSlotDAOImpl;
import com.flipkart.dao.FlipFitUserDAOImpl;
import com.flipkart.exceptions.InvalidChoiceException;
import com.flipkart.model.FlipFitBooking;
import com.flipkart.model.FlipFitGymCentre;
import com.flipkart.model.FlipFitGymCustomer;
import com.flipkart.model.FlipFitSlots;
import com.flipkart.model.FlipFitUser;

public class FlipFitGymCustomerBusiness implements IFlipFitGymCustomer {
    private final FlipFitGymCustomerDAOImpl flipFitGymCustomerDAOImpl;

    public FlipFitGymCustomerBusiness(FlipFitGymCustomerDAOImpl FFGymCustomer) {
        this.flipFitGymCustomerDAOImpl = FFGymCustomer;
    }

    @Override
    public List<FlipFitBooking> viewBookedSlots(int userId) {

        System.out.println("Viewing booked slots:> ");
        FlipFitBookingDAOImpl bookingDAO = new FlipFitBookingDAOImpl();
        FlipFitSlotDAOImpl slotDAO = new FlipFitSlotDAOImpl();
        List<FlipFitBooking> bookingsList = bookingDAO.getAllBookings(userId);
        for (FlipFitBooking booking : bookingsList) {
            FlipFitSlots slotdetails = slotDAO.getSlotDetailsById(booking.getSlotId());
            System.out.println("Booking ID: " + booking.getBookingId() + "Slot timing " + slotdetails.getSlotTime());
        }
        return bookingsList;
    }

    @Override
    public FlipFitBooking checkBookingConflicts(int userId, int slotTime) {
        System.out.println("Checking conflict for slot " + slotTime);
        return flipFitGymCustomerDAOImpl.checkBookingConflicts(userId, slotTime);
    }

    @Override
    public List<FlipFitGymCentre> viewCentres() {
        System.out.println("view centres called:> ");
        return flipFitGymCustomerDAOImpl.viewCentres();
    }

    public boolean makePayment(int userId) {
        System.out.println("Make payment called:> ");
        flipFitGymCustomerDAOImpl.makePayment(userId);
        return true;
    }

    public FlipFitGymCustomer editDetails(FlipFitGymCustomer flipFitGymCustomer) throws InvalidChoiceException {

        // try
        // {
        return flipFitGymCustomerDAOImpl.editDetails(flipFitGymCustomer);

        // }
        // catch (InvalidChoiceException e)
        // {
        // ExceptionHandler.InvalidChoiceEditDetailsMenu(e);
        // return null;
        // }

    }

    @Override
    public FlipFitGymCustomer registerCustomer(FlipFitGymCustomer flipFitGymCustomer) {

        FlipFitUser flipFitUser = new FlipFitUser();
        flipFitUser.setPassword(flipFitGymCustomer.getPassword());
        flipFitUser.setEmailID(flipFitGymCustomer.getEmailID());
        flipFitUser.setPhoneNumber(flipFitGymCustomer.getPhoneNumber());
        flipFitUser.setUserName(flipFitGymCustomer.getUserName());
        flipFitUser.setRoleID(1);
        flipFitGymCustomer.setRole(1);
        flipFitGymCustomerDAOImpl.addUser(flipFitUser);
        return flipFitGymCustomerDAOImpl.addCustomer(flipFitGymCustomer, flipFitUser);
    }

    @Override
    public FlipFitGymCustomer login(FlipFitUser flipFitUser) {
        FlipFitUserDAOImpl userDAO = new FlipFitUserDAOImpl();
        flipFitUser.setRoleID(1);
        return userDAO.login(flipFitUser.getEmailID(), flipFitUser.getPassword());
    }
}
