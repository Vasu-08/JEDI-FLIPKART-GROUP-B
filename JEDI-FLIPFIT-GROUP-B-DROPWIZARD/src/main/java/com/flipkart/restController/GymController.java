package com.flipkart.restController;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flipkart.business.FlipFitGymCentreBusiness;
import com.flipkart.business.interfaces.IFlipFitGymCentre;
import com.flipkart.model.FlipFitGymCentre;
import com.flipkart.model.FlipFitSlots;

@Path("/centre")
@Produces(MediaType.APPLICATION_JSON)

public class GymController {
    private final IFlipFitGymCentre flipFitGymCentreBusiness;

    @Inject
    public GymController(FlipFitGymCentreBusiness flipFitGymCentreService) {
        this.flipFitGymCentreBusiness = flipFitGymCentreService;
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public FlipFitGymCentre updateGymCentre(FlipFitGymCentre flipFitGymCentre) {
        return flipFitGymCentreBusiness.updateGymCentre(flipFitGymCentre);
    }

    @DELETE
    @Path("/delete/centreID={centreID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean deleteGymCentre(@PathParam("centreID") int centreID) {
        return flipFitGymCentreBusiness.deleteGymCentre(centreID);
    }

    @GET
    @Path("/viewSlots/centreID={centreID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitSlots> viewAvailableSlots(@PathParam("centreID") int centreID) {
        return flipFitGymCentreBusiness.viewAvailableSlots(centreID);
    }

}
