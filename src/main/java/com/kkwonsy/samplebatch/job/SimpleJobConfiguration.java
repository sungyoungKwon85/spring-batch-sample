package com.kkwonsy.samplebatch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    private SimpleStep1Tasklet simpleStep1Tasklet;

//    @Bean
//    public BatchConfigurer batchConfigurer() {
//        return new InMemoryBatchConfigure();
//    }

    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1())
                .build();
    }

    @Bean
    public Step simpleStep1() {
        // 방법 1
//        return stepBuilderFactory.get("simpleStep1")
//                .tasklet(((contribution, chunkContext) -> {
//                    log.info("@@@@ This is Step1");
//                    return RepeatStatus.FINISHED;
//                }))
//                .build();

        // 방법 2
        return stepBuilderFactory.get("simpleStep1")
                .tasklet(simpleStep1Tasklet)
                .build();
    }
}
