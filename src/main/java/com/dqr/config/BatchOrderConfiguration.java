package com.dqr.config;

import com.dqr.orderJob.beans.Order;
import com.dqr.orderJob.beans.SvcReq;
import com.dqr.orderJob.order.OrderSvcInvoker;
import com.dqr.orderJob.processor.JobCompletionNotificationListener;
import com.dqr.orderJob.processor.OrderItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Batch Configuration.
 * <p>
 * Created by dqromney on 3/15/17.
 */
@Configuration
@EnableBatchProcessing
@Import({BatchScheduler.class})
public class BatchOrderConfiguration {

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    // job to start every 30 seconds
    // @Scheduled(cron = "*/30 * * * * ?")
    // job to start at '5 PM 53 minutes 1 second' and run for every 3 minutes till 6 PM
    @Scheduled(cron = "1 53/3 17 * * ?")
    public void perform() throws Exception {

        System.out.println("Job Started at :" + new Date());

        JobParameters param = new JobParametersBuilder().addString("JobID",
                String.valueOf(System.currentTimeMillis())).toJobParameters();

        JobExecution execution = jobLauncher.run(processOrderJob(), param);

        System.out.println("Job finished with status :" + execution.getStatus());
    }

    @Bean
    public Job processOrderJob() {
        return jobBuilderFactory.get("processOrderJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(orderStep()).end().build();
    }

    @Bean
    public Step orderStep() {
        return stepBuilderFactory.get("orderStep").<Order, SvcReq>chunk(3)
                .reader(reader()).processor(processor()).writer(writer())
                .build();
    }

    @Bean
    public FlatFileItemReader<Order> reader() {
        FlatFileItemReader<Order> reader = new FlatFileItemReader<Order>();
        reader.setResource(new ClassPathResource("PhoneData.csv"));
        reader.setLineMapper(new DefaultLineMapper<Order>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[]{"orderID", "orderName"});
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Order>() {
                    {
                        setTargetType(Order.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public OrderItemProcessor processor() {
        return new OrderItemProcessor();
    }

    @Bean
    public ItemWriter<SvcReq> writer() {
        return new OrderSvcInvoker();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionNotificationListener();
    }

}