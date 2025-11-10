package com.akimi.webapp.custom.restapi;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import jakarta.inject.Inject;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Inject})
class CustomController {

//    private final InteractionService interactionService;
//    private final TransactionalProcessor transactionalProcessor;
//    private final SimpleObjects simpleObjects;
//
//    @GetMapping("/custom/simpleObjects")
//    List<SimpleObject> all() {
//        return call("sven", simpleObjects::listAll)
//                .orElse(Collections.<SimpleObject>emptyList());
//    }
//
//    private <T> Optional<T> call(
//            final String username,
//            final Callable<T> callable) {
//
//        return interactionService.call(
//                    InteractionContext.ofUserWithSystemDefaults(UserMemento.ofName(username)),
//                    () -> transactionalProcessor.callWithinCurrentTransactionElseCreateNew(callable)
//                )
//                .ifFailureFail()
//                .getValue();
//    }

}
