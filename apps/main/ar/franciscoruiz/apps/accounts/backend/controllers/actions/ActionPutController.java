package ar.franciscoruiz.apps.accounts.backend.controllers.actions;

import ar.franciscoruiz.accounts.actions.application.create.CreateActionCommand;
import ar.franciscoruiz.apps.shared.ApiController;
import ar.franciscoruiz.shared.domain.bus.command.CommandBus;
import ar.franciscoruiz.shared.domain.bus.query.QueryBus;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

    @PutMapping("/api/actions/{id}")
    public ResponseEntity<String> index(
        @RequestParam String id,
        @RequestBody Request request
    ) {
        this.dispatch(new CreateActionCommand(id, request.name(), request.moduleId()));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    static class Request {
        private String name;
        private String moduleId;

        public String name() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String moduleId() {
            return moduleId;
        }

        public void setModuleId(String moduleId) {
            this.moduleId = moduleId;
        }
    }
}