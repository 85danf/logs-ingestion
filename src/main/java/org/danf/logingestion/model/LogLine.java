package org.danf.logingestion.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Builder
@Data
@Document(indexName = "log_line")
public class LogLine {

    @Id
    private final long id;
    private final String containerName;
    private final String content;
    private final String level;

}
