package com.rct.humanresources.infra.config.router;

import com.rct.humanresources.core.model.dto.JobHistoryDTO;
import com.rct.humanresources.infra.handler.JobHistoryHandler;
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

import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_JOB_HISTORIES_PATH;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_JOB_HISTORIES_PATH_BY_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_JOB_HISTORIES_PATH_BY_JOB_ID;
import static com.rct.humanresources.infra.config.VariableConstants.HANDLER_JOB_HISTORIES_STREAM_PATH;
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
public class JobHistoryRouterConfig {
    @Bean
    @RouterOperations({@RouterOperation(path = HANDLER_JOB_HISTORIES_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
            JobHistoryHandler.class, method = GET, beanMethod = "findAll", operation =
    @Operation(operationId = "findAll", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
            @Content(schema = @Schema(implementation = JobHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory details supplied")})),

    @RouterOperation(path = HANDLER_JOB_HISTORIES_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
            JobHistoryHandler.class, method = GET, beanMethod = "findById", operation =
    @Operation(operationId = "findById", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
            @Content(schema = @Schema(implementation = JobHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory details supplied"),
            @ApiResponse(responseCode = "404", description = "JobHistory not found")}, parameters = {@Parameter(in = PATH, name = "id")})),

    @RouterOperation(path = HANDLER_JOB_HISTORIES_PATH_BY_JOB_ID, produces = {APPLICATION_JSON_VALUE}, beanClass =
            JobHistoryHandler.class, method = GET, beanMethod = "findByJobId", operation =
    @Operation(operationId = "findByJobId", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
            @Content(schema = @Schema(implementation = JobHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory details supplied"),
            @ApiResponse(responseCode = "404", description = "JobHistory not found")}, parameters = {
            @Parameter(in = PATH, name = "jobId")})),

    @RouterOperation(path = HANDLER_JOB_HISTORIES_STREAM_PATH, produces = {APPLICATION_JSON_VALUE}, beanClass =
            JobHistoryHandler.class, method = GET, beanMethod = "stream", operation =
    @Operation(operationId = "stream", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content =
            @Content(schema = @Schema(implementation = JobHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory details supplied")})),

    @RouterOperation(path = HANDLER_JOB_HISTORIES_PATH, produces = {APPLICATION_JSON_VALUE}, method = POST, beanClass =
            JobHistoryHandler.class, beanMethod = "create", operation = @Operation(operationId = "create", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = JobHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory details supplied")}, requestBody =
    @RequestBody(content = @Content(schema = @Schema(implementation = JobHistoryDTO.class))))),

    @RouterOperation(path = HANDLER_JOB_HISTORIES_PATH, produces = {APPLICATION_JSON_VALUE}, method = PUT, beanClass =
            JobHistoryHandler.class, beanMethod = "updateById", operation = @Operation(operationId = "updateById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content =
                    @Content(schema = @Schema(implementation = JobHistoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory ID supplied"),
            @ApiResponse(responseCode = "404", description = "JobHistory not found")}, parameters = {
                    @Parameter(in = PATH, name = "id")}, requestBody =
    @RequestBody(content = @Content(schema = @Schema(implementation = JobHistoryDTO.class))))),

    @RouterOperation(path = HANDLER_JOB_HISTORIES_PATH_BY_ID, produces = {APPLICATION_JSON_VALUE}, method = DELETE, beanClass =
            JobHistoryHandler.class, beanMethod = "deleteById", operation = @Operation(operationId = "deleteById", responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "Boolean")),
            @ApiResponse(responseCode = "400", description = "Invalid JobHistory ID supplied"),
            @ApiResponse(responseCode = "404", description = "JobHistory not found")}, parameters = {@Parameter(in = PATH, name = "id")}))})

    /*
     * Routes Configuration - JobHistory
     *
     * @param handler JobHistoryHandler
     *
     * @return RouterFunction ServerResponse
     */
    @NotNull
    public RouterFunction<ServerResponse> jobHistoriesRoutes(JobHistoryHandler handler) {
        return route(RequestPredicates.GET(HANDLER_JOB_HISTORIES_PATH).and(accept(APPLICATION_JSON)), handler::findAll)
                .andRoute(RequestPredicates.GET(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::findById)
                .andRoute(RequestPredicates.GET(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::findByJobId)
                .andRoute(RequestPredicates.GET(HANDLER_JOB_HISTORIES_STREAM_PATH).and(accept(APPLICATION_JSON)), handler::stream)
                .andRoute(RequestPredicates.POST(HANDLER_JOB_HISTORIES_PATH).and(accept(APPLICATION_JSON)), handler::create)
                .andRoute(RequestPredicates.PUT(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::updateById)
                .andRoute(RequestPredicates.DELETE(HANDLER_JOB_HISTORIES_PATH_BY_ID).and(accept(APPLICATION_JSON)), handler::deleteById);
    }
}
