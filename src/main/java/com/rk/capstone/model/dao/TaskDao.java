package com.rk.capstone.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rk.capstone.model.domain.Task;

/**
 * DAO for Task Entity
 */
@Repository
public interface TaskDao extends CrudRepository<Task, Long> {

    Task save(Task task);

    List<Task> getTasksByCampaignId(Long campaignId);
}
