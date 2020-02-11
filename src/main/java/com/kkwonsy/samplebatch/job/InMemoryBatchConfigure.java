package com.kkwonsy.samplebatch.job;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;

public class InMemoryBatchConfigure implements BatchConfigurer {

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;

    @Override
    public JobRepository getJobRepository() throws Exception {
        return jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        return jobExplorer;
    }


    // InMemory Batch Configurer 설정
    // Database Table로 Job을 관리하면 신경 써야 할게 많다. 단순한 목적에 맞게 Memory에서 관리
    @PostConstruct
    public void initialize() {
        if (this.transactionManager == null) {
            // ResourcelessTransactionManager: 트랜잭션이 필요 없을때 써야함
            // in tests/prototypes or when using a non-transactional job repository
            // not recommended to use for production.
            this.transactionManager = new ResourcelessTransactionManager();
        }
        try {
            MapJobRepositoryFactoryBean jobRepositoryFactoryBean
                    = new MapJobRepositoryFactoryBean(this.transactionManager);
            jobRepositoryFactoryBean.afterPropertiesSet();
            this.jobRepository = jobRepositoryFactoryBean.getObject();

            MapJobExplorerFactoryBean jobExplorerFactoryBean = new MapJobExplorerFactoryBean(jobRepositoryFactoryBean);
            jobExplorerFactoryBean.afterPropertiesSet();
            this.jobExplorer = jobExplorerFactoryBean.getObject();

            SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
            jobLauncher.setJobRepository(jobRepository);
            jobLauncher.afterPropertiesSet();
            this.jobLauncher = jobLauncher;

        } catch (Exception e) {
            throw new BatchConfigurationException(e);
        }
    }
}
