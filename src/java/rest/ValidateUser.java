/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rest;

import dao.UserDAO;
import entities.User;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


@Path("/validateuser/{email}")
public class ValidateUser {
    @GET
    @Produces("text/plain")
    public String getText( @PathParam("email") String email) {
      User u = UserDAO.getUser(email);
      if ( u == null)
          return "";  // not found
      else
          return "Email is already in use!";
    }

}
