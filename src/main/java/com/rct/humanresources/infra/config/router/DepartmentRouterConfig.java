package com.rct.humanresources.infra.config.router;

import com.rct.humanresources.core.model.dto.DepartmentDTO;
import com.rct.humanresources.infra.handler.DepartmentHandler;
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

import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_DEPARTMENTS_PATH;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_DEPARTMENTS_PATH_BY_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_DEPARTMENTS_PATH_BY_LOCATION_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_DEPARTMENTS_PATH_BY_MANAGER_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_DEPARTMENTS_SEARCH_PATH;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_DEPARTMENTS_STREAM_PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;
import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class DepartmentRouterConfig {
    @Bean
    @RouterOperations({@RouterOperation(path = HANDLER_DEPARTMENTS_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
            DepartmentHandler.class, method = GET, beanMethod = "findAll", operation =
    @Operation(operationId = "findAll", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
            @Content(schema = @Schema(implementation = DepartmentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid Department details supplied")})),

            @RouterOperation(path = HANDLER_DEPARTMENTS_PATH_BY_LOCATION_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    DepartmentHandler.class, method = GET, beanMethod = "findByLocationId", operation =
            @Operation(operationId = "findByLocationId", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department details supplied"),
                    @ApiResponse(responseCode = "404", description = "Department not found")}, parameters = {
                    @Parameter(in = PATH, name = "locationId")})),

            @RouterOperation(path = HANDLER_DEPARTMENTS_PATH_BY_MANAGER_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    DepartmentHandler.class, method = GET, beanMethod = "findByManagerId", operation =
            @Operation(operationId = "findByManagerId", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department details supplied"),
                    @ApiResponse(responseCode = "404", description = "Department not found")}, parameters = {
                    @Parameter(in = PATH, name = "managerId")})),

            @RouterOperation(path = HANDLER_DEPARTMENTS_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    DepartmentHandler.class, method = GET, beanMethod = "findById", operation =
            @Operation(operationId = "findById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department details supplied"),
                    @ApiResponse(responseCode = "404", description = "Department not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")})),

            @RouterOperation(path = HANDLER_DEPARTMENTS_SEARCH_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    DepartmentHandler.class, method = GET, beanMethod = "search", operation =
            @Operation(operationId = "search", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department details supplied"),
                    @ApiResponse(responseCode = "404", description = "Department not found")}, parameters = {
                    @Parameter(in = QUERY, name = "name")})),

            @RouterOperation(path = HANDLER_DEPARTMENTS_STREAM_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
                    DepartmentHandler.class, method = GET, beanMethod = "stream", operation =
            @Operation(operationId = "stream", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department details supplied")})),


            @RouterOperation(path = HANDLER_DEPARTMENTS_PATH, produces = {APPLICATION_JSON_VALUE}, method = PUT, beanClass =
                    DepartmentHandler.class, beanMethod = "updateById", operation =
            @Operation(operationId = "updateById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Department not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")}, requestBody =
            @RequestBody(content = @Content(schema = @Schema(implementation = DepartmentDTO.class))))),

            @RouterOperation(path = HANDLER_DEPARTMENTS_PATH, produces = {APPLICATION_JSON_VALUE}, method = POST, beanClass =
                    DepartmentHandler.class, beanMethod = "create", operation =
            @Operation(operationId = "create", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = DepartmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid Department details supplied")}, requestBody =
            @RequestBody(content = @Content(schema = @Schema(implementation = DepartmentDTO.class))))),

            @RouterOperation(path = HANDLER_DEPARTMENTS_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, method = DELETE, beanClass =
                    DepartmentHandler.class, beanMethod = "deleteById", operation =
            @Operation(operationId = "deleteById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(mediaType = "Boolean")),
                    @ApiResponse(responseCode = "400", description = "Invalid Department ID supplied"),
                    @ApiResponse(responseCode = "404", description = "Department not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")}))})

    /*
     * Routes Configuration - Department
     *
     * @param handler DepartmentHandler
     *
     * @return RouterFunction ServerResponse
     */
    @NotNull
    public RouterFunction<ServerResponse> departmentsRoutes(DepartmentHandler handler) {
        return route(RequestPredicates.GET(HANDLER_DEPARTMENTS_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(RequestPredicates.GET(HANDLER_DEPARTMENTS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(RequestPredicates.GET(HANDLER_DEPARTMENTS_PATH_BY_LOCATION_ID).and(accept(APPLICATION_JSON)), handler::findByLocationId)
                .andRoute(RequestPredicates.GET(HANDLER_DEPARTMENTS_PATH_BY_MANAGER_ID).and(accept(APPLICATION_JSON)), handler::findByManagerId)
                .andRoute(RequestPredicates.GET(HANDLER_DEPARTMENTS_SEARCH_PATH).and(accept(APPLICATION_JSON)), handler::search)
                .andRoute(RequestPredicates.GET(HANDLER_DEPARTMENTS_STREAM_PATH).and(accept(APPLICATION_JSON)), handler::stream)
                .andRoute(RequestPredicates.POST(HANDLER_DEPARTMENTS_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT(HANDLER_DEPARTMENTS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::updateById)
                .andRoute(RequestPredicates.DELETE(HANDLER_DEPARTMENTS_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);

    }

}