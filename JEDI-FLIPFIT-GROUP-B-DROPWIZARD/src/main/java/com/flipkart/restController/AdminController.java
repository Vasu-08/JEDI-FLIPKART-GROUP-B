package com.flipkart.restController;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flipkart.business.FlipFitAdminBusiness;
import com.flipkart.business.interfaces.IFlipFitAdmin;
import com.flipkart.model.FlipFitAdmin;
import com.flipkart.model.FlipFitGymCentre;
import com.flipkart.model.FlipFitGymCustomer;
import com.flipkart.model.FlipFitGymOwner;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)

public class AdminController {
    private final IFlipFitAdmin flipFitAdminBusiness;

    @Inject
    public AdminController(FlipFitAdminBusiness flipFitAdmin) {
        this.flipFitAdminBusiness = flipFitAdmin;
    }

    @GET
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean adminLogin(FlipFitAdmin flipFitAdmin) {
        return flipFitAdminBusiness.adminLogin(flipFitAdmin);
    }

    @GET
    @Path("/getPendingOwnerList")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitGymOwner> getPendingOwnerList() {
        return flipFitAdminBusiness.getPendingOwnerList();
    }

    @GET
    @Path("/getApprovedOwnerList")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitGymOwner> getApprovedOwnerList() {
        return flipFitAdminBusiness.getApprovedOwnerList();
    }

    @GET
    @Path("/getCustomersList")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitGymCustomer> getCustomersList() {
        return flipFitAdminBusiness.getUserList();
    }

    @GET
    @Path("/getGymCentreUsingOwnerId/ownerID={ownerID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitGymCentre> getGymCentreUsingOwnerId(@PathParam("ownerID") int ownerID) {
        return flipFitAdminBusiness.getGymCentreUsingOwnerId(ownerID);
    }

    @DELETE
    @Path("/deleteOwner/ownerID={ownerID}")
    @Consumes(MediaType.APPLICATION_JSON)
    public boolean deleteOwner(@PathParam("ownerID") int ownerID) {
        return flipFitAdminBusiness.deleteOwner(ownerID);
    }

}
