package com.rct.humanresources.infra.config.router;

import com.rct.humanresources.core.model.dto.LocationDTO;
import com.rct.humanresources.infra.handler.LocationHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.jetbrains.annotations.NotNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_LOCATIONS_PATH;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_LOCATIONS_PATH_BY_CITY_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_LOCATIONS_PATH_BY_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_LOCATIONS_STREAM_PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class LocationRouterConfig {
    @Bean
    @RouterOperations({
            @RouterOperation(path = HANDLER_LOCATIONS_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    LocationHandler.class, method = GET, beanMethod = "findAll", operation =
            @Operation(operationId = "findAll", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Location details supplied")})),

            @RouterOperation(path = HANDLER_LOCATIONS_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    LocationHandler.class, method = GET, beanMethod = "findById", operation =
            @Operation(operationId = "findById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Location details supplied"),
                    @ApiResponse(responseCode = "404", description = "Location not found")}, parameters = {@Parameter(in = PATH, name = "id")})),

            @RouterOperation(path = HANDLER_LOCATIONS_PATH_BY_CITY_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    LocationHandler.class, method = GET, beanMethod = "findByCityId", operation =
            @Operation(operationId = "findByCityId", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Location details supplied"),
                    @ApiResponse(responseCode = "404", description = "Location not found")}, parameters = {@Parameter(in = PATH, name = "cityId")})),


            @RouterOperation(path = HANDLER_LOCATIONS_STREAM_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    LocationHandler.class, method = GET, beanMethod = "stream", operation =
            @Operation(operationId = "stream", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Location details supplied")})),

            @RouterOperation(path = HANDLER_LOCATIONS_PATH, produces = {APPLICATION_JSON_VALUE}, method = POST, beanClass =
                    LocationHandler.class, beanMethod = "create", operation =
            @Operation(operationId = "create", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Location details supplied")}, requestBody =
            @RequestBody(content = @Content(schema = @Schema(implementation = LocationDTO.class))))),

            @RouterOperation(path = HANDLER_LOCATIONS_PATH, produces = {APPLICATION_JSON_VALUE}, method = PUT, beanClass =
                    LocationHandler.class, beanMethod = "updateById", operation =
            @Operation(operationId = "updateById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = LocationDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Location ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Location not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")}, requestBody =
            @RequestBody(content = @Content(schema = @Schema(implementation = LocationDTO.class))))),

            @RouterOperation(path = HANDLER_LOCATIONS_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, method = DELETE, beanClass =
                    LocationHandler.class, beanMethod = "deleteById", operation =
            @Operation(operationId = "deleteById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(mediaType = "Boolean")),
                    @ApiResponse(responseCode = "400", description = "Invalid Location ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Location not found")}, parameters = {@Parameter(in = PATH, name = "id")}))})

    /*
     * Routes Configuration - Location
     *
     * @param handler LocationHandler
     *
     * @return RouterFunction ServerResponse
     */
    @NotNull
    public RouterFunction<ServerResponse> locationsRoutes(LocationHandler handler) {
        return route(RequestPredicates.GET(HANDLER_LOCATIONS_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(RequestPredicates.GET(HANDLER_LOCATIONS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(RequestPredicates.GET(HANDLER_LOCATIONS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::findByCityId)
                .andRoute(RequestPredicates.GET(HANDLER_LOCATIONS_STREAM_PATH).and(accept(APPLICATION_JSON)), handler::stream)
                .andRoute(RequestPredicates.POST(HANDLER_LOCATIONS_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT(HANDLER_LOCATIONS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::updateById)
                .andRoute(RequestPredicates.DELETE(HANDLER_LOCATIONS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }
}
