package ar.franciscoruiz.apps.accounts.backend.controllers.actions;

import ar.franciscoruiz.accounts.actions.application.create.CreateActionCommand;
import ar.franciscoruiz.apps.accounts.backend.controllers.actions.dto.ActionRequest;
import ar.franciscoruiz.apps.shared.ApiController;
import ar.franciscoruiz.shared.domain.bus.command.CommandBus;
import ar.franciscoruiz.shared.domain.bus.query.QueryBus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class ActionPutController extends ApiController {
    public ActionPutController(QueryBus queryBus, CommandBus commandBus) {
        super(queryBus, commandBus);
    }

    @PutMapping("/actions/{id}")
    public ResponseEntity<String> index(
        @RequestParam String id,
        @RequestBody ActionRequest request
    ) {
        this.dispatch(new CreateActionCommand(id, request.method(), request.moduleId()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
