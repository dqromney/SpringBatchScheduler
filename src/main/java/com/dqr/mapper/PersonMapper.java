package com.dqr.mapper;

import com.dqr.model.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper{

	public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
		Person person = Person.builder()
				.firstName(rs.getString("firstName"))
				.lastName(rs.getString("lastName"))
				.school(rs.getString("school"))
				.rollNumber(rs.getInt("rollNumber"))
				.build();
		return person;
	}

}

