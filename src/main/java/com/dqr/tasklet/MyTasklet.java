package com.dqr.tasklet;

import com.dqr.mapper.PersonMapper;
import com.dqr.model.Person;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class MyTasklet implements Tasklet {

    private DataSource dataSource;
    private String sql = "select firstName,lastName,school,rollNumber from PERSON";


    public RepeatStatus execute(StepContribution contribution,
                                ChunkContext chunkContext) throws Exception {
        List<Person> personList = new ArrayList<Person>();
        JdbcTemplate myTemplate = new JdbcTemplate(getDataSource());
        personList = myTemplate.query(sql, new PersonMapper());
        for (Person p : personList) {
            System.out.println(p.toString());
        }
        return RepeatStatus.FINISHED;
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
