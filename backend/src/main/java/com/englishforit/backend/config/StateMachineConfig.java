package com.englishforit.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<LearningState, LearningEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<LearningState, LearningEvent> states)
            throws Exception {
        states
                .withStates()
                .initial(LearningState.ASSESS)
                .states(EnumSet.allOf(LearningState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<LearningState, LearningEvent> transitions)
            throws Exception {
        transitions
                // Start assessment -> Go to Practice
                .withExternal()
                .source(LearningState.ASSESS).target(LearningState.PRACTICE)
                .event(LearningEvent.ASSESSMENT_COMPLETE)
                .and()
                // Practice done -> Evaluate
                .withExternal()
                .source(LearningState.PRACTICE).target(LearningState.EVALUATE)
                .event(LearningEvent.PRACTICE_COMPLETE)
                .and()
                // Evaluation done -> Adapt (change difficulty/content)
                .withExternal()
                .source(LearningState.EVALUATE).target(LearningState.ADAPT)
                .event(LearningEvent.EVALUATION_COMPLETE)
                .and()
                // Adaptation done -> Next (or back to practice if needed, simplified flow here)
                .withExternal()
                .source(LearningState.ADAPT).target(LearningState.NEXT)
                .event(LearningEvent.ADAPTATION_COMPLETE)
                .and()
                // Next -> Loop back to Assess for next topic
                .withExternal()
                .source(LearningState.NEXT).target(LearningState.ASSESS)
                .event(LearningEvent.NEXT_TOPIC_SELECTED);
    }
}
