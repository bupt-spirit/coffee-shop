package coffeeshop.restful;

import coffeeshop.entity.Image;
import coffeeshop.facade.ImageFacade;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.ejb.EJB;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

@Path("images")
public class ImagesResource {

    @EJB
    private ImageFacade imageFacade;

    @GET
    @Path("{uuid}")
    public Response getJpgImage(@PathParam("uuid") String uuid) {
        Image image = imageFacade.find(uuid);
        if (image == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
        ResponseBuilder rb = Response.ok(image.getContent(), image.getMediaType());
        return rb.build();
    }

}
