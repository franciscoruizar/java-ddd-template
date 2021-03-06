package ar.franciscoruiz.apps.accounts.backend.controllers.roles;

import ar.franciscoruiz.accounts.roles.application.RoleResponse;
import ar.franciscoruiz.accounts.roles.application.find.FindRoleQuery;
import ar.franciscoruiz.apps.shared.ApiController;
import ar.franciscoruiz.shared.domain.bus.command.CommandBus;
import ar.franciscoruiz.shared.domain.bus.query.QueryBus;
import ar.franciscoruiz.shared.domain.bus.query.QueryHandlerExecutionError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public final class RoleGetController extends ApiController {
    public RoleGetController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @GetMapping(value = "/roles/{id}")
    public ResponseEntity<HashMap<String, Object>> index(@PathVariable String id) throws QueryHandlerExecutionError {
        RoleResponse response = ask(new FindRoleQuery(id));

        return ResponseEntity.ok().body(encode(response));
    }
}
