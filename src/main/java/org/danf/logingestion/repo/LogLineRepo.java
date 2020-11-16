package org.danf.logingestion.repo;


import org.danf.logingestion.model.LogLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogLineRepo extends ElasticsearchRepository<LogLine, Long> {


}
