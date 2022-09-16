package com.posada.santiago.alphapostsandcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.DomainEventRepository;
import com.posada.santiago.alphapostsandcomments.application.handlers.gateways.EventBus;
import com.posada.santiago.alphapostsandcomments.business.generic.UseCaseForCommand;
import com.posada.santiago.alphapostsandcomments.domain.Post;
import com.posada.santiago.alphapostsandcomments.domain.commands.AddCommentCommand;
import com.posada.santiago.alphapostsandcomments.domain.values.Author;
import com.posada.santiago.alphapostsandcomments.domain.values.CommentId;
import com.posada.santiago.alphapostsandcomments.domain.values.Content;
import com.posada.santiago.alphapostsandcomments.domain.values.Font;
import com.posada.santiago.alphapostsandcomments.domain.values.PostId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class AddCommentUseCase extends UseCaseForCommand<AddCommentCommand> {

    private final DomainEventRepository repository;
    private final EventBus bus;

    public AddCommentUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    @Override
    public Flux<DomainEvent> apply(Mono<AddCommentCommand> addCommentCommandMono) {
        return addCommentCommandMono.flatMapMany(command -> repository.findById(command.getPostId())
                .collectList()
                .flatMapIterable(events -> {
                    Post post = Post.from(PostId.of(command.getPostId()), events);
                    post.addAComment(CommentId.of(
                            command.getCommentId()),
                            new Author(command.getAuthor()),
                            new Content(command.getContent()),
                            new Font(command.getFont()));
                    log.info(" Congratulations, Comment is being added, looks like everything is working fine so far ");
                    return post.getUncommittedChanges();
                }).map(event -> {
                    bus.publish(event);
                    return event;
                }).flatMap(event -> repository.saveEvent(event))
        ).doOnError(error -> log.error("Error - Comment couldn't be created." + error));
    }
}
