package com.flipkart.restController;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flipkart.business.BookingsBusiness;
import com.flipkart.model.FlipFitBooking;

@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)

public class BookingController {
    private final BookingsBusiness bookingService;

    @Inject
    public BookingController(BookingsBusiness bookingsService) {
        this.bookingService = bookingsService;
    }

    @POST
    @Path("/add/centreID={centreID}/startTime={startTime}/userID={userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public FlipFitBooking addBooking(@PathParam("centreID") int centreID, @PathParam("startTime") int startTime,
            @PathParam("userID") int userID) {
        return bookingService.makeBooking(userID, centreID, startTime);
    }

    @DELETE
    @Path("/delete/bookingID={bookingID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean deleteBooking(@PathParam("bookingID") int bookingID) {
        return bookingService.deleteBooking(bookingID);
    }

}
