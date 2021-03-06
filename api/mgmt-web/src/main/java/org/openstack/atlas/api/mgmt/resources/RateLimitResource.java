package org.openstack.atlas.api.mgmt.resources;

import org.openstack.atlas.docs.loadbalancers.api.management.v1.RateLimit;
import org.openstack.atlas.service.domain.entities.LoadBalancer;
import org.openstack.atlas.service.domain.management.operations.EsbRequest;
import org.openstack.atlas.service.domain.operations.Operation;
import org.openstack.atlas.api.faults.HttpResponseBuilder;
import org.openstack.atlas.api.helpers.ResponseFactory;
import org.openstack.atlas.api.mgmt.repository.ValidatorRepository;
import org.openstack.atlas.api.mgmt.resources.providers.ManagementDependencyProvider;
import org.openstack.atlas.api.validation.context.HttpRequestType;
import org.openstack.atlas.api.validation.results.ValidatorResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

public class RateLimitResource extends ManagementDependencyProvider {

    private int loadbalancerId;

    public void setLoadbalancerId(int loadbalancerId) {
        this.loadbalancerId = loadbalancerId;
    }

    public int getLoadbalancerId() {
        return loadbalancerId;
    }

    @GET
    public Response getRateLimitDetails() throws ParseException {
        if (!isUserInRole("cp,ops,support")) {
            return ResponseFactory.accessDenied();
        }

        org.openstack.atlas.service.domain.entities.RateLimit domainRateLimit;
        RateLimit apiRateLimit;
        try {
            domainRateLimit = rateLimitingService.get(loadbalancerId);
            apiRateLimit = getDozerMapper().map(domainRateLimit, RateLimit.class);
            return Response.status(200).entity(apiRateLimit).build();
        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e, null, null);
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createRateLimit(RateLimit rateLimit) {
        if (!isUserInRole("cp,ops")) {
            return ResponseFactory.accessDenied();
        }

        ValidatorResult result = ValidatorRepository.getValidatorFor(RateLimit.class).validate(rateLimit, HttpRequestType.POST);

        if (!result.passedValidation()) {
            return Response.status(400).entity(HttpResponseBuilder.buildBadRequestResponse("Validation fault", result.getValidationErrorMessages())).build();
        }

        try {
            org.openstack.atlas.docs.loadbalancers.api.management.v1.LoadBalancer apiLb = new org.openstack.atlas.docs.loadbalancers.api.management.v1.LoadBalancer();
            apiLb.setRateLimit(rateLimit);
            LoadBalancer domainLb = getDozerMapper().map(apiLb, LoadBalancer.class);
            domainLb.setId(loadbalancerId);
            EsbRequest request = new EsbRequest();
            request.setLoadBalancer(domainLb);

            rateLimitingService.create(domainLb);
            getManagementAsyncService().callAsyncLoadBalancingOperation(Operation.CREATE_RATE_LIMIT, request);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e, null, null);
        }
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response appendRateLimit(RateLimit rateLimit) {
        if (!isUserInRole("cp,ops")) {
            return ResponseFactory.accessDenied();
        }
        ValidatorResult result = ValidatorRepository.getValidatorFor(RateLimit.class).validate(rateLimit, HttpRequestType.PUT);

        if (!result.passedValidation()) {
            return Response.status(400).entity(HttpResponseBuilder.buildBadRequestResponse("Validation fault", result.getValidationErrorMessages())).build();
        }

        try {
            org.openstack.atlas.docs.loadbalancers.api.management.v1.LoadBalancer apiLb = new org.openstack.atlas.docs.loadbalancers.api.management.v1.LoadBalancer();
            apiLb.setRateLimit(rateLimit);
            LoadBalancer domainLb   = getDozerMapper().map(apiLb, LoadBalancer.class);
            domainLb  .setId(loadbalancerId);
            EsbRequest request = new EsbRequest();
            request.setLoadBalancer(domainLb  );

            rateLimitingService.update(domainLb  );
            getManagementAsyncService().callAsyncLoadBalancingOperation(Operation.UPDATE_RATE_LIMIT, request);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e, null, null);
        }
    }

    @DELETE
    public Response deleteRateLimit() {
        if (!isUserInRole("cp,ops")) {
            return ResponseFactory.accessDenied();
        }

        try {
            LoadBalancer domainLb = new LoadBalancer();
            domainLb.setId(loadbalancerId);
            EsbRequest request = new EsbRequest();
            request.setLoadBalancer(domainLb);

            rateLimitingService.delete(domainLb);
            getManagementAsyncService().callAsyncLoadBalancingOperation(Operation.DELETE_RATE_LIMIT, request);
            return Response.status(Response.Status.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseFactory.getErrorResponse(e, null, null);
        }
    }
}
