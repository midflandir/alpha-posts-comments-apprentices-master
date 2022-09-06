package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddCommentCommand;
import com.posada.santiago.alphapostsandcomments.domain.events.CommentAdded;
import com.posada.santiago.alphapostsandcomments.domain.events.PostCreated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class AddCommentUseCaseTest {


    @Mock
    private EventBus eventBus;
    @Mock
    private DomainEventRepository domainEventRepository;

    @InjectMocks
    private AddCommentUseCase useCase;

    @Test
    void AddCommentUseCaseTest() {
        AddCommentCommand command = new AddCommentCommand(
                "77777",
                "33333",
                "Jean Camille",
                "你好！我是Juan Camilo， 我是哥伦比亚人",
                "Times New Roman"
        );

        CommentAdded event = new CommentAdded(
                "33333",
                "Jean Camille",
                "你好！我是Juan Camilo， 我是哥伦比亚人",
                "Times New Roman"
        );

        BDDMockito
                .when(this.domainEventRepository.findById(ArgumentMatchers.anyString()))
                .thenReturn(Flux.just(
                                new PostCreated(
                                        "The Last One",
                                        "Jean Camille")
                        )
                );
        BDDMockito
                .when(this.domainEventRepository.saveEvent(ArgumentMatchers.any(DomainEvent.class)))
                .thenReturn(Mono.just(event));

        Mono<List<DomainEvent>> triggeredevents = this.useCase.apply(Mono.just(command))
                .collectList();

        StepVerifier.create(triggeredevents)
                .expectSubscription()
                .expectNextMatches(domainEvents ->
                        domainEvents.size() == 1 &&
                                domainEvents.get(0) instanceof CommentAdded)
                .verifyComplete();

        BDDMockito.verify(this.eventBus, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.domainEventRepository, BDDMockito.times(1))
                .saveEvent(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.domainEventRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyString());
    }
}